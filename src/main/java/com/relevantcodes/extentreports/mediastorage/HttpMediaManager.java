package com.relevantcodes.extentreports.mediastorage;

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

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.relevantcodes.extentreports.model.Media;
import com.relevantcodes.extentreports.utils.FileUtil;

class HttpMediaManager implements MediaStorage {

    private static final Logger logger = Logger.getLogger(HttpMediaManager.class.getName());
    
    private final String route = "upload";
    private final String csrfRoute = "csrfToken";
    private String csrf;
    private String host;
    private String cookie;
    
    @Override
    public void init(String host) throws IOException {
        this.host = host;

        if (host.lastIndexOf("/") != host.length() - 1) {
            this.host = host + "/";
        }
        
        storeCsrfCookie();
    }
    
    @SuppressWarnings("rawtypes")
    private void storeCsrfCookie() throws IOException {
        HttpGet get = new HttpGet(host + csrfRoute);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(get);
        
        int responseCode = response.getStatusLine().getStatusCode();
        boolean isValid = isResponseValid(responseCode); 
        
        if (isValid) {
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
        HttpPost post = new HttpPost(host + route);

        post.addHeader("X-CSRF-TOKEN", csrf);
        post.addHeader("Connection", "keep-alive");
        post.addHeader("User-Agent", "Mozilla/5.0");
        post.addHeader("Cookie", cookie);

        String ext = FileUtil.getExtension(m.getPath());
        
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("name", new StringBody(m.getSequence() + "." + ext));
        entity.addPart("id", new StringBody(m.getObjectId().toString()));
        entity.addPart("reportId", new StringBody(m.getReportObjectId().toString()));
        entity.addPart("testId", new StringBody(m.getTestObjectId().toString()));
        entity.addPart("mediaType", new StringBody(String.valueOf(m.getMediaType()).toLowerCase()));
        entity.addPart("f", new FileBody(new File(m.getPath())));
        post.setEntity(entity);
        
        CloseableHttpClient client = HttpClientBuilder.create().build();
        
        CloseableHttpResponse response = client.execute(post);

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
