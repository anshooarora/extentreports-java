package com.aventstack.extentreports;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Asterisk;
import com.aventstack.extentreports.gherkin.model.Background;
import com.aventstack.extentreports.gherkin.model.But;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.gherkin.model.ScenarioOutline;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;

public class BddTypeTest {
    private ExtentReports extent() {
        return new ExtentReports();
    }

    @Test
    public void featureIsOfBddType(Method method) {
        ExtentTest feature = extent().createTest(Feature.class, method.getName());
        Assert.assertTrue(feature.getModel().isBDD());
        Assert.assertEquals(feature.getModel().getBddType(), Feature.class);
    }

    @Test
    public void scenarioIsOfBddTypeWithBddChild(Method method) {
        ExtentTest feature = extent().createTest(Feature.class, method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        Assert.assertTrue(scenario.getModel().isBDD());
        Assert.assertEquals(scenario.getModel().getBddType(), Scenario.class);
    }

    @Test
    public void scenarioOutlineIsOfBddTypeWithBddChild(Method method) {
        ExtentTest feature = extent().createTest(Feature.class, method.getName());
        ExtentTest scenarioOutline = feature.createNode(ScenarioOutline.class, "ScenarioOutline");
        Assert.assertTrue(scenarioOutline.getModel().isBDD());
        Assert.assertEquals(scenarioOutline.getModel().getBddType(), ScenarioOutline.class);
    }

    @Test
    public void andIsOfBddTypeWithBddChild(Method method) {
        ExtentTest feature = extent().createTest(Feature.class, method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest and = scenario.createNode(And.class, "And");
        Assert.assertTrue(and.getModel().isBDD());
        Assert.assertEquals(and.getModel().getBddType(), And.class);
    }

    @Test
    public void asteriskIsOfBddTypeWithBddChild(Method method) {
        ExtentTest feature = extent().createTest(Feature.class, method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest asterisk = scenario.createNode(Asterisk.class, "Asterisk");
        Assert.assertTrue(asterisk.getModel().isBDD());
        Assert.assertEquals(asterisk.getModel().getBddType(), Asterisk.class);
    }

    @Test
    public void backgroundIsOfBddTypeWithBddChild(Method method) {
        ExtentTest feature = extent().createTest(Feature.class, method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest background = scenario.createNode(Background.class, "Background");
        Assert.assertTrue(background.getModel().isBDD());
        Assert.assertEquals(background.getModel().getBddType(), Background.class);
    }

    @Test
    public void butIsOfBddTypeWithBddChild(Method method) {
        ExtentTest feature = extent().createTest(Feature.class, method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest but = scenario.createNode(But.class, "But");
        Assert.assertTrue(but.getModel().isBDD());
        Assert.assertEquals(but.getModel().getBddType(), But.class);
    }

    @Test
    public void givenIsOfBddTypeWithBddChild(Method method) {
        ExtentTest feature = extent().createTest(Feature.class, method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest given = scenario.createNode(Given.class, "Given");
        Assert.assertTrue(given.getModel().isBDD());
        Assert.assertEquals(given.getModel().getBddType(), Given.class);
    }

    @Test
    public void thenIsOfBddTypeWithBddChild(Method method) {
        ExtentTest feature = extent().createTest(Feature.class, method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest then = scenario.createNode(Then.class, "Then");
        Assert.assertTrue(then.getModel().isBDD());
        Assert.assertEquals(then.getModel().getBddType(), Then.class);
    }

    @Test
    public void whenIsOfBddTypeWithBddChild(Method method) {
        ExtentTest feature = extent().createTest(Feature.class, method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest when = scenario.createNode(When.class, "When");
        Assert.assertTrue(when.getModel().isBDD());
        Assert.assertEquals(when.getModel().getBddType(), When.class);
    }
}
