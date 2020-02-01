package Itacademy.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MailPage extends BasePage {

  private static final By NEW_MAIL_BUTTON = By.xpath("//*[text()=\"Написать письмо\"]");
  private static final By ADDRES_FIELD = By
      .xpath("//div[contains(@class, 'fields_container')]//input");
  private static final By TOPIC_FIELD = By
      .xpath("//div[contains(@class, 'subject__wrapper')]//input");
  private static final By MAIL_TEXT_FIELD = By.xpath("//div[@role='textbox']");
  private static final By SEND_BUTTON = By.xpath("//span[text()=\"Отправить\"]");
  private static final By SENT_EMAILS_BUTTON = By.xpath("//a[@href=\"/sent/\"]");
  private static final By EMAIL_FROM = By
      .xpath("//span[contains(@title, 'kalacheva.tamara@bk.ru')]");
  private static final By INBOX_EMAILS_BUTTON = By.xpath("//a[@href=\"/inbox/\"]");
  private static final By CLOSE_DIALOG_BUTTON = By.xpath("//span[@title='Закрыть']");
  private static final By SELECT_ALL_BUTTON = By.xpath("//span[@title='Выделить все']/parent::div");
  private static final By DELETE_EMAILS_BUTTON = By.xpath("//span[@title=\"Удалить\"]/parent::div");
  private static final String INBOX_URL = "https://e.mail.ru/inbox/";
  private static final By EMTY_FOLDER_MESSAGE = By.xpath("//div[@class='octopus__text']");
  private static final By CLEAR_EMAILS_ELEMENT = By
      .xpath("//div[@class='layer__submit-button']//span[text()='Очистить']");
  private static final By ERROR_MESSAGE = By.xpath("//div[contains(@class, 'rowError')]");
  private static final By CLOSE_NEW_EMAIL_DIALOG = By
      .xpath("//button[contains(@title, 'Закрыть')]");
  private static final By CANCEL_EMAEL = By.xpath("//span[text()='Отменить']");
  private static final By SAVE_DRAFT_EMAIL = By.xpath("//span[text()='Сохранить']");
  private static final By NOTIFY_MESSAGE_TEXT = By
      .xpath("//span[@class='notify__message__text']");
  private static final By CLOSE_NOTIFY_MESSAGE = By
      .xpath("//div[contains(@class,'notify__body')]//div[@class='notify__ico-close']");
  private static final By SEND_EMPTY_BOOTON = By.xpath(
      "//div[@data-test-id='confirmation:empty-letter']//span[text()='Отправить']/parent::button");
  private static final By DRAFT_FOLDER = By.xpath("//a[@href='/drafts/']");
  private static final By TRASH_FOLDER = By.xpath("//a[@href='/trash/']");
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
    WebElement addresField = getDriver().findElement(ADDRES_FIELD);
    addresField.sendKeys(addres);
  }

  //заполняет тему письма
  public void fillTopic(String topic) {
    WebElement topicField = getDriver().findElement(TOPIC_FIELD);
    topicField.sendKeys(topic);
  }

  // заполняет тело письма
  public void fillMailText(String text) {
    WebElement mailText = getDriver().findElement(MAIL_TEXT_FIELD);
    mailText.sendKeys(text);
  }

  //отправляет письмо
  public void sendMail() {
    WebElement sendButton = getDriver().findElement(SEND_BUTTON);
    sendButton.click();
  }

  // заходим в папку отправленные
  public void goToSentEmails() {
    WebElement sentEmailsButton = getDriver().findElement(SENT_EMAILS_BUTTON);
    sentEmailsButton.click();
  }

  //заходим папку входящие
  public void goToInboxEmails() {
    WebElement InboxEmailsButton = getDriver().findElement(INBOX_EMAILS_BUTTON);
    InboxEmailsButton.click();
  }

  //заходим папку Черновики
  public void goToDraftEmails() {
    getDriver().findElement(DRAFT_FOLDER).click();
  }

  //заходим папку Удаленные
  public void goToTrashEmails() {
    getDriver().findElement(TRASH_FOLDER).click();
  }

  //проверяем есть ли письмо от указанного адреса
  public boolean isMailExist(String addres) {
    boolean result = false;
    try {
      By by = By.xpath(E_PREFIX + addres + E_POSTFIX);
      WebElement email = waitVisible(by);
      if (email.getAttribute(TITLE).contains(addres)) {
        result = true;
      }
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
    return getDriver().findElement(EMTY_FOLDER_MESSAGE).isDisplayed();
  }

  //возвращает текст ошибки "Не указан адрес получателя"
  public String getNoAddressErrorMessage() {
    WebElement element = waitVisible(ERROR_MESSAGE);
    return element.getText();
  }

  //закрывает окно нового письма
  public void closeNewEmailDialog() {
    getDriver().findElement(CLOSE_NEW_EMAIL_DIALOG).click();
  }

  // нажимает кнопку "Отменить" в новом письме
  public void cancelEmail() {
    getDriver().findElement(CANCEL_EMAEL).click();
  }

  // сохраняет письмо в черновики
  public void saveDraftEmail() {
    getDriver().findElement(SAVE_DRAFT_EMAIL).click();
    //ждем пока не появится сообщение "Сохранено в черновиках в 16:35"
    waitVisible(NOTIFY_MESSAGE_TEXT);
    //закрыть уведомление
    waitVisible(CLOSE_NOTIFY_MESSAGE).click();

  }

  // получает текст сообщения, что папка пуста
  public String getEmtyFolderMessage() {
    return getDriver().findElement(EMTY_FOLDER_MESSAGE).getText();
  }
}
