package org.example;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Yanni
 */
public class HelloTest {

    @Test
    public void startChrome() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com/");

        String title = driver.getTitle();
        System.out.println(title);
        driver.close();
    }
}
