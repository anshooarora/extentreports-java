package com.aventstack.extentreports.reporter.klov.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

public class URIUtil {
    public static URI build(String host, String path) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(host);
        builder.setPath(path);
        URI uri = builder.build();
        return uri;
    }
}
