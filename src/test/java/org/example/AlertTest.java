package org.example;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * alert、confirm以及prompt
 *
 * @author Yanni
 */
public class AlertTest {
    @Test
    public void alert() throws  Exception{

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");

        driver.findElement(By.id("s-usersetting-top")).click();
//        driver.findElement(By.linkText("设置")).click();
        driver.findElement(By.linkText("搜索设置")).click();
        Thread.sleep(2000);

        //保存设置
        driver.findElement(By.className("prefpanelgo")).click();
        Thread.sleep(2000);

        //接收弹窗
        driver.switchTo().alert().accept();
        Thread.sleep(2000);

        driver.quit();
    }
}
