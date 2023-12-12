package by.academy.it.test.mail;


import static by.academy.it.page.Constants.DRAFT_ADDRESS;
import static by.academy.it.page.Constants.LOGIN;

import by.academy.it.service.mail.LoginService;
import by.academy.it.service.mail.MailService;
import by.academy.it.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SendMailTest extends BaseTest {

  private LoginService loginService = new LoginService();
  private MailService mailService = new MailService();

  @BeforeTest
  public void beforeTest() {
    loginService.doLogin();
  }

  @AfterTest
  public void afterTest() {
    // выходим из почты
    loginService.doLogout();
  }

  @Test(priority = 3)
  //отправка письма на свой же адрес (сама себе)
  public void sendMailTest() {
    mailService.sendNewValidMail();
    //переходим в папку отправленные
    mailService.goToSentEmails();
    //проверям есть ли письмо с указанным адресом в текущей папке
    Assert.assertTrue(mailService.isMailExist(LOGIN), "Check mail exists in SENT folder");
    //удаляем все письма с текущей папки
    mailService.deleteAllLetters();
    //переходим папку входящие
    mailService.goToInboxEmails();
    // проверям есть ли письмо с указанным адресом в текущей папке
    Assert.assertTrue(mailService.isMailExist(LOGIN), "Check mail exists in INBOX folder");
    mailService.deleteAllLetters();
  }

  @Test(priority = 4)
  public void sendMainNoSubjectNoBodyTest() {
    mailService.sendNewNoSubjectNoBodyMail();
    // заходим в папку отправленные
    mailService.goToSentEmails();
    //проверям есть ли письмо с указанным адресом в текущей папке(Отправленные)
    Assert.assertTrue(mailService.isMailExist(LOGIN), "Check mail exists in SENT folder");
    //удаляем все письма
    mailService.deleteAllLetters();
    //заходим в папку Входящие
    mailService.goToInboxEmails();
    //проверям есть ли письмо с указанным адресом в текущей папке(Входящие)
    Assert.assertTrue(mailService.isMailExist(LOGIN), "Check mail exists in INBOX folder");
    mailService.deleteAllLetters();
  }

  @Test(priority = 5)
  public void sendMailNoAddressTest() {
    //отправляем письмо без указания адреса
    mailService.sendNoAddressNewMail();
    //проверяем что появилось сообщение об ошибке(Не указан адрес получателя)
    Assert.assertEquals(mailService.getNoAddressErrorMessage(),
        mailService.getExpectedNoAddressErrorMessage());
    mailService.closeNewEmailDialog();
  }

  @Test(priority = 6)
  public void draftMailTest() {
    //создаем черновик письма и сохрняем его
    mailService.createDraftMail();
    //заходим в папку Черновики
    mailService.goToDraftEmails();
    // проверяем есть ли письмо с таким адресом
    Assert.assertTrue(mailService.isMailExist(DRAFT_ADDRESS), "Check mail exists in DRAFTS folder");
    //удаляем все письма из текущей папки
    mailService.deleteAllLetters();
    //захододим в паку Удаленные (Корзина)
    mailService.goToTrashEmails();
    //проверяем есть ли письмо с таким адресом
    Assert.assertTrue(mailService.isMailExist(LOGIN), "Check mail exists in TRASH folder");
    //удаляем все письма из папки, если есть письмо с таким адресом
    mailService.deleteAllLetters();
    //проверяем что текст сообщения = "В корзине пусто"
    Assert.assertEquals(mailService.getEmtyFolderMessage(),
        mailService.getExpectedEmtyFolderMessage());
  }
}
