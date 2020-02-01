package Itacademy.Test;


import Itacademy.Page.LoginPage;
import Itacademy.Page.MailPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SendMailTest extends BaseTest {

  public static final String TOPIC = "Test letter";
  public static final String TEXT = "Some message ";
  public static final String EMPTY = "";
  public static final String DRAFT_ADDRESS = "draft@mail.ru";

  private LoginPage loginPage;
  private MailPage mailPage;

  @BeforeTest
  public void beforeTest() {
    System.out.println("beforeClass");
    getDriver().get(LOGIN_URL);
    loginPage = new LoginPage(getDriver());
    mailPage = new MailPage(getDriver());
    loginPage.doLogin(LOGIN, PASSWORD);
  }

  @AfterTest
  public void afterTest() {
    System.out.println("afterClass");
    // выходим из почты
    loginPage.doLogout();
  }

  @Test(priority = 3)
  //отправка письма
  public void sendMailTest() {
    mailPage.sendNewMail(LOGIN, TOPIC, TEXT);
    //переходим в папку отправленные
    mailPage.goToSentEmails();
    //проверям есть ли письмо с указанным адресом в текущей папке
    Assert.assertTrue(mailPage.isMailExist(LOGIN), "Check mail exists in SENT folder");
    //удаляем все письма с текущей папки
    mailPage.deleteAllLetters();
    //переходим папку входящие
    mailPage.goToInboxEmails();
    // проверям есть ли письмо с указанным адресом в текущей папке
    Assert.assertTrue(mailPage.isMailExist(LOGIN), "Check mail exists in INBOX folder");
    mailPage.deleteAllLetters();
  }

  @Test(priority = 4)
  public void sendMainNoSubjectNoBodyTest() {
    mailPage.sendNewMail(LOGIN, EMPTY, EMPTY);
    // заходим в папку отправленные
    mailPage.goToSentEmails();
    //проверям есть ли письмо с указанным адресом в текущей папке(Отправленные)
    Assert.assertTrue(mailPage.isMailExist(LOGIN), "Check mail exists in SENT folder");
    //удаляем все письма
    mailPage.deleteAllLetters();
    //заходим в папку Входящие
    mailPage.goToInboxEmails();
    //проверям есть ли письмо с указанным адресом в текущей папке(Входящие)
    Assert.assertTrue(mailPage.isMailExist(LOGIN), "Check mail exists in INBOX folder");
    mailPage.deleteAllLetters();
  }

  @Test(priority = 5)
  public void sendMailNoAddressTest() {
    //отправляем письмо без указания адреса
    mailPage.sendNewMail(EMPTY, TOPIC, TEXT);
    //проверяем что появилось сообщение об ошибке(Не указан адрес получателя)
    Assert.assertEquals(mailPage.getNoAddressErrorMessage(), "Не указан адрес получателя");
    mailPage.closeNewEmailDialog();

  }

  @Test(priority = 6)
  public void DraftMailTest() {
    //создаем черновик письма и сохрняем его
    mailPage.createDraftMail(DRAFT_ADDRESS, TOPIC, TEXT);
    mailPage.closeNewEmailDialog();
    //заходим в папку Черновики
    mailPage.goToDraftEmails();
    // проверяем есть ли письмо с таким адресом
    Assert.assertTrue(mailPage.isMailExist(DRAFT_ADDRESS), "Check mail exists in DRAFTS folder");
    //удаляем все письма из текущей папки
    mailPage.deleteAllLetters();
    //захододим в паку Удаленные (Корзина)
    mailPage.goToTrashEmails();
    //проверяем есть ли письмо с таким адресом
    Assert.assertTrue(mailPage.isMailExist(LOGIN), "Check mail exists in TRASH folder");
    //удаляем все письма из папки, если есть письмо с таким адресом
    mailPage.deleteAllLetters();
    //проверяем что текст сообщения = "В корзине пусто"
    Assert.assertEquals(mailPage.getEmtyFolderMessage(), "В корзине пусто");
  }
}
