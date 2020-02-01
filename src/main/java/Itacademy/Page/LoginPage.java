package Itacademy.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

  private static final By LOGIN_INPUT_LOCATOR = By.xpath("//*[contains(@name, 'Login')]");
  private static final By ENTER_PASSWORD_BUTTON = By.xpath("//button");
  private static final By PASSWORD_FIELD = By.xpath("//*[contains(@name, 'Password') ]");
  private static final By ENTER_BUTTON = By.xpath("//*[contains(@type, 'submit') ]");
  private static final By LOGOUT_LINK = By.xpath("//*[@id='PH_logoutLink']");
  private static final By LOGIN_LINK = By.xpath("//*[@id='PH_authLink']");
  private static final By LOGGED_USER_MAIL = By.xpath("//*[@id='PH_user-email']");
  private static final By LOGIN_ERROR_MESSAGE = By
      .xpath("//*[@data-test-id='error-footer-text']");

  public LoginPage(WebDriver driver) {
    super(driver);
  }

  //выполнит вход в майл по указанному логину и паролю
  public void doLogin(String login, String password) {
    //вызвали метод для ввода логина
    enterLogin(login);
    //вводим пароль
    enterPassword(password);
    waitVisible(LOGGED_USER_MAIL);
  }

  public void enterPassword(String password) {
    //вводим пароль
    WebElement passwordField = getDriver().findElement(PASSWORD_FIELD);
    passwordField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));

    passwordField.sendKeys(password);
    // ищем кнопку для входа на страницу и жмем ее
    WebElement enterButton = getDriver().findElement(ENTER_BUTTON);
    enterButton.click();

  }

  public void enterLogin(String login) {
    WebElement loginField = getDriver().findElement(LOGIN_INPUT_LOCATOR);
    loginField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    loginField.sendKeys(login);

    // нажимаем по enter password кнопке
    WebElement enterPasswordButton = getDriver().findElement(ENTER_PASSWORD_BUTTON);
    enterPasswordButton.click();
  }

  public void doLogout() {
    WebElement logoutButton = getDriver().findElement(LOGOUT_LINK);
    logoutButton.click();
    waitVisible(LOGIN_LINK);
  }

  // возвращает email залогиненного usera
  public String getLoggedUserMail() {
    WebElement loggedUserMail = getDriver().findElement(LOGGED_USER_MAIL);
    return loggedUserMail.getText();
  }

  // возвращает ошибку не верного логина
  public String getErrorLoginMessage() {
    WebElement errorMessage = getDriver().findElement(LOGIN_ERROR_MESSAGE);
    return errorMessage.getText();
  }


}
