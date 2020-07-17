package com.aventstack.extentreports.config.external;

import java.io.File;
import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class JsonConfigLoaderTest {
    private static final String JSON_FILE = "src/test/resources/config/spark-config.json";
    private static final String JSON_FILE_ENUM = "src/test/resources/config/spark-config_lowercase_enum.json";

    @Test
    public void jsonConfigTest() throws FileNotFoundException {
        ExtentSparkReporterConfig conf = ExtentSparkReporterConfig.builder().build();
        JsonConfigLoader<ExtentSparkReporterConfig> loader = new JsonConfigLoader<ExtentSparkReporterConfig>(conf,
                new File(JSON_FILE));
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
    public void jsonConfigLowerCaseEnumTest() throws FileNotFoundException {
        ExtentSparkReporterConfig conf = ExtentSparkReporterConfig.builder().build();
        JsonConfigLoader<ExtentSparkReporterConfig> loader = new JsonConfigLoader<ExtentSparkReporterConfig>(conf,
                new File(JSON_FILE_ENUM));
        loader.apply();
        Assert.assertEquals(conf.getProtocol(), Protocol.HTTP);
        Assert.assertEquals(conf.getTheme(), Theme.DARK);
    }
}
