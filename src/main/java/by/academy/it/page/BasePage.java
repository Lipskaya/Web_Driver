package by.academy.it.page;

import by.academy.it.framework.Browser;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

  private static Logger logger = Logger.getLogger(BasePage.class);
  private Browser browser;

  public BasePage() {
    logger.debug("BasePage constructor start");
    browser = Browser.getInstance();
    logger.debug("BasePage constructor end");
  }

  public WebDriver getDriver() {
    logger.debug("getDriver returns driver instance");
    return browser.getWrappedDriver();
  }

  // Ждет пока указанный элемент не появится на странице и не станет видимым (опрос элемента происходит в соответствии с настройками wait)
  public WebElement waitVisible(String xpathLocator) {
    logger.debug("waitVisible(" + xpathLocator + ") start");
    WebElement el = browser.waitVisible(xpathLocator);
    logger.debug("waitVisible(" + xpathLocator + ") end. return:" + el.toString());
    return el;
  }

  //ждет пока не произойдет StaleElementReferenceException у элемента
  public void waitStaleness(String xPath) {
    logger.debug("waitStaleness(" + xPath + ") start");
    browser.waitStaleness(xPath);
    logger.debug("waitStaleness(" + xPath + ") end");
  }

}
