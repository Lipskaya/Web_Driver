package by.academy.it.page;

import by.academy.it.model.User;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

  private static final String LOGIN_INPUT_LOCATOR = "//*[contains(@name, 'Login')] ";
  private static final String ENTER_PASSWORD_BUTTON = "//button ";
  private static final String PASSWORD_FIELD = "//*[contains(@name, 'Password') ] ";
  private static final String ENTER_BUTTON = "//*[contains(@type, 'submit') ] ";
  private static final String LOGOUT_LINK = "//*[@id='PH_logoutLink'] ";
  private static final String LOGIN_LINK = "//*[@id='PH_authLink']";
  private static final String LOGGED_USER_MAIL = "//*[@id='PH_user-email']";
  private static final String LOGIN_ERROR_MESSAGE = "//*[@data-test-id='error-footer-text'] ";

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
  public void doLogin(User user) {
    //вызвали метод для ввода логина
    enterLogin(user.getLogin());
    //вводим пароль
    enterPassword(user.getPassword());
    waitVisible(LOGGED_USER_MAIL);
  }

  public void enterPassword(String password) {
    //вводим пароль
    WebElement passwordField = waitVisible(PASSWORD_FIELD);
    passwordField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));

    passwordField.sendKeys(password);
    // ищем кнопку для входа на страницу и жмем ее
    WebElement enterButton = waitVisible(ENTER_BUTTON);
    enterButton.click();

  }

  public void enterLogin(String login) {
    WebElement loginField = waitVisible(LOGIN_INPUT_LOCATOR);
    loginField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    loginField.sendKeys(login);

    // нажимаем по enter password кнопке
    WebElement enterPasswordButton = waitVisible(ENTER_PASSWORD_BUTTON);
    enterPasswordButton.click();
  }

  public void doLogout() {
    WebElement logoutButton = waitVisible(LOGOUT_LINK);
    logoutButton.click();
    waitVisible(LOGIN_LINK);
  }

  // возвращает email залогиненного usera
  public String getLoggedUserMail() {
    WebElement loggedUserMail = waitVisible(LOGGED_USER_MAIL);
    return loggedUserMail.getText();
  }

  // возвращает ошибку не верного логина
  public String getErrorLoginMessage() {
    WebElement errorMessage = waitVisible(LOGIN_ERROR_MESSAGE);
    return errorMessage.getText();
  }


}
