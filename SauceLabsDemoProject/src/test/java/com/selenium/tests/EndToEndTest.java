package com.selenium.tests;


import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.selenium.base.BaseTest;
import com.selenium.pages.CheckoutPage;
import com.selenium.pages.LoginPage;
import com.selenium.pages.ProductPage;
import com.selenium.utils.ExcelUtils;

@Listeners(com.selenium.tests.ExtentReportListener.class)
public class EndToEndTest extends BaseTest {

    @Test(dataProvider = "loginData")
    public void endToEndTest(String username, String password, String firstName, String lastName) {
    	System.out.println(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        ProductPage productPage = new ProductPage(driver);
        assert productPage.isProductPageDisplayed();
        productPage.addItemToCart();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillCheckoutForm(firstName, lastName, "112123");
    }

    @DataProvider(name = "loginData")
    public Object[][] getData() throws IOException {
        ExcelUtils excel = new ExcelUtils("src/test/resources/data/testdata.xlsx");
        return excel.getTestData("Sheet1");
    }
}

