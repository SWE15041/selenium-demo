package demo.jsjp;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yanni
 */
public class TimerTest {

    @Test
    public void startUp() throws Exception {
        WebDriver driver = new ChromeDriver();
        login(driver, "username", "password");
        System.out.println("开始计时");
        driver.findElement(By.id("startTrain")).click();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        timing(driver, startTime, endTime);
        System.out.println("关闭计时");
        driver.findElement(By.id("startTrain")).click();
    }

    public void login(WebDriver driver, String username, String password) {
        while (true) {
            try {
                driver.get("http://www.jppt.com.cn/");
                driver.findElement(By.linkText("用户登录")).click();
                driver.findElement(By.id("UserName")).sendKeys(username);
                driver.findElement(By.id("Pwd")).sendKeys(password);
                downloadImg(driver, "codeimg", "verifyCodeImg.png");
                String verifyCode = getVerifyCode("verifyCodeImg.png").replace(" ", "");
                System.out.println("verifyCode: " + verifyCode);
                driver.findElement(By.id("VerifyCode")).sendKeys(verifyCode);
                driver.findElement(By.className("logbtn")).click();

                WebElement hasLogin = driver.findElement(By.id("startTrain"));
                if (hasLogin != null) {
                    System.out.println("登录成功");
                    break;
                }
            } catch (Exception e) {
                System.out.println("登录失败");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
    }

    public void timing(WebDriver driver, LocalDateTime startTime, LocalDateTime endTime) throws InterruptedException {
        List<String> ids = new ArrayList<>();
        ids.add("exam");
        ids.add("guide");
        ids.add("video");
        ids.add("index");
        while (startTime.isBefore(endTime)) {
            for (String id : ids) {
                System.out.println("switch id: " + id);
                driver.findElement(By.id(id)).click();
                Thread.sleep(3 * 60 * 1000);
            }
            driver.navigate().refresh();
            Thread.sleep(3 * 60 * 1000);
            startTime = startTime.plusMinutes(16);
            int count = 0;
            while (count < 20) {
                count++;
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 30, 3000);
                    WebElement verificationCode = wait.until(new ExpectedCondition<WebElement>() {
                        @Override
                        public WebElement apply(WebDriver text) {
                            return text.findElement(By.id("VerificationCode"));
                        }
                    });
                    if (verificationCode != null) {
                        System.out.println("算术验证码识别：start");
                        boolean result = calculationResult(driver);
                        if (result) {
                            System.out.println("算术验证码识别：end");
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("算术验证码识别 fail");
                    Thread.sleep(2000);
                    driver.navigate().refresh();
                }
            }
        }

    }

    public boolean calculationResult(WebDriver driver) {
        if (driver.findElement(By.id("VerificationCode")).isDisplayed()) {
            downloadImg(driver, "VerificationCode", "VerificationCode.png");
            String expression = getVerifyCode("VerificationCode.png").replace(" ", "");
            expression = expression.substring(0, expression.indexOf("="));
            System.out.println("expression: " + expression);
            Pattern pattern = Pattern.compile("^(\\d+)(\\+|-)(\\d+)$");
            Matcher matcher = pattern.matcher(expression);
            String result = "";
            if (matcher.find()) {
                Integer num1 = Integer.valueOf(matcher.group(1));
                String operator = matcher.group(2);
                Integer num2 = Integer.valueOf(matcher.group(3));
                switch (operator) {
                    case "+":
                        result = String.valueOf(num1 + num2);
                        break;
                    case "-":
                        result = String.valueOf(num1 - num2);
                        break;
                    default:
                        System.out.println("error");

                }
            }
            System.out.println("result:\t" + result);
            driver.findElement(By.id("IdentyCode")).sendKeys(result);
            driver.findElement(By.xpath("//div[@class=\"ui-dialog-button\"]/button")).click();
            return true;
        } else {
            System.out.println("VerificationCode not found");
            return false;
        }
    }

    public void downloadImg(WebDriver driver, String id, String path) {
        try {
            WebElement ele = driver.findElement(By.id(id));
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // Get entire page screenshot
            BufferedImage fullImg = ImageIO.read(screenshot);
            // Get the location of element on the page
            org.openqa.selenium.Point point = ele.getLocation();
            // Get width and height of the element
            int eleWidth = ele.getSize().getWidth();
            int eleHeight = ele.getSize().getHeight();
            // Crop the entire page screenshot to get only element screenshot
            BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "png", screenshot);
            // Copy the element screenshot to disk
            File screenshotLocation = new File(path);
            FileUtils.copyFile(screenshot, screenshotLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getVerifyCode(String path) {
        // 语言库位置（修改为跟自己语言库文件夹的路径）
        String lagnguagePath = "tessdata";

        File file = new File(path);
        ITesseract instance = new Tesseract();

        //设置训练库的位置
        instance.setDatapath(lagnguagePath);

        //chi_sim ：简体中文， eng    根据需求选择语言库
        instance.setLanguage("eng");
        String result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = instance.doOCR(file);
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * cd ～/.m2/repository/net/sourceforge/tess4j/tess4j/4.5.4/
     * mkdir darwin
     * jar uf tess4j-4.5.4.jar darwin/
     * brew info tesseract (here you can find path to libtesseract.4.dylib)
     * cp /usr/local/Cellar/tesseract/4.1.1/lib/libtesseract.4.dylib darwin/libtesseract.dylib
     * jar uf tess4j-4.5.4.jar darwin/libtesseract.dylib
     * jar tf tess4j-4.5.4.jar
     */

    @Test
    public void identifyVerificationCodeTest() {
        // 识别图片的路径（修改为自己的图片路径）
        String path = "verifyCodeImg.png";

        // 语言库位置（修改为跟自己语言库文件夹的路径）
        String lagnguagePath = "/Users/Yanni/Automation/tess/Tess4J/tessdata";

        File file = new File(path);
        ITesseract instance = new Tesseract();

        //设置训练库的位置
        instance.setDatapath(lagnguagePath);

        //chi_sim ：简体中文， eng    根据需求选择语言库
        instance.setLanguage("eng");
        String result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = instance.doOCR(file);
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        System.out.println("result:\t" + result);
    }

}
