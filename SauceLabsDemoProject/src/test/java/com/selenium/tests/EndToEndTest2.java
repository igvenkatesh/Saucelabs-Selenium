package com.selenium.tests;


import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.selenium.base.BaseTest;
import com.selenium.base.DataProviderExample;
import com.selenium.pages.CheckoutPage;
import com.selenium.pages.LoginPage;
import com.selenium.pages.ProductPage;
import com.selenium.utils.ExcelUtils;

public class EndToEndTest2 extends BaseTest {

    @Test(dataProvider = "loginData1",dataProviderClass=DataProviderExample.class)
    public void endToEndTest(String username, String password, String firstName, String lastName) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        ProductPage productPage = new ProductPage(driver);
        assert productPage.isProductPageDisplayed();
        productPage.addItemToCart();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillCheckoutForm(firstName, lastName, "112123");
    }

   
}

