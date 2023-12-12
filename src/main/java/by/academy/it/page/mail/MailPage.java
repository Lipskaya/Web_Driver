package by.academy.it.page.mail;

import by.academy.it.model.Letter;
import by.academy.it.page.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MailPage extends BasePage {

  private static final String NEW_MAIL_BUTTON = "//*[text()=\"Написать письмо\"]";
  private static final String ADDRES_FIELD = "//div[contains(@class, 'fields_container')]//input";
  private static final String TOPIC_FIELD = "//div[contains(@class, 'subject__wrapper')]//input";
  private static final String MAIL_TEXT_FIELD = "//div[@role='textbox']";
  private static final String SEND_BUTTON = "//span[text()=\"Отправить\"]";
  private static final String SENT_EMAILS_BUTTON = "//a[@href=\"/sent/\"]";
  private static final String INBOX_EMAILS_BUTTON = "//a[@href=\"/inbox/\"]";
  private static final String CLOSE_DIALOG_BUTTON = "//span[@title='Закрыть']";
  private static final String SELECT_ALL_BUTTON = "//span[@title='Выделить все']/parent::div";
  private static final String DELETE_EMAILS_BUTTON = "//span[@title=\"Удалить\"]/parent::div";
  private static final String INBOX_URL = "https://e.mail.ru/inbox/";
  private static final String EMTY_FOLDER_MESSAGE = "//div[@class='octopus__text']";
  private static final String CLEAR_EMAILS_ELEMENT = "//div[@class='layer__submit-button']//span[text()='Очистить']";
  private static final String ERROR_MESSAGE = "//div[contains(@class, 'rowError')]";
  private static final String CLOSE_NEW_EMAIL_DIALOG = "//button[contains(@title, 'Закрыть')]";
  private static final String CANCEL_EMAEL = "//span[text()='Отменить']";
  private static final String SAVE_DRAFT_EMAIL = "//span[text()='Сохранить']";
  private static final String NOTIFY_MESSAGE_TEXT = "//span[@class='notify__message__text']";
  private static final String CLOSE_NOTIFY_MESSAGE = "//div[contains(@class,'notify__body')]//div[@class='notify__ico-close']";
  private static final String SEND_EMPTY_BOOTON = "//div[@data-test-id='confirmation:empty-letter']//span[text()='Отправить']/parent::button";
  private static final String DRAFT_FOLDER = "//a[@href='/drafts/']";
  private static final String TRASH_FOLDER = "//a[@href='/trash/']";
  private static final String E_PREFIX = "//span[contains(@title, '";
  private static final String E_POSTFIX = "')]";
  private static Logger logger = Logger.getLogger(MailPage.class);

  public MailPage() {
    super();
    logger.debug("MailPage constructor finished");
  }

  //Создает и отправляет новое письмо
  public void sendNewMail(String addres, String topic, String text) {
    logger.debug("sendNewMail() start");
    newMail();
    fillAddres(addres);
    fillTopic(topic);
    fillMailText(text);
    sendMail();
    if (addres.trim().isEmpty()) {
      return;
    }
    if (text.trim().isEmpty()) {
      // подтверждаем отправку пустого письма
      WebElement sendEmtyButton = waitVisible(SEND_EMPTY_BOOTON);
      sendEmtyButton.click();
    }
    closeSentEmailDialog();
    logger.debug("sendNewMail() end");
  }

  // отправляет новое письмо
  public void sendNewMail(Letter letter) {
    logger.debug("sendNewMail() start");
    newMail();
    fillAddres(letter.getAddress());
    fillTopic(letter.getTopic());
    fillMailText(letter.getMessage());
    sendMail();
    if (letter.getAddress().trim().isEmpty()) {
      return;
    }
    if (letter.getMessage().trim().isEmpty()) {
      // подтверждаем отправку пустого письма
      WebElement sendEmtyButton = waitVisible(SEND_EMPTY_BOOTON);
      sendEmtyButton.click();
    }
    closeSentEmailDialog();
    logger.debug("sendNewMail() end");
  }

  // создает и сохраняет черновик письма
  public void createDraftMail(String addres, String topic, String text) {
    logger.debug("createDraftMail() start");
    newMail();
    fillAddres(addres);
    fillTopic(topic);
    fillMailText(text);
    saveDraftEmail();
    logger.debug("createDraftMail() end");
  }

  //написать письмо
  public void newMail() {
    logger.debug("newMail() start");
    waitVisible(NEW_MAIL_BUTTON).click();
    logger.debug("newMail() end");
  }

  //заполняет адрес письма "Кому"(адрес)
  public void fillAddres(String addres) {
    logger.debug("fillAddres() start");
    WebElement addresField = waitVisible(ADDRES_FIELD);
    addresField.sendKeys(addres);
    logger.debug("fillAddres() end");
  }

  //заполняет тему письма
  public void fillTopic(String topic) {
    logger.debug("fillTopic() start");
    WebElement topicField = waitVisible(TOPIC_FIELD);
    topicField.sendKeys(topic);
    logger.debug("fillTopic() end");
  }

  // заполняет тело письма
  public void fillMailText(String text) {
    logger.debug("fillMailText() start");
    WebElement mailText = waitVisible(MAIL_TEXT_FIELD);
    mailText.sendKeys(text);
    logger.debug("fillMailText() end");
  }

  //отправляет письмо
  public void sendMail() {
    logger.debug("sendMail() start");
    WebElement sendButton = waitVisible(SEND_BUTTON);
    sendButton.click();
    logger.debug("sendMail() end");
  }

  // заходим в папку отправленные
  public void goToSentEmails() {
    logger.debug("goToSentEmails() start");
    WebElement sentEmailsButton = waitVisible(SENT_EMAILS_BUTTON);
    Actions builder = new Actions(getDriver());
    builder.moveToElement(sentEmailsButton)
        .click()
        .pause(500)
        .build()
        .perform();
    //   sentEmailsButton.click();
    logger.debug("goToSentEmails() end");
  }

  //заходим папку входящие
  public void goToInboxEmails() {
    logger.debug("goToInboxEmails() start");
    WebElement InboxEmailsButton = waitVisible(INBOX_EMAILS_BUTTON);
    InboxEmailsButton.click();
    logger.debug("goToInboxEmails() end");
  }

  //заходим папку Черновики
  public void goToDraftEmails() {
    logger.debug("goToDraftEmails() start");
    waitVisible(DRAFT_FOLDER).click();
    logger.debug("goToDraftEmails() end");
  }

  //заходим папку Удаленные
  public void goToTrashEmails() {
    logger.debug("goToTrashEmails() start");
    waitVisible(TRASH_FOLDER).click();
    logger.debug("goToTrashEmails() end");
  }

  //проверяем есть ли письмо от указанного адреса
  public boolean isMailExist(String addres) {
    logger.debug("isMailExist() start");
    boolean result = false;
    try {
      String xpathString = E_PREFIX + addres + E_POSTFIX;
      //     waitStaleness(xpathString);
      WebElement email = waitVisible(xpathString);
      result = true;
    } catch (Exception e) {
      logger.error("Error while checking if mail exists", e);
      e.printStackTrace();
      result = false;
    }
    logger.debug("isMailExist() end");
    return result;
  }

  //закрываем диалоговое окно об отправке письма
  public void closeSentEmailDialog() {
    logger.debug("closeSentEmailDialog() start");
    WebElement closeDialogButton = waitVisible(CLOSE_DIALOG_BUTTON);
    closeDialogButton.click();
    logger.debug("closeSentEmailDialog() start");
  }

  //удаление всех писем в текущей папке
  public void deleteAllLetters() {
    logger.debug("deleteAllLetters() start");
    WebElement selectALL = waitVisible(SELECT_ALL_BUTTON);
    selectALL.click();
    WebElement deleteElement = waitVisible(DELETE_EMAILS_BUTTON);
    deleteElement.click();
    //Кейс для папки ИНБОКС (нужно закрыть диалоговое окно и подтвердить удаление писем)
    String url = getDriver().getCurrentUrl();
    if (url.equals(INBOX_URL)) {
      try {
        WebElement clearEmailsElement = waitVisible(CLEAR_EMAILS_ELEMENT);
        clearEmailsElement.click();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    waitVisible(NOTIFY_MESSAGE_TEXT);
    waitVisible(CLOSE_NOTIFY_MESSAGE).click();
    logger.debug("deleteAllLetters() end");
  }

  // метод на проверку пустой папки
  public boolean emptyFolder() {
    logger.debug("emptyFolder() check if folder is empty");
    return waitVisible(EMTY_FOLDER_MESSAGE).isDisplayed();
  }

  //возвращает текст ошибки "Не указан адрес получателя"
  public String getNoAddressErrorMessage() {
    logger.debug("getNoAddressErrorMessage() start");
    WebElement element = waitVisible(ERROR_MESSAGE);
    logger.debug("getNoAddressErrorMessage() end");
    return element.getText();
  }

  //закрывает окно нового письма
  public void closeNewEmailDialog() {
    logger.debug("closeNewEmailDialog() start");
    waitVisible(CLOSE_NEW_EMAIL_DIALOG).click();
    logger.debug("closeNewEmailDialog() end");
  }

  // нажимает кнопку "Отменить" в новом письме
  public void cancelEmail() {
    logger.debug("cancelEmail() start");
    waitVisible(CANCEL_EMAEL).click();
    logger.debug("cancelEmail() end");
  }

  // сохраняет письмо в черновики
  public void saveDraftEmail() {
    logger.debug("saveDraftEmail() start");
    waitVisible(SAVE_DRAFT_EMAIL).click();
    //ждем пока не появится сообщение "Сохранено в черновиках в 16:35"
    waitVisible(NOTIFY_MESSAGE_TEXT);
    //закрыть уведомление
    waitVisible(CLOSE_NOTIFY_MESSAGE).click();
    logger.debug("saveDraftEmail() end");
  }

  // получает текст сообщения, что папка пуста
  public String getEmtyFolderMessage() {
    logger.debug("getEmtyFolderMessage() returns message");
    return waitVisible(EMTY_FOLDER_MESSAGE).getText();
  }
}
