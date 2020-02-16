package by.academy.it.service.mail;

import static by.academy.it.page.Constants.INVALID_LOGIN;
import static by.academy.it.page.Constants.INVALID_PASSWORD;
import static by.academy.it.page.Constants.LOGIN;
import static by.academy.it.page.Constants.LOGIN_URL;
import static by.academy.it.page.Constants.PASSWORD;

import by.academy.it.framework.Browser;
import by.academy.it.model.User;
import by.academy.it.page.mail.LoginPage;

public class LoginService {

  private Browser browser;
  private LoginPage loginPage;
  private User validUser;
  private User invalidUser;

  public LoginService() {
    browser = Browser.getInstance();
    loginPage = new LoginPage();
    validUser = new User();
    validUser.setLogin(LOGIN);
    validUser.setPassword(PASSWORD);
    invalidUser = new User();
    invalidUser.setLogin(LOGIN);
    invalidUser.setPassword(PASSWORD);
  }

  public User getValidUser() {
    return validUser;
  }

  public User getInvalidUser() {
    return invalidUser;
  }

  public void doLogin(User user) {
    browser.open(LOGIN_URL);
    loginPage.doLogin(user);
  }

  //логинемся валидным юзером
  public void doLogin() {
    doLogin(validUser);
  }

  public String getLoggedUserMail() {
    return loginPage.getLoggedUserMail();
  }

  public void doLogout() {
    loginPage.doLogout();
  }

  public void enterLogin(String login) {
    browser.open(LOGIN_URL);
    loginPage.enterLogin(login);
  }

  public void enterInvaildLogin() {
    enterLogin(INVALID_LOGIN);
  }

  public void enterVaildLogin() {
    enterLogin(LOGIN);
  }

  public String getErrorMessage() {
    return loginPage.getErrorLoginMessage();
  }

  public String getIncorrectLoginExpectedMessage() {
    return "Такой аккаунт не зарегистрирован";
  }

  public String getIncorrectPasswordExpectedMessage() {
    return "Неверный пароль, попробуйте ещё раз";
  }

  public void enterPassword(String password) {
    loginPage.enterPassword(password);
  }

  public void enterInvalidPassword() {
    loginPage.enterPassword(INVALID_PASSWORD);
  }
}
