package com.aventstack.extentreports.reporter.klov;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.aventstack.extentreports.model.Report;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.observer.entity.ReportEntity;
import com.aventstack.extentreports.reporter.klov.entity.KlovProject;
import com.aventstack.extentreports.reporter.klov.entity.KlovURI;
import com.aventstack.extentreports.reporter.klov.util.URIUtil;
import com.google.gson.Gson;

public class KlovReporterClient {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final String url;
    private final String projectName;
    private final String reportName;

    public KlovReporterClient(String url, String projectName, String reportName) {
        this.url = url;
        this.projectName = projectName;
        this.reportName = reportName;
        try {
            init();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private final void init() throws ClientProtocolException, IOException, URISyntaxException {
        KlovProject project = new KlovProject(null, projectName);
        String json = new Gson().toJson(project);
        StringEntity requestEntity = new StringEntity(
                json,
                ContentType.APPLICATION_JSON);
        HttpPost postMethod = new HttpPost(URIUtil.build(url, KlovURI.PROJECT));
        postMethod.setEntity(requestEntity);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = httpclient.execute(postMethod);
        System.out.println(response);
    }

    public void flush(ReportEntity entity) throws ClientProtocolException, IOException {
        Report report = entity.getReport();
        putReport(report);
        report.getTestList().forEach(this::postTest);
    }

    private void putReport(Report report) {
        // KlovReport r = new KlovReport(report);
        // System.out.println(r.toString());
    }

    private void postTest(Test test) {
        // KlovTest t = new KlovTest(test, null);
        // System.out.println(t.toString());
    }
}
