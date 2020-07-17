package com.aventstack.extentreports;

import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Main {

    public static void main(String[] args) {
        /*ExtentKlovReporter klov = new ExtentKlovReporter("http://localhost", "ExtentReports");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(klov);
        extent.createTest("Test").pass("pass")
                .createNode("Node").fail("fail");
        extent.flush();*/
        
        String base64 = "iVBORw0KGgoAAAANSUhEUgAAB38AAAPBCAIAAABxzPdfAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAP+lSURBVHhe7P3/k11VeuaJVkT/VH+F7TA/mF+IuA7/5LgxfaNMf6mYpieqiaGa4UYbAlp2QdVgSs29BkOVgQIhpGqEaqANClyUDMgISEAICavvgNRjSoDQF1sSqkakQMWXhESZKSi6Ugzl4D5rPWu/+91r7ZN5MvOczLNTzyeeOL32u9/1rnetfaQZP+w6+trp9z+SJEmSJEmSJEmSJEmSJEmSpMHqa18JsXrBVzyNhFil4Ev+63P/tyRJkiRJkiRJkiRJ0ghK7rNYzch9Fqseuc+SJEmSJEmSJEmSJI2s5D6L1YzcZ7HqkfssSZIkSZIkSZIkSdLISu6zWM3IfRarHrnPkiRJkiRJkiRJkiSNrOQ+i9WM3Gex6pH7LEmSJEmSJEmSJEnSyErus1jNyH0Wqx65z5IkSZIkSZIkSZIkjazkPovVjNxnseqR+yxJkiRJkiRJkiRJ0shK7rNYzch9Fqseuc+SJEmSJEmSJEmSJI2s5D53hk9/9fnEx1O//ODj0+9/RGGMCOIpQxTIfRarHrnPkiRJkiRJkiRJkiQNQ++++Psnn/z6vHrnxf9HNtFL7nMH+PLL30x8PGWmcyncRU7KFg4cThoJsUrBlzz7a12SJEmSJEmSJEmSJGnpOvnk17NIq+ZOk/tcMDF2zYUXXRC17mAITI5dd83YJ/HeyjC39UwhJ2ULB04mjcSC+WT7muu2T6QLgD8I9ifigg1HYkysPPiSZ3+tL17vvXTb3S+9kwUXJFRY+9hrWTDqnd333bb7Qxf5x/+y9r4d79nlgoS57assWq/99Nb/cigPjpyyB7T05zXqKh70/nv4/3UOuubJX/pbQ9Avn/iOLXfVExNV/I07Lrxnv0sbkrC6W3SuYKb9d/tuJUmSJEmSJEmSFi+5z4Mn2GoXbjyUrr46NDY2udLu86e/+twsZujkS//H//ov/803/uV/2PjSL30c0k9wlOBY0kgsmJ7uc08mxq5ZE/7IiOUEX/Lsr/Ul6MMddy/aEZ5PA7VKB28WH3rs2p/+Yx4cjOY61YVtJD/DxTyvxRzdEA8nqVdXeXz/PRfc/QbHw/BYUfOO/WkcrGdncONWtfSou8+SJEmSJEmSJEmDktzngXNknbOejZV1n5svPp/466v+3eb9H53e/9C/vurxw3U8SK8/l+BY0kgsGLnP3QBf8uyv9SXp0GPNN5QHqdd+Ojhr+72XbhuwH/rhjrsH/D51peG5z4t5Xt1yn/MH7dznxnhAqt3n009elVvME49e851HT2Mg91mSJEmSJEmSpPNFma380T/85f";
        Exception ex = new Exception("an exception has occurred");
        
        /*ExtentKlovReporter klov = new ExtentKlovReporter();
        klov.initKlovServerConnection("http://localhost:8585").initMongoDbConnection("localhost");
        klov.setProjectName("Tests");*/
        ExtentSparkReporter spark = new ExtentSparkReporter("target/spark/");
        spark.config().enableOfflineMode(true);
        
        ExtentReports extent = new ExtentReports();
        //extent.createDomainFromJsonArchive("target/spark/test.json");
        extent.attachReporter(spark);
        /*
        ExtentTest feature = extent.createTest(Feature.class, "My feature").assignCategory("BDD");
        ExtentTest scenario = feature.createNode(Scenario.class, "My scenario");
        scenario.createNode(Given.class, "Given something").pass("");
        scenario.createNode(When.class, "When I").pass("");
        Thread.sleep(1000);
        scenario.createNode(Then.class, "Then something").pass("");
        
        extent.flush();
        System.exit(0);
        */
        /*
        extent.createTest("FirstTest").pass("Passed");
        extent.createTest("SecondTest").createNode("SecondTestNode").warning("warn");
        extent.createTest("ThirdTest").createNode("ThirdTestNode").createNode("ThirdTestNodeNode").fail("fail");
        extent.createTest("CategoryTest").assignCategory("Cat").pass("Pass");
        extent.createTest("CategoryNodeTest").createNode("CategoryNode").assignCategory("ChildCat").skip("skip");
        */
        
        String[][] data = {
                { "Header1", "Header2", "Header3" },
                { "Content.1.1", "Content.2.1", "Content.3.1" },
                { "Content.1.2", "Content.2.2", "Content.3.2" },
                { "Content.1.3", "Content.2.3", "Content.3.3" },
                { "Content.1.4", "Content.2.4", "Content.3.4" }
            };
        Markup m = MarkupHelper.createTable(data);
        
        ExtentTest test = extent.createTest("MediaTestBase64", "A short description");
        test.addScreenCaptureFromBase64String(base64);
        test.fail("base64", MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
        test.fail(ex);
            //.addScreenCaptureFromPath("temp/1.png")
            //.fail("fail", MediaEntityBuilder.createScreenCaptureFromPath("temp/1.png").build());
        //test.pass(MarkupHelper.createCodeBlock("{\"foo\" : \"bar\", \"foos\" : [\"b\",\"a\",\"r\"], \"bar\" : {\"foo\":\"bar\", \"bar\":false,\"foobar\":1234}}", CodeLanguage.JSON));
        //test.pass(m);
        //extent.removeTest(test);
                
        test = extent.createTest("MediaTest", "A short description");
        test
            .createNode("MediaTestNode1Level1").addScreenCaptureFromPath("1.png").fail("fail", MediaEntityBuilder.createScreenCaptureFromPath("1.png").build());
        ExtentTest node = test.createNode("MediaTestNode2Level1");
        ExtentTest subNode = node.createNode("MediaTestNode1Level2").addScreenCaptureFromPath("1.png").fail("fail", MediaEntityBuilder.createScreenCaptureFromPath("1.png").build());
        subNode = node.createNode("MediaTestNode2Level2").addScreenCaptureFromPath("1.png").pass("pass");       
        
        extent.createTest("CategoryTest").assignCategory("ExtentAPI").fail("fail");
        
        test = extent.createTest("MultipleCategoriesTest").assignCategory("ExtentAPI", "Klov").pass("pass");
        
        //extent.removeTest(test);

        extent.createTest("AuthorTest").assignAuthor("A").pass("Pass");
        
        test = extent.createTest("MultipleAuthorsTest").assignAuthor("A", "B").pass("pass");
        
        extent.createTest("MultipleCategoriesAndAuthorsTest").assignCategory("ExtentAPI", "Klov").assignAuthor("A", "B").pass("pass");
        
        extent.createTest("EventHierarchyTest")
            .fail("fail")
            .warning("warning")
            .skip("skip")
            .pass("pass")
            .info("info");
        
        extent.createTest("ExceptionTest")
            .fail(new RuntimeException("Runtime ex 1"))
            .createNode("NodeLevel1")
                .fail(new RuntimeException("Runtime ex 2"));
        
        extent.createTest("SkippedTest").skip("skipped");
        
        extent.setSystemInfo("sys", "value");
        extent.setSystemInfo("sys", "value");
        extent.flush();
    }

}
