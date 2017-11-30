package com.aventstack.extentreports.mediastorage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;

import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.utils.FileUtil;

class HttpMediaManagerKlov implements MediaStorage {

    private static final Logger logger = Logger.getLogger(HttpMediaManagerKlov.class.getName());
    
    private static final String ROUTE = "files/upload";
    private String host;
    
    @Override
    public void init(String host) throws IOException {
        this.host = host;

        if (host.lastIndexOf('/') != host.length() - 1) {
            this.host = host + "/";
        }
    }
    
    @Override
    public void storeMedia(Media m) throws IOException {
        if (m.getPath() == null || m.getBase64String() != null)
            return;
        
        File f = new File(m.getPath());
        if (!f.exists()) {
            throw new IOException("The system cannot find the file specified " + m.getPath());
        }
        
        HttpPost post = new HttpPost(host + ROUTE);

        post.addHeader("Connection", "keep-alive");
        post.addHeader("User-Agent", "Mozilla/5.0");
        post.addHeader("Accept", "application/json");

        String ext = FileUtil.getExtension(m.getPath());

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();        
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("name",new StringBody(m.getSequence() + "." + ext, ContentType.TEXT_PLAIN));
        builder.addPart("id", new StringBody(m.getObjectId().toString(), ContentType.TEXT_PLAIN));
        builder.addPart("reportId", new StringBody(m.getReportObjectId().toString(), ContentType.TEXT_PLAIN));
        builder.addPart("testId", new StringBody(m.getTestObjectId().toString(), ContentType.TEXT_PLAIN));
        builder.addPart("mediaType", new StringBody(String.valueOf(m.getMediaType()).toLowerCase(), ContentType.TEXT_PLAIN));
        builder.addPart("f", new FileBody(new File(m.getPath())));
        post.setEntity(builder.build());

        String logId = m.getLogObjectId() == null ? "" : m.getLogObjectId().toString();
        builder.addPart("logId", new StringBody(logId, ContentType.TEXT_PLAIN));
        
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(post);

        int responseCode = response.getStatusLine().getStatusCode();
        boolean isValid = isResponseValid(responseCode);
        
        if (!isValid) {
            logger.warning("Unable to upload file to server " + m.getPath());
        }
    }
    
    private boolean isResponseValid(int responseCode) {
        return 200 <= responseCode && responseCode <= 399;
    }

}
