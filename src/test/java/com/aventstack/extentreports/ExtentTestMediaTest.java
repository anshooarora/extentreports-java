package com.aventstack.extentreports;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.model.ScreenCapture;

public class ExtentTestMediaTest {
    private static final String BASE64_ENCODED = "data:image/png;base64,";
    private static final String BASE64 = "iVBORw0KGgoAAAANSUhEUgAAAY4AAABbCAYAAABkgGJUAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAABIMSURBVHhe7Z3"
            +
            "daxRXH8effyZ3gVwIXgiFemXwookXkUIINFQQGrx4clV9LpILadCLEOzjWm0U00ZMQ7SQUEuKbSw1BoKkEdnqI1tss3lZ1rh1a9JsTMLvOWfmnJmzu/NyzuyLO/H7gUObcWfmzMzu7zPn/V8EAAAA"
            +
            "GABxAAAAMALiAAAAYATEAQAAwAiIAwAAgBEQBwAAACMgDgAAAEZAHAAAAIyAOAAAABgBcQAAADAC4gAAAGAExAEAAMAIiAMAAIAREAcAAAAjIA4AAABGQBwAAACMgDgAAAAYAXEAAAAwAuIAAABgB"
            +
            "MQBAADACIgDAACAERAHAAAAIyAOAAAARkAcAAAAjIA4AAAAGAFxAAAAMALiAAAAYATEAQAAwAiIAwAAgBEQBwAAvAUyM8t0dGiJemf+FlviA8QBAABvAYijhIVzTdTUVFk6MZEVRwP7hcXbS9YPpZ"
            +
            "IUxx8ZAF5AHCVAHMALiAMAF4ijBCmOWAX/3TkaaDpB42vi73eM9Ddd1HRuQfxVG6Q4EPwBgDjKiKU4lsep6x0Wx9xn7JlBHADUDYijhDiKo/BzP8vzuyqOFI0egzgAqCcQRwlxFEfyi8Pvrjjy09T"
            +
            "LnhfEAUD9gDhKiJ848jT9b57nd1QcvyXoMMQBQF2BOEqImzgKa9PU3/KOiqOQp+S149bzgjgAqB8QRwnVF0eBHbPVOmbzyXFK74rNumwu0MARnqdWGnhYsDYVlu/RaF8PtR608xqeBkgrrO7mKfXj"
            +
            "KPV/0kZt7zU7+ze/x/7+pJ9Gf0xRXjf/a+N0gu9/cpKK7mQuRXMTV8vO0dR0gFqP9VD/l+O0EChAJouJQer98BA1O/sGp2o8y1qIwz5mmsb+FBs4e5u0NJ+h8yNp6rhgn5On9otp6p58SdviY2W82"
            +
            "aKlZ1kam1yl05eL9z16ge07skpjsznKvBGfDyOTpV6+79gL5ZzblHv+gsbGlqn7c4/jz7+inN/x869o5q6dt3a5H0sdl5ap73aGZtKb4oMReLNBKXHP1Hy1f+7ma2NPfNYLea1DqzRr/8T02HtJCX"
            +
            "Gfh5+JbTrI811Yo8WgfHH2tiiTzNIIv+eX2DWJa7PvXZpOj7F793xDfFiTx2vW/t0/vBIbODu0kfZ5tpfXaL7kuUYSxya7X+warP1+youN9Sc+JY7dFI128OM2s+OmxUYdCnSvzw6urV8kxTai7J0"
            +
            "z1HaMBV4rtdIBK0A206EP5LbSdDVUHOkpdkyr5OImSxhFwZ1t++AMjf9P49clxdHFZMn/3s3S3H+7RF7D0gHq+nLBR1ILdFW9Npm/g63K9RanM3caWRxLlHgsNqy/oKHL4gfrkU795HHu9XUauVEc"
            +
            "jK1k/eDtVPRvF5Zp5OmW2DkAGdyuZSnD/87naOSachy/dGGFbv2uHJ+JMHV3pTx/Hqn7JjtXWCAtYpuWZlepW5UkS5Yw1ODHt11epSk1X0W8ojFx3xOPd8Q2DVgAltfV8d1fYmM4Gw9W7HwVSbmUb"
            +
            "cosrtGpkmvzS8HXV4IQx9HbL+2/917TLItLvs9oWHwHFIzFwaUh7jF/ATJUXVWJV1XVMgukVmBupcRvYlsI2W9P2G/VHweVVFiJxAq2EauqWCljTpSIrIB9jr3x/1Eiht0CZX+/R4mPDojPuaUfX6"
            +
            "Q4eGlnM0mJD+0Af6Cjl65OJSmdK9+/8EeSJhW5tJ5bYOoMJjtxws5TDKuqisShvI2dup2lxRU1COzQ9utN2ih4BLWnGepg+xxlAaaXvX1OP8t7fG6btlfWaVgGfiaPqbWQAOm8hbO34rK8/UPbaoB"
            +
            "npZ0MK4mUHX/vb5q5mba2tV9eoRH25p/ZLAmVm3laZCURGfzbv85STvxTIExI87ftY7v5Kgmc7G2dl5CcfLHSXeKxd3B98p04vwymoWzR7Lg8LksXM/RE/EswO85z73vgV8raYvlZdqV0fc3jufLv"
            +
            "RJ6ezGaoz3nZSNPZBxrfzyJxbLH8iGd0bY2mknnaVksXhX9o43X5PTMSB/seTF2389jJzvk2pcGJXRuHI4IjPJiKjX5I0bQwISyLbZ5UIo4CLQwJaRzsofE/xGZfCpT6SlxDWL4ccRynri4ujQPUc"
            +
            "yMVKgJO4eEAtVr7Hnbfxn3YH+LYoJmv2f+zgDv8xLTK5m9anM/5VxGp8GA7YZ/z6EhIgHbEkaZTw3bebqX9348t2JvrzJh7/Pkf7IDUOf6CcmEliTV2PiEPv+DuwoLdpJAGK+FMheWLfT71gwjEft"
            +
            "KUpQddARTW6TzPL/v8sLinetVVf9HwRf55lo/S13iLHSso23lN09DD12J7AOy5Ljqi8ZejgyKOHCv98P1OfZcLrs4rQVscqjR0vgd1IIaN4wWa+4x3nQ15m95N0/jHPB/NdOLbsHxEF0fh10E7QIf"
            +
            "KScW9huZ/T5NvTaUjDnE/vzGpoiMmKNHo3XcvUDb7QRy8rpr/eIcehQXMKpDL0ml2Lh5gbgU9EkccdtLOm3N8kXhVl2awWLorZBBYhcPKT0/WqJN/Tqfk5LDlSNOzVOO0V5S0OfmwvbBqPbOOKSZt"
            +
            "UfVU3Gbgw58Z6uZ5v5yhJbGpiGcZ+9p0BFDENrt/djA/emGVZoPeP6Q4Lqapk11zx8S6cSlASxxKqbD9umk1ZO2IoTgYTmN3M/X/7B0Sk1/YpYDDn81pvKFHFUdatLuYB3XKTVKPdc7jNOpXSlHF8"
            +
            "X6CkqadAuT+Lax0FrDvfhCH9cNiwbI+RfgtmhWlAv+qEoYqjis+Qc4T9/g8nV8wCH6s1HGK78cbjcWmcvJ064p97F6vNp8gAqXp5jv8GbvVVFabiLxXfjJQkAHXu01kg6ZH7ON2sNJAWDmqDPZ2f0"
            +
            "tUy3UGtblIcfAUJhkfwsWhVIE1kDQ4NRVH5KQTwH5L+L7pO9U0OtVZFhHFIcc/tPTTnE79URG80Z6fM0CwijjavkqJjSYs0KBGN+P9Io7hp7pvzpUTHLwEijhMr3lJVFHxXkrzRgGDvfVb+7GSxLr"
            +
            "YVAp7I7fbdAx7QFkEy8FptGYlkkCJy2oqp1eUbFwPK61s2lWS7ByejfDpDJ20rn+FprUaesqRJaHAHluKOHiJKQrB4nCrBtuHM2Rc+1pj4isORpoFPN5WUNRFV+l6O/ir7q8imjjs0eZsv5CqID+s"
            +
            "iQWD9nfE0cyuRWwzIiuq6w4HdiaotzgiJ49GV+eY2g2rVeLRqm+eHBRxGHU1Zcig4vTI0oa9MfP2FN/6f7cRO6w6y4/MTyJvXvs71xwspe2H9v1rH193jiHzFVhdVXhB5wOOn/sl6n1T0OkirIjD9"
            +
            "NlK/MWhtNFc4h0rxOYGIp5VVRLejnHS7ml03Hojd7ve2n/rEkUcWZo8Ka4ztA3FB1YysgK27G5biiMOw5KQgxRHEw08FJs82BfimPB7va4RSuOoL04Q9Q/ifjjiCDq+J3/TlFXV4nfO105Da+8vGo"
            +
            "3GXshr9+hiqlaD+XfLdUstRdVwsiQUVF3Fzm0FVM8SzY7TBqPVVuKLW6rxLSk64jAtEbp4i2OHckyqVhsNk8Z8vn6laBPiLQ7O5j0x6ruVzvzHowSiRRRxyH2Cg3IgUhx+gwsdcQzQnGn7hkVjisP"
            +
            "3xxgBeczKAkUEjMQR1N7gTe3EIauyeGAXm0xxgqb3dclqNt9qPFlqKKsqk72l/Kur5LG9v0Py2pmQHlbWScIplfndf3kPNNpk/PASxwY7rnmnhfoTf3Ew3K6nLBn1bpJEEIe1foc4Z8UpXBzRQvq7"
            +
            "I46qHbOwQankC5r6fo2Gbiw7AwDLBgHKFDdx7K3TkJr/ipLPdYWUHGQ7iNd4DxmwT9716msor81PLOogRLEpIqH3X4qjgiqxUnFsPM0ogxVNe4TVl30hDrfUwVLgQD8/IohDaX/wH22um3xGpUMco"
            +
            "VTnmNuUe5aloessmFk/Wu/UqUjEGVUdN3E4eWIlgkvK9URKPu1KThuBVx5CGreldLx6oeWz1Mf/zbc9qwqlKUHdxSHH4LCSxtCEHIOyQjMRG/hrzT4QB29rsNs1mlvs/7YOhY+WLiaCOORU5JHbHz"
            +
            "SAOEKp+Jhv8jR90y1NtF9ZoaG7WZrno4zz/m98WoG9EcUhg69vVVY1cNswyrory/P79lhyq6tKu/s6Deq+9ySeJY6z32foNJfGJSYKq3qKPUPRDtVo3XAlsRdHUc+qPBNAyWSGekQQh7NPV4SqMU0"
            +
            "gjlAqO6b7A+U/2qmUfveV2IpD6a57q1YvPAwZ5Et7XgVVU0m8q6t0phmR117HNo4qiIOndj4JotoQrkxR0xk0MedbIt7i+H2UjvOAp7RrOO0dLf10TzsORBFHmsa77OscmBObqg3EEUolx3T765sP"
            +
            "4IqvOGR3XT6SvYaNr54li5BqKomsrioKyrI0EVxSkt+HuvWqqoI42q9nyPOd5U85JqXx2jviK47CAg2K0eO9d4ob0uSocf3eVVHEwa/Trho7rMy6W1UgjlCiH9OtTokSZELfSDkNKQ73zd1kNlpz3"
            +
            "Ld/Z5xDaDWVxGMuKnkvQ3oxOQMQKwjoRuM4qiAO/++uOucWuxcN1MsqpuJw1+fwnFJEnadKawr2aOIo3O+3JyuMMh2IDhBHKNGP6QY28zdvd6xC/MShlLR4I3MN689Lu87K6iudkdZSzLJaytk3TH"
            +
            "a684gFYDRyvKbi4DTmtCOxFEdhbsCe6iNoShGjKdilOIJHWJexm6TE+3w/nYkUI1BvcQRNuFgFGlUcxvXhchp2nmIoDtrLiTd6dt9+qd6zKENWOVntHG5JR2tqGLmvGOi3OGnvG1jFZeHOgRVl4kH"
            +
            "ae0Vj4qVAa66qmouDobZ3sO9DfeZjCyZ+4shNU6+mEPSnYHcDbM+UWejM3+m1zxFp/EgIdRKHMxCxViUnQWOJw62qMpprSPkRWymO4mDIqcBrWwUiqpz4m7v8f+1Sjqyu4oMEZftG8DQmDs59T1Pi"
            +
            "kUnj1Ralvhf3XXd23HqIg6NMmR84y/LeFm3kN4vXA6kB8RKHMsWI3my0yup/54K76DrzTnWMUsooeLrVZk0txynxq5l4CstZjWnVaywOp2ux/2zD1aCxxKFUSbAgG7pOBicrVxdM09mbGoG9gcWhV"
            +
            "oFweZitYbJD22uvNRaLkqUMdn8f2ffCRNKyuirxQLSNhE2c6KC0DbBndX6+hutx1EscjHDZs2fv9BJk37na/ZTjJQ7PSQ3DUKZgL21EL8IJ0kwyn45T0uP7XfD7bTGhTX8q5MHOc+jkAI3fT1HW6/"
            +
            "OFPOX5Kn03+qnnCF8NMEAK9RKHJT8hTia/wftpKpTe30KhfJshjSaOoh/aBSaDuy9pqXSltsI/lHm+TlO33UbKYb5s7BMxZ1JsxcFQVhfkia+SN7XoscIgh69it5Kjab7muVUS0Lsm2TbRe82+HqM"
            +
            "ZjEV1VfcVe5yN2TNWFp5iSa7MV7ay4+Zra631yCsA1lEcRbK/5FFyc75vdkrUcNbPmoqjklQmHaXr7aRh/3PdxZbSd87Yn5Op5RC1WcFd/B3SeJy9n6Cug8r+oYlLhklQ7F9G3cTBUJamlenAkTY6"
            +
            "JEfkV2GgY+OJg1EyADAo8eAzvy4Cj/yRxlkcFmbrcsvUcT2rN0eTM+CQJePGeFldxZPe4lDF7NBGKktnHSkEp0hrjtdVHAx1vRD23Sgqga0XiyPqrL06xEMcTtfbwzRgvvCFhbMaXtiUJGtzNNrXU"
            +
            "yyMg63U9lEvDf6sUYLaLVD64Thd5cf44JDd/iETF9GxNurpu0rjPyYpHXYp9RQHZzdPyalB6v1IFYY9pUpP3yQlKyz6NqQ4LFiASb+gsbFl6r7EAojy4+u8vEx9tzM087y0kkQMpIu9OAR7W5RJZm"
            +
            "nE4x7wtdj5FCOnxzI0NZ+jjNe67b4oCytFWLfC6fZcybT5/NqeZe3n64jITv7PN4S3JQ6O0t5R3LnBXg6YP7uOr1m+atgDqybiAAAAsH+BOAAAABgBcQAAADAC4gAAAGAExAEAAMAIiAMAAIAREAc"
            +
            "AAAAjIA4AAABGQBwAAACMgDgAAAAYAXEAAAAwAuIAAABgBMQBAADACIgDAACAERAHAAAAIyAOAAAARkAcAAAAjIA4AAAAGAFxAAAAMALiAAAAYATEAQAAwAiIAwAAgBEQBwAAACMgDgAAAEZAHAAA"
            +
            "AIyAOAAAABgBcQAAADAC4gAAAGAExAEAAMAAov8D9RpXHrLGShcAAAAASUVORK5CYII=";
    private static final String PATH = "src/test/resources/img.png";
    private static final String TITLE = "MediaTitle";

    @Test
    public void addScreenCaptureFromPathTest() {
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest("Test")
                .addScreenCaptureFromPath(PATH, TITLE)
                .pass("Pass");
        Assert.assertEquals(test.getModel().getMedia().size(), 1);
        Assert.assertEquals(test.getModel().getMedia().get(0).getPath(), PATH);
        Assert.assertEquals(test.getModel().getMedia().get(0).getTitle(), TITLE);
    }

    @Test
    public void addScreenCaptureFromPathTestOverloads() {
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest("Test")
                .addScreenCaptureFromPath(PATH)
                .pass("Pass");
        Assert.assertEquals(test.getModel().getMedia().size(), 1);
        Assert.assertEquals(test.getModel().getMedia().get(0).getPath(), PATH);
    }

    @Test
    public void addScreenCaptureFromPathNode() {
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest("Test");
        ExtentTest node = test
                .createNode("Node")
                .addScreenCaptureFromPath(PATH, TITLE)
                .pass("Pass");
        Assert.assertEquals(test.getModel().getMedia().size(), 0);
        Assert.assertEquals(node.getModel().getMedia().size(), 1);
        Assert.assertEquals(node.getModel().getMedia().get(0).getPath(), PATH);
        Assert.assertEquals(node.getModel().getMedia().get(0).getTitle(), TITLE);
    }

    @Test
    public void addScreenCaptureFromPathTestLog() {
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest("Test")
                .pass("Pass", MediaEntityBuilder.createScreenCaptureFromPath(PATH, TITLE).build());
        Assert.assertEquals(test.getModel().getMedia().size(), 0);
        Assert.assertNotNull(test.getModel().getLogs().get(0).getMedia());
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), PATH);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getTitle(), TITLE);
    }

    @Test
    public void addScreenCaptureFromPathTestLogOverloads() {
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest("Test")
                .pass("Pass", MediaEntityBuilder.createScreenCaptureFromPath(PATH).build());
        Assert.assertEquals(test.getModel().getMedia().size(), 0);
        Assert.assertNotNull(test.getModel().getLogs().get(0).getMedia());
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), PATH);
    }

    @Test
    public void addScreenCaptureFromPathNodeLog() {
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest("Test");
        ExtentTest node = test
                .createNode("Node")
                .pass("Pass", MediaEntityBuilder.createScreenCaptureFromPath(PATH, TITLE).build());
        Assert.assertEquals(node.getModel().getMedia().size(), 0);
        Assert.assertNotNull(node.getModel().getLogs().get(0).getMedia());
        Assert.assertEquals(node.getModel().getLogs().get(0).getMedia().getPath(), PATH);
        Assert.assertEquals(node.getModel().getLogs().get(0).getMedia().getTitle(), TITLE);
    }

    @Test
    public void addScreenCaptureFromBase64Test() {
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest("Test")
                .addScreenCaptureFromBase64String(BASE64, TITLE)
                .pass("Pass");
        Assert.assertEquals(test.getModel().getMedia().size(), 1);
        Assert.assertEquals(((ScreenCapture) test.getModel().getMedia().get(0)).getBase64(), BASE64_ENCODED + BASE64);
        Assert.assertEquals(test.getModel().getMedia().get(0).getTitle(), TITLE);
    }

    @Test
    public void addScreenCaptureFromBase64Node() {
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest("Test");
        ExtentTest node = test
                .createNode("Node")
                .addScreenCaptureFromBase64String(BASE64, TITLE)
                .pass("Pass");
        Assert.assertEquals(test.getModel().getMedia().size(), 0);
        Assert.assertEquals(node.getModel().getMedia().size(), 1);
        Assert.assertEquals(((ScreenCapture) node.getModel().getMedia().get(0)).getBase64(), BASE64_ENCODED + BASE64);
        Assert.assertEquals(node.getModel().getMedia().get(0).getTitle(), TITLE);
    }

    @Test
    public void addScreenCaptureFromBase64NodeOverloads() {
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest("Test");
        ExtentTest node = test
                .createNode("Node")
                .addScreenCaptureFromBase64String(BASE64)
                .pass("Pass");
        Assert.assertEquals(test.getModel().getMedia().size(), 0);
        Assert.assertEquals(node.getModel().getMedia().size(), 1);
        Assert.assertEquals(((ScreenCapture) node.getModel().getMedia().get(0)).getBase64(), BASE64_ENCODED + BASE64);
    }

    @Test
    public void addScreenCaptureFromBase64TestLog() {
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest("Test")
                .pass("Pass", MediaEntityBuilder.createScreenCaptureFromBase64String(BASE64, TITLE).build());
        Assert.assertEquals(test.getModel().getMedia().size(), 0);
        Assert.assertNotNull(test.getModel().getLogs().get(0).getMedia());
        Assert.assertEquals(((ScreenCapture) test.getModel().getLogs().get(0).getMedia()).getBase64(),
                BASE64_ENCODED + BASE64);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getTitle(), TITLE);
    }

    @Test
    public void addScreenCaptureFromBase64NodeLog() {
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest("Test");
        ExtentTest node = test
                .createNode("Node")
                .pass("Pass", MediaEntityBuilder.createScreenCaptureFromBase64String(BASE64, TITLE).build());
        Assert.assertEquals(node.getModel().getMedia().size(), 0);
        Assert.assertNotNull(node.getModel().getLogs().get(0).getMedia());
        Assert.assertEquals(((ScreenCapture) node.getModel().getLogs().get(0).getMedia()).getBase64(),
                BASE64_ENCODED + BASE64);
        Assert.assertEquals(node.getModel().getLogs().get(0).getMedia().getTitle(), TITLE);
    }

    @Test
    public void addScreenCaptureFromBase64NodeLogOverloads() {
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest("Test");
        ExtentTest node = test
                .createNode("Node")
                .pass("Pass", MediaEntityBuilder.createScreenCaptureFromBase64String(BASE64).build());
        Assert.assertEquals(node.getModel().getMedia().size(), 0);
        Assert.assertNotNull(node.getModel().getLogs().get(0).getMedia());
        Assert.assertEquals(((ScreenCapture) node.getModel().getLogs().get(0).getMedia()).getBase64(),
                BASE64_ENCODED + BASE64);
    }
}
