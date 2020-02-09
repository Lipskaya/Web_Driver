package by.academy.it.page;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

  private WebDriver driver;
  private Wait<WebDriver> wait;

  public BasePage(WebDriver driver) {
    this.driver = driver;
    wait = new WebDriverWait(driver, 10, 500)
        .withMessage("Element was not found in X seconds");
 //       .ignoring(StaleElementReferenceException.class);
 //   .ignoring(ElementClickInterceptedException.class);
  }

  public WebDriver getDriver() {
    return driver;
  }

  // Ждет пока указанный элемент не появится на странице и не станет видимым (опрос элемента происходит в соответствии с настройками wait)
  public WebElement waitVisible(String xpathLocator) {
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathLocator)));
    return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLocator)));
  }

  // from here: https://stackoverflow.com/questions/15237129/webdriverwait-for-an-element-attribute-to-change
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
  public void waitStaleness(String xpath){
    wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath(xpath))));
  }
  //клик правой кнопкой мыши по элементу
  public void doRightMouseClick (String xPath){
    Actions builder = new Actions(getDriver());
    WebElement element = driver.findElement(By.xpath(xPath));
    builder.contextClick(element).build().perform();
  }
}
