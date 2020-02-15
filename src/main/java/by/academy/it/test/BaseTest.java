package by.academy.it.test;

import by.academy.it.framework.Browser;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

 private Browser browser;

  @BeforeSuite(alwaysRun = true)//открывает браузер перед выполнением сценария suite
  public void setup() {
    browser = Browser.getInstance();
  }

  @AfterSuite
  public void tearDown() {
    browser.stopBrowser();
  }
}
