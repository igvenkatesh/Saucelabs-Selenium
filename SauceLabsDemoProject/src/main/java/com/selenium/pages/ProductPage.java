package com.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {

    private WebDriver driver;

    private By productTitle = By.className("product_label");
    private By addToCartButton = By.className("btn_inventory");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isProductPageDisplayed() {
        return driver.findElement(productTitle).getText().equals("Products1");
    }

    public void addItemToCart() {
        driver.findElement(addToCartButton).click();
        driver.findElement(By.id("shopping_cart_container")).click();
        driver.findElement(By.xpath("//*[@id='cart_contents_container']/div/div[2]/a[2]")).click();
    }
}

