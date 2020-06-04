package com.webselenium;

import base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;


public class SampleWebUI extends BaseTest {

    String baseUrl = "https://www.swtestacademy.com";

    @Test(priority = 1)
    public void screenshot() {
        driver.navigate().to(baseUrl);
        WebElement logo = driver.findElement(By.className("fusion-logo-link"));
        File file = logo.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File("logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 2)
    public void openNewTab() {
        driver.navigate().to("https://www.google.com");
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://www.swtestacademy.com");
    }

    @Test(priority = 3)
    public void openNewWindow() {
        driver.navigate().to("https://www.google.com");
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.navigate().to("https://www.swtestacademy.com");
    }


}
