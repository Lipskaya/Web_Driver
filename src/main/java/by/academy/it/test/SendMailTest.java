package by.academy.it.test;


import by.academy.it.model.Letter;
import by.academy.it.model.User;
import by.academy.it.page.LoginPage;
import by.academy.it.page.MailPage;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SendMailTest extends BaseTest {

  public static final String TOPIC = "test letter";
  public static final String TEXT = "Some message ";
  public static final String EMPTY = "";
  public static final String DRAFT_ADDRESS = "draft@mail.ru";
  private LoginPage loginPage;
  private MailPage mailPage;

  @BeforeTest
  public void beforeTest() {
    getDriver().get(LOGIN_URL);
    loginPage = new LoginPage(getDriver());
    mailPage = new MailPage(getDriver());
    User user = new User();
    user.setLogin(LOGIN);
    user.setPassword(PASSWORD);
    loginPage.doLogin(user);
  }

  @AfterTest
  public void afterTest() {
    // выходим из почты
    loginPage.doLogout();
  }

  @Test(priority = 3)
  //отправка письма на свой же адрес (сама себе)
  public void sendMailTest() {
    Letter letter = new Letter();
    letter.setAddress(LOGIN);
    letter.setTopic(TOPIC);
    letter.setMessage(TEXT);
    mailPage.sendNewMail(letter);
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
    Letter letter = new Letter();
    letter.setAddress(LOGIN);
    letter.setTopic(EMPTY);
    letter.setMessage(EMPTY);
    mailPage.sendNewMail(letter);
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
    Letter letter = new Letter();
    letter.setAddress(EMPTY);
    letter.setTopic(TOPIC);
    letter.setMessage(TEXT);
    mailPage.sendNewMail(letter);
    //проверяем что появилось сообщение об ошибке(Не указан адрес получателя)
    Assert.assertEquals(mailPage.getNoAddressErrorMessage(), "Не указан адрес получателя");
    mailPage.closeNewEmailDialog();
  }

  @Test(priority = 6)
  public void draftMailTest() {
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
