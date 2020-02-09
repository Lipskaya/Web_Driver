package by.academy.it.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CloudPage extends BasePage {

  private final String CREATE = "//div[text()='Создать']";
  private final String NEW_FOLDER = "//div[text()='Папку']";
  private final String FOLDER_NAME = "//input[@placeholder='Введите имя папки']";
  private final String CREATE_FOLDER_BUTTON = "//button[text()='Создать']";
  private final String NEW_FOLDER_BREADCRUMB_PREFIX = "// div[@id='breadcrumbs']//span[text()='";
  private final String NEW_FOLDER_BREADCRUMB_POSTFIX = "']";
  private final String FOLDER_PATH_PREFIX = "//*[@data-id='/";
  private final String FOLDER_PATH_POSTFIX = "']/parent::a";
  private final String DELETE_FOLDER = "//div[text()='Удалить']";
  private final String APPROVE_FOLDER_REMOVE = "//button[@data-name='remove']";
  private final String REMOVE_MESSAGE = "//span[text()='Удалено: 1 папка']";
  private final String CLOSE_DIALOG = "//div[@class='layer_trashbin-tutorial']//button[@data-name='close']";
  private final String UPLOAD = "//div[contains(@class,'Toolbar__root')]//div[@data-name='upload']";
  private final String FILE_PATH_INPUT = "//div[contains(@class,'layer_upload__controls__btn-wrapper')]//input[@type='file']";
  private final String UPLOAD_MESSAGE = "//span[contains(text(),'Загрузка завершена')]";
  private final String FILE_PATH_PREFIX = "//div[contains(@data-id,'";
  private final String FILE_PATH_POSTFIX = "') and @data-name='link']";
  private final String XPATH_PREFIX = "//div[contains(@data-id,'";
  private final String XPATH_POSTFIX = "') and @data-name='link']";
  private final String SELECT_FILE_CHECKBOX = "//div[@class='b-checkbox__box']";
  private final String MOVE_CONFIRM_BUTTON = "// div[@class='layer_move-confirm']//button[@data-name='move']";
  private final String FOLDER_NAME_PREFIX = "//div[@class='navmenu']//div[@data-id='/";
  private final String FOLDER_NAME_POSTFIX = "']";
  private final String ROOT_FOLDER = "//div[@class='navmenu']//a[@href='/home/']";
  private final String ROOT_FOLDER_BREADCRUMBS = "// div[@id='breadcrumbs']//span[text()='Облако']";
  private final String SHARE_PREFIX = "//div[contains(@data-id,'";
  private final String SHARE_POSTFIX = "') and @data-name='link']";
  private final String PUBLISH_BUTTON = "//button[@data-name='publish']";
  private final String COPY_LINK = "//input[@title='Скопировать']";
  private final String SHARED_FILE_PREFIX = "//div[text()='";
  private final String SHARED_FILE_POSTFIX = "']";
  private final String CLOSE_DIALOG_COMMERCIAL = "//div[contains(@class, 'Dialog__root_whiteCloseIcon')]/*[name()= 'svg']";
  private final String EMPTY_FOLDER_MESSAGE = "//div[text()='В этой папке нет содержимого']";

  public CloudPage(WebDriver driver) {
    super(driver);
  }

  //создаем папку в облаке
  public void createNewFolder(String name) {
    waitVisible(CREATE).click();
    waitVisible(NEW_FOLDER).click();
    waitVisible(FOLDER_NAME).sendKeys(name);
    waitVisible(CREATE_FOLDER_BUTTON).click();
    waitVisible(NEW_FOLDER_BREADCRUMB_PREFIX + name + NEW_FOLDER_BREADCRUMB_POSTFIX);
  }

  //проверяем, что папка создана
  public boolean isFolderExist(String folderName) {
    boolean result = false;
    try {
      //генерим динамический xPath для переданного в параметре метода имени папки
      String xpathString = FOLDER_PATH_PREFIX + folderName + FOLDER_PATH_POSTFIX;
      waitVisible(xpathString);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
  }

  //удаляем папку
  public void removeFolder(String name) {
    String xpathString = FOLDER_PATH_PREFIX + name + FOLDER_PATH_POSTFIX;
    waitVisible(xpathString).click();
    waitVisible(DELETE_FOLDER).click();
    waitVisible(APPROVE_FOLDER_REMOVE).click();
    waitVisible(REMOVE_MESSAGE);
    //закрываем диалог
    waitVisible(CLOSE_DIALOG).click();
  }

  // загрузить файл
  public void uploadFile(String filePath) {
    waitVisible(UPLOAD).click();
    getDriver().findElement(By.xpath(FILE_PATH_INPUT)).sendKeys(filePath);
    //ждем пока закончится загрузка(10с)
    waitVisible(UPLOAD_MESSAGE);
  }

  //проверяем, что файл загружен
  public boolean isFileUploaded(String fileName) {
    boolean result = false;
    try {
      //генерим динамический xPath для переданного в параметре метода имени папки
      String xpathString = FILE_PATH_PREFIX + fileName + FILE_PATH_POSTFIX;
      waitVisible(xpathString);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
  }

  // перетягивание файла в папку
  public void moveFileToFolder(String folderName, String fileName) {
    WebElement file = waitVisible(XPATH_PREFIX + fileName + XPATH_POSTFIX);
    WebElement folder = waitVisible(XPATH_PREFIX + folderName + XPATH_POSTFIX);
    file.findElement(By.xpath(SELECT_FILE_CHECKBOX)).click();
    Actions builder = new Actions(getDriver());
    builder.clickAndHold(file)
        .moveToElement(folder)
        .moveByOffset(5, 5)
        .release()
        .build()
        .perform();
    waitVisible(MOVE_CONFIRM_BUTTON).click();
  }

  // переходим в указанную папку
  public void goToFolder(String nameFolder) {
    waitStaleness(FOLDER_NAME_PREFIX + nameFolder + FOLDER_NAME_POSTFIX);
    waitVisible(FOLDER_NAME_PREFIX + nameFolder + FOLDER_NAME_POSTFIX).click();
    waitVisible(NEW_FOLDER_BREADCRUMB_PREFIX + nameFolder + NEW_FOLDER_BREADCRUMB_POSTFIX);
  }

  // переходим в корневую папку
  public void goToRootFolder() {
    waitVisible(ROOT_FOLDER).click();
    waitVisible(ROOT_FOLDER_BREADCRUMBS);
  }

  //возвращает URL на расшаренный файл
  public String getShareLink(String fileName) {
    WebElement file = waitVisible(SHARE_PREFIX + fileName + SHARE_POSTFIX);
    file.findElement(By.xpath(SELECT_FILE_CHECKBOX));
    file.findElement(By.xpath(PUBLISH_BUTTON)).click();
    return waitVisible(COPY_LINK).getAttribute("value");
  }

  // проверяем есть ли ссылка на скачивание расшаренного файла
  public boolean isSharedLinkExist(String fileName) {
    boolean result = false;
    try {
      //генерим динамический xPath для переданного в параметре метода имени файла
      String xpathString = SHARED_FILE_PREFIX + fileName + SHARED_FILE_POSTFIX;
      waitVisible(xpathString);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
  }

  //закрываем рекламный диалог
  public void closeDialog() {
    waitVisible(CLOSE_DIALOG_COMMERCIAL).click();
  }

  //
  public boolean isFolderEmpty() {
    boolean result = false;
    try {
      waitVisible(EMPTY_FOLDER_MESSAGE);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
  }
}
