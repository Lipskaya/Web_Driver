package by.academy.it.page.mail;

import by.academy.it.model.User;
import by.academy.it.page.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

  private static final String LOGIN_INPUT_LOCATOR = "//*[contains(@name,'Login')]";
  private static final String ENTER_PASSWORD_BUTTON = "//button ";
  private static final String PASSWORD_FIELD = "//*[contains(@name,'Password')]";
  private static final String ENTER_BUTTON = "//*[contains(@type,'submit')]";
  private static final String LOGOUT_LINK = "//*[@id='PH_logoutLink']";
  private static final String LOGIN_LINK = "//*[@id='PH_authLink']";
  private static final String LOGGED_USER_MAIL = "//*[@id='PH_user-email']";
  private static final String LOGIN_ERROR_MESSAGE = "//*[@data-test-id='error-footer-text']";
  private static Logger logger = Logger.getLogger(LoginPage.class);

  public LoginPage() {
    logger.debug("LoginPage constructor finished");
  }

  //выполнит вход в майл по указанному логину и паролю
  public void doLogin(String login, String password) {
    logger.debug("doLogin(" + login + ", " + password + ") start");
    //вызвали метод для ввода логина
    enterLogin(login);
    //вводим пароль
    enterPassword(password);
    waitVisible(LOGGED_USER_MAIL);
    logger.debug("doLogin(" + login + ", " + password + ") end");
  }

  public void doLogin(User user) {
    logger.info("doLogin(" + user.toString() + ") start");
    //вызвали метод для ввода логина
    enterLogin(user.getLogin());
    //вводим пароль
    enterPassword(user.getPassword());
    waitVisible(LOGGED_USER_MAIL);
    logger.info("doLogin(" + user.toString() + ") end");
  }

  public void enterPassword(String password) {
    logger.debug("enterPassword(" + password + ") start");
    //вводим пароль
    WebElement passwordField = waitVisible(PASSWORD_FIELD);
    passwordField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    passwordField.sendKeys(password);
    // ищем кнопку для входа на страницу и жмем ее
    WebElement enterButton = waitVisible(ENTER_BUTTON);
    enterButton.click();
    logger.debug("enterPassword(" + password + ") end");
  }

  public void enterLogin(String login) {
    logger.debug("enterLogin(" + login + ") start");
    WebElement loginField = waitVisible(LOGIN_INPUT_LOCATOR);
    loginField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    loginField.sendKeys(login);
    // нажимаем по enter password кнопке
    WebElement enterPasswordButton = waitVisible(ENTER_PASSWORD_BUTTON);
    enterPasswordButton.click();
    logger.debug("enterLogin(" + login + ") end");
  }

  // выходим из почты
  public void doLogout() {
    logger.debug("doLogout() start");
    WebElement logoutButton = waitVisible(LOGOUT_LINK);
    logoutButton.click();
    waitVisible(LOGIN_LINK);
    logger.debug("doLogout() end");
  }

  // возвращает email залогиненного usera
  public String getLoggedUserMail() {
    logger.debug("getLoggedUserMail() start");
    WebElement loggedUserMail = waitVisible(LOGGED_USER_MAIL);
    logger.debug("getLoggedUserMail() end. return:" + loggedUserMail.getText());
    return loggedUserMail.getText();
  }

  // возвращает ошибку не верного логина
  public String getErrorLoginMessage() {
    logger.debug("getErrorMessage() start");
    WebElement errorMessage = waitVisible(LOGIN_ERROR_MESSAGE);
    logger.debug("getErrorMessage() end. return:" + errorMessage.getText());
    return errorMessage.getText();
  }
}
