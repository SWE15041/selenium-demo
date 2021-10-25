install driver

brew install --cask chromedriver

定位元素的方式（使用优先级）
- id
- name
- css selector
- xpath
- class name
- tag name
- link text
- partial link text

org.openqa.selenium.By(***)

findElements(By.id())
findElements(By.name())
findElements(By.className())
findElements(By.tagName())
findElements(By.linkText())
findElements(By.partialLinkText())
findElements(By.xpath())
findElements(By.cssSelector())

xpath
1. id定位 -- driver.find_element_by_xpath('//input[@id="kw"]')
2. class定位 -- driver.find_element_by_xpath('//input[@class="s_ipt"]')
3. 相对定位 -- 以// 开头 如：//form//input[@name="phone"]
4. 绝对定位 -- 以/ 开头，但是要从根目录开始，比较繁琐，一般不建议使用 如：/html/body/div/a
5. 文本定位 -- 使用text()元素的text内容 如：//button[text()="登录"]
6. 模糊定位一 -- 使用contains() 包含函数 如：//button[contains(text(),"登录")]、//button[contains(@class,"btn")] 除了contains不是=等于
7. 模糊定位二 -- 使用starts-with，匹配以xx开头的属性值；ends-with 匹配以xx结尾的属性值 如：//button[starts-with(@class,"btn")]、//input[ends-with(@class,"-special")]
8. 逻辑运算符 -- and、or；如：//input[@name="phone" and @datatype="m"]
9. 轴定位 -- `使用语法： 轴名称 :: 节点名称` -- `//input[@id=‘kw’]//ancestor::span` -- `//input[@id=‘kw’]//parent::span 同等于 //input[@id=‘kw’]//…`


   ``` 
    轴运算
       ancestor：祖先节点 包括父
       parent：父节点
       preceding-sibling：当前元素节点标签之前的所有兄弟节点
       preceding：当前元素节点标签之前的所有节点
       following-sibling:当前元素节点标签之后的所有兄弟节点
       following：当前元素节点标签之后的所有节点
   ```
https://www.cnblogs.com/fcc-123/p/10930231.html
    




## selenium 和 Appium 是的关系
1. selenium是专门做web端的自动化测试工具
2. appium是手机app端的自动化，它继承了webdriver(也就是selenium 2)。不过appium仍然需要通过selenium最后做测试工具，但是appium起到了一个连接手机端非常好的桥梁工作！

Selenium 测试直接在浏览器中运行，就像真实用户所做的一样。
Selenium 测试可以在 Windows、Linux 和 Macintosh上的 Internet Explorer、Chrome和 Firefox 中运行。
其他测试工具都不能覆盖如此多的平台。使用 Selenium 和在浏览器中运行测试还有很多其他好处。

通过编写模仿用户操作的 Selenium 测试脚本，可以从终端用户的角度来测试应用程序。
通过在不同浏览器中运行测试，更容易发现浏览器的不兼容性。
Selenium 的核心，也称browser bot，是用 JavaScript 编写的。
这使得测试脚本可以在受支持的浏览器中运行。
browser bot 负责执行从测试脚本接收到的命令，测试脚本要么是用 HTML 的表布局编写的，要么是使用一种受支持的编程语言编写的。

二 、appium是手机app端的自动化，它继承了webdriver(也就是selenium 2)
不过appium仍然需要通过selenium最后做测试工具，但是appium起到了一个连接手机端非常好的桥梁工作！可以连接到电脑上非常方便的调用selenium工具来做测试。


## 在selenium中处理多个弹出窗口的机制是什么？
可以使用命令getWindowHandles()来处理多个弹出窗口。
然后将所有窗口名称存储到Set变量中并将其转换为数组。
接下来，通过使用数组索引，导航到特定的窗口。
driver.switchTo().window(ArrayIndex);


## 如何处理Selenium WebDriver中的警报/弹出窗口？
常见有两种类型的警报：
- 基于Windows的警报弹出窗口 （不支持处理）
- 基于Web的警报弹出窗口 （支持处理）

WebDriver为用户提供了一种使用Alert界面处理这些弹出窗口的非常有效的方法。
- dismiss()，执行点击“Cancel”按钮。
- accept() ，点击“Ok”按钮。
- getText() 返回警告框中显示的文本。
- sendKeys(String） 将指定的字符串模式输入到警告框中

Selenium是一个自动化测试工具，它只支持Web应用程序测试，不支持基于Windows的应用程序，窗口警报就是其中之一。
Robot class是基于Java的实用程序，它模拟键盘和鼠标操作，并可以有效地用于处理基于windows 的弹出与键盘事件的帮助。
KeyPress和KkeyRelease方法可以分别模拟用户按下和释放键盘上某个键的操作。








