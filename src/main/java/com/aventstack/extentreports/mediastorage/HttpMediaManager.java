package com.aventstack.extentreports.mediastorage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.utils.FileUtil;

class HttpMediaManager implements MediaStorage {

    private static final Logger logger = Logger.getLogger(HttpMediaManager.class.getName());
    
    private static final String ROUTE = "upload";
    private static final String CSRF_ROUTE = "csrfToken";
    private String csrf;
    private String host;
    private String cookie;
    
    @Override
    public void init(String host) throws IOException {
        this.host = host;

        if (host.lastIndexOf('/') != host.length() - 1) {
            this.host = host + "/";
        }
        
        storeCsrfCookie();
    }
    
    @SuppressWarnings("rawtypes")
    private void storeCsrfCookie() throws IOException {
        HttpGet get = new HttpGet(host + CSRF_ROUTE);
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(get);
        
        int responseCode = response.getStatusLine().getStatusCode();
        boolean isValid = isResponseValid(responseCode); 
        
        if (isValid) {
            if (response.getHeaders("set-cookie").length == 0)
                throw new AssertionError("set-cookie was not returned from the server");
            
            cookie = response.getHeaders("set-cookie")[0].getValue();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            StringBuilder s = new StringBuilder();

            String sResponse;
            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }

            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine engine = sem.getEngineByName("javascript");
            String json = s.toString();
            String script = "Java.asJSONCompatible(" + json + ")";
        
            try {
                Object result = engine.eval(script);
                Map contents = (Map) result;
                csrf = contents.get("_csrf").toString();
            } 
            catch (ScriptException e) {
                logger.log(Level.SEVERE, "Unable to parse x-csrf-token", e);
            }
        }
    }
    
    @Override
    public void storeMedia(Media m) throws IOException {
        File f = new File(m.getPath());
        if (!f.exists()) {
            throw new IOException("The system cannot find the file specified " + m.getPath());
        }
        
        HttpPost post = new HttpPost(host + ROUTE);

        post.addHeader("X-CSRF-TOKEN", csrf);
        post.addHeader("Connection", "keep-alive");
        post.addHeader("User-Agent", "Mozilla/5.0");
        post.addHeader("Cookie", cookie);
        post.addHeader("Accept", "application/json");

        String ext = FileUtil.getExtension(m.getPath());
        
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();        
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("name", new StringBody(m.getSequence() + "." + ext, ContentType.TEXT_PLAIN));
        builder.addPart("id", new StringBody(m.getObjectId().toString(), ContentType.TEXT_PLAIN));
        builder.addPart("reportId", new StringBody(m.getReportObjectId().toString(), ContentType.TEXT_PLAIN));
        builder.addPart("testId", new StringBody(m.getTestObjectId().toString(), ContentType.TEXT_PLAIN));
        if (m.getLogObjectId() != null) 
            builder.addPart("logId", new StringBody(m.getLogObjectId().toString(), ContentType.TEXT_PLAIN));
        builder.addPart("mediaType", new StringBody(String.valueOf(m.getMediaType()).toLowerCase(), ContentType.TEXT_PLAIN));
        builder.addPart("f", new FileBody(new File(m.getPath())));
        post.setEntity(builder.build());
        
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(post);

        int responseCode = response.getStatusLine().getStatusCode();
        boolean isValid = isResponseValid(responseCode);
        
        if (!isValid) {
            logger.warning("Unable to upload file to server " + m.getPath());
        }
    }
    
    private boolean isResponseValid(int responseCode) {
        if (200 <= responseCode && responseCode <= 399) {
            return true;
        }
        
        return false;
    }

}
