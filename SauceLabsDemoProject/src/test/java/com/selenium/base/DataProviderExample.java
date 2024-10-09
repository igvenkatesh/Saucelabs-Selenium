package com.selenium.base;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import com.selenium.utils.ExcelUtils;

public class DataProviderExample {
	 @DataProvider(name = "loginData1")
	    public Object[][] getData() throws IOException {
	        ExcelUtils excel = new ExcelUtils("src/test/resources/data/testdata.xlsx");
	        return excel.getTestData("Sheet1");
	    }
}
