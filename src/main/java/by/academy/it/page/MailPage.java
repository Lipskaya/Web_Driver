package by.academy.it.page;

import by.academy.it.model.Letter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MailPage extends BasePage {

  private static final String NEW_MAIL_BUTTON = "//*[text()=\"Написать письмо\"]";
  private static final String ADDRES_FIELD = "//div[contains(@class, 'fields_container')]//input";
  private static final String TOPIC_FIELD = "//div[contains(@class, 'subject__wrapper')]//input";
  private static final String MAIL_TEXT_FIELD = "//div[@role='textbox']";
  private static final String SEND_BUTTON = "//span[text()=\"Отправить\"]";
  private static final String SENT_EMAILS_BUTTON = "//a[@href=\"/sent/\"]";
  private static final String EMAIL_FROM = "//span[contains(@title, 'kalacheva.tamara@bk.ru')]";
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
  private static final String TITLE = "title";

  public MailPage(WebDriver driver) {
    super(driver);
  }

  //Создает и отправляет новое письмо
  public void sendNewMail(String addres, String topic, String text) {
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
  }
  public void sendNewMail(Letter letter) {

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
  }
  // создает и сохраняет черновик письма
  public void createDraftMail(String addres, String topic, String text) {
    newMail();
    fillAddres(addres);
    fillTopic(topic);
    fillMailText(text);
    saveDraftEmail();
  }

  //написать письмо
  public void newMail() {
    waitVisible(NEW_MAIL_BUTTON).click();
  }

  //заполняет адрес письма "Кому"(адрес)
  public void fillAddres(String addres) {
    WebElement addresField = waitVisible(ADDRES_FIELD);
    addresField.sendKeys(addres);
  }

  //заполняет тему письма
  public void fillTopic(String topic) {
    WebElement topicField = waitVisible(TOPIC_FIELD);
    topicField.sendKeys(topic);
  }

  // заполняет тело письма
  public void fillMailText(String text) {
    WebElement mailText = waitVisible(MAIL_TEXT_FIELD);
    mailText.sendKeys(text);
  }

  //отправляет письмо
  public void sendMail() {
    WebElement sendButton = waitVisible(SEND_BUTTON);
    sendButton.click();
  }

  // заходим в папку отправленные
  public void goToSentEmails() {
    WebElement sentEmailsButton = waitVisible(SENT_EMAILS_BUTTON);
    sentEmailsButton.click();
  }

  //заходим папку входящие
  public void goToInboxEmails() {
    WebElement InboxEmailsButton = waitVisible(INBOX_EMAILS_BUTTON);
    InboxEmailsButton.click();
  }

  //заходим папку Черновики
  public void goToDraftEmails() {
    waitVisible(DRAFT_FOLDER).click();
  }

  //заходим папку Удаленные
  public void goToTrashEmails() {
    waitVisible(TRASH_FOLDER).click();
  }

  //проверяем есть ли письмо от указанного адреса
  public boolean isMailExist(String addres) {
    boolean result = false;
    try {
      String xpathString = E_PREFIX + addres + E_POSTFIX;
      WebElement email = waitVisible(xpathString);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
  }

  //закрываем диалоговое окно об отправке письма
  public void closeSentEmailDialog() {
    WebElement closeDialogButton = waitVisible(CLOSE_DIALOG_BUTTON);
    closeDialogButton.click();
  }

  //удаление всех писем в текущей папке
  public void deleteAllLetters() {
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
  }

  // метод на проверку пустой папки
  public boolean emptyFolder() {
    return waitVisible(EMTY_FOLDER_MESSAGE).isDisplayed();
  }

  //возвращает текст ошибки "Не указан адрес получателя"
  public String getNoAddressErrorMessage() {
    WebElement element = waitVisible(ERROR_MESSAGE);
    return element.getText();
  }

  //закрывает окно нового письма
  public void closeNewEmailDialog() {
    waitVisible(CLOSE_NEW_EMAIL_DIALOG).click();
  }

  // нажимает кнопку "Отменить" в новом письме
  public void cancelEmail() {
    waitVisible(CANCEL_EMAEL).click();
  }

  // сохраняет письмо в черновики
  public void saveDraftEmail() {
    waitVisible(SAVE_DRAFT_EMAIL).click();
    //ждем пока не появится сообщение "Сохранено в черновиках в 16:35"
    waitVisible(NOTIFY_MESSAGE_TEXT);
    //закрыть уведомление
    waitVisible(CLOSE_NOTIFY_MESSAGE).click();

  }

  // получает текст сообщения, что папка пуста
  public String getEmtyFolderMessage() {
    return waitVisible(EMTY_FOLDER_MESSAGE).getText();
  }
}
