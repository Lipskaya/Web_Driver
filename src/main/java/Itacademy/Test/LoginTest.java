package Itacademy.Test;

import Itacademy.Page.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {


  @Test(priority = 1)
  public void loginToMailTest() {
    getDriver().get(LOGIN_URL);
    LoginPage loginPage = new LoginPage(getDriver());

    loginPage.doLogin(LOGIN, PASSWORD);

    Assert.assertEquals(loginPage.getLoggedUserMail(), LOGIN,
        "Testing of logging with valid data is failed");
    loginPage.doLogout();

  }

  @Test(priority = 2)
  public void negativeLoginToMailTest() {
    getDriver().get("https://account.mail.ru/login");
    LoginPage loginPage = new LoginPage(getDriver());
// проверяем неправильный логин
    loginPage.enterLogin(INVALID_LOGIN);
    Assert.assertEquals(loginPage.getErrorLoginMessage(), "Такой аккаунт не зарегистрирован",
        "Invalid Error message");

// проверяем неправильный пароль
    loginPage.enterLogin(LOGIN);
    loginPage.enterPassword(INVALID_PASSWORD);
    Assert.assertEquals(loginPage.getErrorLoginMessage(), "Неверный пароль, попробуйте ещё раз",
        "Invalid Error message");
  }
}

