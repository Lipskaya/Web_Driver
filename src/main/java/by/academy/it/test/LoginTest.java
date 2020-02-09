package by.academy.it.test;

import by.academy.it.model.User;
import by.academy.it.page.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

  @Test(priority = 1)
  public void loginToMailTest() {
    getDriver().get(LOGIN_URL);
    LoginPage loginPage = new LoginPage(getDriver());
    User user = new User();
    user.setLogin(LOGIN);
    user.setPassword(PASSWORD);
    loginPage.doLogin(user);
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
