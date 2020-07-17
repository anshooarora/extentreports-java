package com.aventstack.extentreports.config.external;

import java.io.File;
import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class XmlConfigLoaderTest {
    private static final String XML_FILE = "src/test/resources/config/spark-config.xml";
    private static final String XML_FILE_ENUM = "src/test/resources/config/spark-config.xml";

    @Test
    public void xmlConfigTest() throws FileNotFoundException {
        ExtentSparkReporterConfig conf = ExtentSparkReporterConfig.builder().build();
        XmlConfigLoader<ExtentSparkReporterConfig> loader = new XmlConfigLoader<ExtentSparkReporterConfig>(conf,
                new File(XML_FILE));
        loader.apply();
        Assert.assertEquals(conf.getCss(), "css1");
        Assert.assertEquals(conf.getDocumentTitle(), "Test1");
        Assert.assertEquals(conf.getEncoding(), "utf-16");
        Assert.assertEquals(conf.getReportName(), "Test2");
        Assert.assertEquals(conf.getTimeStampFormat(), "MMM dd, yyyy HH:mm:ss a");
        Assert.assertEquals(conf.getOfflineMode().booleanValue(), true);
        Assert.assertEquals(conf.getProtocol(), Protocol.HTTP);
        Assert.assertEquals(conf.getTheme(), Theme.DARK);
    }

    @Test
    public void xmlConfigLowerCaseEnumTest() throws FileNotFoundException {
        ExtentSparkReporterConfig conf = ExtentSparkReporterConfig.builder().build();
        XmlConfigLoader<ExtentSparkReporterConfig> loader = new XmlConfigLoader<ExtentSparkReporterConfig>(conf,
                new File(XML_FILE_ENUM));
        loader.apply();
        Assert.assertEquals(conf.getProtocol(), Protocol.HTTP);
        Assert.assertEquals(conf.getTheme(), Theme.DARK);
    }
}
