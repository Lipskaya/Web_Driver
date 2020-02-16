package by.academy.it.framework;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

// класс отвечающий за настройку веб-дрфйвера
public class Browser implements WrapsDriver {

  //поле для хранения экземпляра Browser класса
  private static Browser instance;
  private static WebDriver driver;
  private static Wait<WebDriver> wait;

  // приватный конструктор, чтобы реализовать синглтон и не позволить создавать new Btowser() вне этого класса
  //По примеру Степана
  private Browser() {
    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    // from here: https://stackoverflow.com/questions/26772793/org-openqa-selenium-unhandledalertexception-unexpected-alert-open
    // ПОзволяет настроить поведение драйвера. В нашем случае он нажимает на кнопку "ПРИНЯТЬ"
    // во всех АЛЕРТАХ которые будут показываться браузером
    DesiredCapabilities dc = new DesiredCapabilities();
    dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
    driver = new ChromeDriver(dc);
    // разворачиваем окно на весь экран
    driver.manage().window().maximize();
    // неявное ожидание браузера в 30 секунд
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    // Создаем и настраиваем объект для ЯВНЫХ ожиданий. Ждет 10 секунд, опрашивая ДОМ каздые 0.5 секунд
    wait = new WebDriverWait(driver, 10, 500)
        .withMessage("Element was not found in X seconds");
  }

  //Метод, который надо вызывать вместо new Browser(), чтобы получить экземпляр синглтора
  public static Browser getInstance() {
    if (instance == null || driver == null) {
      instance = new Browser();
    }
    return instance;
  }

  @Override
  //Единственный метод интерфейса WrapsDriver который нужно реализовать (взято из примера Степана)
  public WebDriver getWrappedDriver() {
    return driver;
  }

  public void stopBrowser() {
    try {
      getInstance().getWrappedDriver().quit();
      getInstance().driver = null;
      instance = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void open(String url) {
    driver.get(url);
  }

  public void refresh() {
    driver.navigate().refresh();
  }

  // from here: https://stackoverflow.com/questions/10660291/highlight-elements-in-webdriver-during-runtime
  // Подсвечиваем элемент красной рамкой
  public WebElement highlightElement(WebElement element) {
    if (driver instanceof JavascriptExecutor) {
      ((JavascriptExecutor) driver)
          .executeScript("arguments[0].style.border='3px solid red'", element);
    }
    return element;
  }

  //клик правой кнопкой мыши по элементу
  public void doRightMouseClick(String xPath) {
    Actions builder = new Actions(driver);
    WebElement element = driver.findElement(By.xpath(xPath));
    builder.contextClick(element).build().perform();
  }

  //ждет пока не произойдет StaleElementReferenceException у элемента (ждет пока элемент не удалиться со странички Жаваскриптом и не будет добавлен на нее снова)
  public void waitStaleness(String xPath) {
    wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath(xPath))));
  }

  // from here: https://stackoverflow.com/questions/15237129/webdriverwait-for-an-element-attribute-to-change
  //ждет пока не появится атрибут
  public boolean waiteForAttribute(final By by, final String atributeName) {
    return wait.until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver driver) {
        WebElement element = driver.findElement(by);
        String attributeValue = element.getAttribute(atributeName);
        if (attributeValue != null) {
          return true;
        } else {
          return false;
        }
      }
    });
  }

  // Ждет пока указанный элемент не появится на странице и не станет видимым (опрос элемента происходит в соответствии с настройками wait)
  public WebElement waitVisible(String xpathLocator) {
    //Ждем пока элемент не появится на странице (presenceOfElementLocated).
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathLocator)));
    //Ждем пока элемент не станет видимым на странице.
    WebElement el = wait
        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLocator)));
    //Этот метод - единственное место через готорое мы обращаеся к элементам страницы. поэтому подсвечиваем только тут.
    el = highlightElement(el);
    return el;
  }
}
