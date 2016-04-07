package com.relevantcodes.extentreports.converters;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.relevantcodes.extentreports.utils.FileReaderEx;

public class ReportParser {
    private Document doc;
    
    public String getMongoDBObjectID() {
        Elements extentxMeta = doc.select("meta#extentx");
        
        if (extentxMeta != null && extentxMeta.size() > 0) {
            return extentxMeta.first().attr("content");
        }
        
        return null;
    }
    
    public ReportParser(File file) {
        String source = FileReaderEx.readAllText(file);
        doc = Jsoup.parse(source);
    }
}
