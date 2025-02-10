package com.selenium.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.service.ExtentTestManager;
//@Listeners({ExtentITestListenerClassAdapter.class})
public class BaseTest {

	public WebDriver driver;
	private static final String IMG_NAME = "screenshot.png";

    // A static image stored under classpath
    private static final String IMG_PATH = "src/test/resources/" + IMG_NAME;

    // Using the same OUTPUT_PATH as set in extent.properties
    // [extent.reporter.html.out]
    private static final String OUTPUT_PATH = "test-output/HtmlReport/";

	@BeforeSuite
	public void setUp() {

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/v1/index.html");
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		
	}
	 @AfterMethod
	    public synchronized void afterMethod(ITestResult result) throws IOException {
	        switch (result.getStatus()) {
	        case ITestResult.FAILURE:
	        	 ExtentTestManager.getTest(result).fail("ITestResult.FAILURE, event afterMethod",
	        			 MediaEntityBuilder.createScreenCaptureFromBase64String("base64String").build());
	            break;
	        case ITestResult.SKIP:
	            ExtentTestManager.getTest(result).skip("ITestResult.SKIP, event afterMethod");
	            break;
	        default:
	            ExtentTestManager.getTest(result).pass("default, event afterMethod");
	            break;
	        }
	        
	    }

private String getImage() throws IOException {
    Files.copy(new File(IMG_PATH).toPath(), new File(OUTPUT_PATH + IMG_NAME).toPath());
    return IMG_NAME;
}
}