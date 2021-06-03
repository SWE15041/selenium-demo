package org.example;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;

/**
 * @author Yanni
 */
public class ScreenShotTest {

    @Test
    public void simpleScreenshot(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");

        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile,new File("screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.quit();

    }
}
