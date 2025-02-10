package com.selenium.tests;

import java.io.IOException;
import java.lang.reflect.Field;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.selenium.base.BaseTest;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
    	
        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).driver;
        try {
                String base64Screenshot = captureScreenshotAsBase64(driver);
            test.get().fail(result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());;
        } catch (IOException e) {
            test.get().fail("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented
    }

	 private WebDriver getWebDriver(Object testClass) {
	        WebDriver driver = null;
	        try {
	            Field driverField = testClass.getClass().getDeclaredField("driver");
	            driverField.setAccessible(true);
	            driver = (WebDriver) driverField.get(testClass);
	        } catch (NoSuchFieldException | IllegalAccessException e) {
	            e.printStackTrace();
	        }
	        return driver;
	    }
	 private String captureScreenshotAsBase64(WebDriver driver) {
	        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
	    }
}
