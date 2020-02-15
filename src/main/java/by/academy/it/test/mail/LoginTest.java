package by.academy.it.test.mail;

import by.academy.it.service.mail.LoginService;
import by.academy.it.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

  private LoginService loginService = new LoginService();

  @Test(priority = 1)
  public void loginToMailTest() {
    loginService.doLogin();
    Assert.assertEquals(loginService.getLoggedUserMail(), loginService.getValidUser().getLogin(),
        "Testing of logging with valid data failed");
    loginService.doLogout();
  }

  @Test(priority = 2)
  public void negativeLoginToMailTest() {
    // проверяем неправильный логин
    loginService.enterInvaildLogin();
    Assert.assertEquals(loginService.getErrorMessage(),
        loginService.getIncorrectLoginExpectedMessage(),
        "Invalid Error message");
  }

  @Test(dependsOnMethods = {"negativeLoginToMailTest"})
  public void negativePasswordTest() {
    //Вводим правильный логин
    loginService.enterVaildLogin();
    // проверяем неправильный пароль
    loginService.enterInvalidPassword();
    Assert.assertEquals(loginService.getErrorMessage(),
        loginService.getIncorrectPasswordExpectedMessage(),
        "Invalid Error message");
  }
}
