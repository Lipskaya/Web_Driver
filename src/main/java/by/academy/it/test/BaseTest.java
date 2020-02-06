package by.academy.it.test;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

  public static final String LOGIN = "kalacheva.tamara@bk.ru";
  public static final String PASSWORD = "&YPviFA1zpu1";
  public static final String INVALID_LOGIN = "kalachevaaaa.tamara@bk.ru";
  public static final String INVALID_PASSWORD = "&YPviFA1zpu1po";
  public static final String LOGIN_URL = "https://account.mail.ru/login";

  public static final String CLOUD_URL = "https://cloud.mail.ru";

  public static WebDriver driver = null;


  public WebDriver getDriver() {
    return driver;
  }

  @BeforeSuite(alwaysRun = true)//открывает браузер перед выполнением сценария suite
  public void setup() {
    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

    // from here: https://stackoverflow.com/questions/26772793/org-openqa-selenium-unhandledalertexception-unexpected-alert-open
    DesiredCapabilities dc = new DesiredCapabilities();
    dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);

    driver = new ChromeDriver(dc);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

  }

  @AfterSuite
  public void tearDown() {
    getDriver().quit();
  }

}
