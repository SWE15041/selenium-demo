package org.example;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.Set;

/**
 * @author Yanni
 */
public class ChromeUITest {

    /**
     * set window size
     *
     * @throws Exception
     */
    @Test
    public void updateWindowSize() throws Exception {
        WebDriver driver = new ChromeDriver();

        System.out.println("Open a Google window of the max size");
        driver.get("https://www.baidu.com");
        driver.manage().window().maximize();
        Thread.sleep(2000);

        System.out.println("Open a Google window of the specified size");
        driver.get("https://www.baidu.com");
        driver.manage().window().setSize(new Dimension(480, 800));
        Thread.sleep(2000);

        driver.quit();
    }


    @Test
    public void switchBackAndForward() throws Exception {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.baidu.com");
        System.out.println("now access url is " + driver.getCurrentUrl());
        Thread.sleep(2000);

        driver.findElement(By.linkText("新闻")).click();
        driver.navigate().refresh();
        // bug http://news.baidu.com/
        System.out.println("now access url is " + driver.getCurrentUrl());
        Thread.sleep(20000);

        driver.navigate().back();
        System.out.printf("back to %s \n", driver.getCurrentUrl());
        Thread.sleep(2000);

        driver.navigate().forward();
        System.out.printf("forward to %s \n", driver.getCurrentUrl());
        Thread.sleep(2000);

        driver.quit();
    }

    @Test
    public void frequentlyMethod() throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com/");

        WebElement search_text = driver.findElement(By.id("kw"));
        WebElement search_button = driver.findElement(By.id("su"));

        search_text.sendKeys("Java");
        Thread.sleep(2000);
        search_text.clear();
        search_text.sendKeys("Selenium");
        Thread.sleep(2000);
        search_button.click();

        driver.quit();
    }

    @Test
    public void mouseOperator() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com/");

        //设置
        WebElement search_setting = driver.findElement(By.id("s-usersetting-top"));
        Actions action = new Actions(driver);
        action.clickAndHold(search_setting).perform();

        //鼠标右键点击指定的元素
        action.contextClick(driver.findElement(By.id("element"))).perform();
        // 鼠标右键点击指定的元素
        action.doubleClick(driver.findElement(By.id("element"))).perform();

        // 鼠标拖拽动作， 将 source 元素拖放到 target 元素的位置。
        WebElement source = driver.findElement(By.name("element"));
        WebElement target = driver.findElement(By.name("element"));
        action.dragAndDrop(source, target).perform();


        // 释放鼠标
        action.release().perform();

        driver.quit();
    }

    @Test
    public void keyBoard() throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");

        // 获取搜素输入框 输入seleniumm
        WebElement input = driver.findElement(By.id("kw"));
        input.sendKeys("seleniumm");
        Thread.sleep(2000);

        // 退格键 删掉最后一个m
        input.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(2000);

        // 追加 空格
        input.sendKeys(Keys.SPACE);
        // 追加 教程
        input.sendKeys("教程");
        Thread.sleep(2000);

        // 执行 ctrl+a 全选
        input.sendKeys(Keys.CONTROL, "a");
        Thread.sleep(2000);

        // 执行 ctrl+x 剪切
        input.sendKeys(Keys.CONTROL, "x");
        Thread.sleep(2000);

        // 执行 ctrl+v 粘贴
        input.sendKeys(Keys.CONTROL, "v");
        Thread.sleep(2000);

        // 执行 回车键≈
        input.sendKeys(Keys.ENTER);
        Thread.sleep(2000);


        driver.quit();
    }

    @Test
    public void switchWindow() throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");

        //获得当前窗口句柄
        String search_handle = driver.getWindowHandle();

        //打开百度注册窗口
        driver.findElement(By.linkText("登录")).click();
        Thread.sleep(3000);
        driver.findElement(By.linkText("立即注册")).click();

        //获得所有窗口句柄
        Set<String> handles = driver.getWindowHandles();

        //判断是否为注册窗口， 并操作注册窗口上的元素
        for (String handle : handles) {
            if (!handle.equals(search_handle)) {
                //切换到注册页面
                driver.switchTo().window(handle);
                System.out.println("now register window!");
                Thread.sleep(2000);
                driver.findElement(By.name("userName")).clear();
                driver.findElement(By.name("userName")).sendKeys("user name");
                driver.findElement(By.name("phone")).clear();
                driver.findElement(By.name("phone")).sendKeys("phone number");
                //......
                Thread.sleep(2000);
                //关闭当前窗口
                driver.close();
            }
        }
        Thread.sleep(2000);

        driver.quit();

    }

    @Test
    public void dropDown() throws Exception{
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");

        driver.findElement(By.id("s-usersetting-top")).click();
//        driver.findElement(By.linkText("设置")).click();
        driver.findElement(By.linkText("搜索设置")).click();
        Thread.sleep(2000);

        //<select>标签的下拉框选择
//        WebElement el = driver.findElement(By.xpath("//select"));
//        Select sel = new Select(el);
//        sel.selectByValue("20");
//        Thread.sleep(2000);

        driver.quit();
    }
}

