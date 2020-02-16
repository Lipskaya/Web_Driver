package by.academy.it.page.cloud;

import by.academy.it.framework.Log;
import by.academy.it.page.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CloudPage extends BasePage {

  private static final String CREATE = "//div[text()='Создать']";
  private static final String NEW_FOLDER = "//div[text()='Папку']";
  private static final String FOLDER_NAME = "//input[@placeholder='Введите имя папки']";
  private static final String CREATE_FOLDER_BUTTON = "//button[text()='Создать']";
  private static final String NEW_FOLDER_BREADCRUMB_PREFIX = "// div[@id='breadcrumbs']//span[text()='";
  private static final String NEW_FOLDER_BREADCRUMB_POSTFIX = "']";
  private static final String FOLDER_PATH_PREFIX = "//*[@data-id='/";
  private static final String FOLDER_PATH_POSTFIX = "']/parent::a";
  private static final String DELETE_FOLDER = "//div[text()='Удалить']";
  private static final String APPROVE_FOLDER_REMOVE = "//button[@data-name='remove']";
  private static final String REMOVE_MESSAGE = "//span[text()='Удалено: 1 папка']";
  private static final String CLOSE_DIALOG = "//div[@class='layer_trashbin-tutorial']//button[@data-name='close']";
  private static final String UPLOAD = "//div[contains(@class,'Toolbar__root')]//div[@data-name='upload']";
  private static final String FILE_PATH_INPUT = "//div[contains(@class,'layer_upload__controls__btn-wrapper')]//input[@type='file']";
  private static final String UPLOAD_MESSAGE = "//span[contains(text(),'Загрузка завершена')]";
  private static final String FILE_PATH_PREFIX = "//div[contains(@data-id,'";
  private static final String FILE_PATH_POSTFIX = "') and @data-name='link']";
  private static final String XPATH_PREFIX = "//div[contains(@data-id,'";
  private static final String XPATH_POSTFIX = "') and @data-name='link']";
  private static final String SELECT_FILE_CHECKBOX = "//div[@class='b-checkbox__box']";
  private static final String MOVE_CONFIRM_BUTTON = "// div[@class='layer_move-confirm']//button[@data-name='move']";
  private static final String FOLDER_NAME_PREFIX = "//div[@class='navmenu']//div[@data-id='/";
  private static final String FOLDER_NAME_POSTFIX = "']";
  private static final String ROOT_FOLDER = "//div[@class='navmenu']//a[@href='/home/']";
  private static final String ROOT_FOLDER_BREADCRUMBS = "// div[@id='breadcrumbs']//span[text()='Облако']";
  private static final String SHARE_PREFIX = "//div[contains(@data-id,'";
  private static final String SHARE_POSTFIX = "') and @data-name='link']";
  private static final String PUBLISH_BUTTON = "//button[@data-name='publish']";
  private static final String COPY_LINK = "//input[@title='Скопировать']";
  private static final String SHARED_FILE_PREFIX = "//div[text()='";
  private static final String SHARED_FILE_POSTFIX = "']";
  private static final String CLOSE_DIALOG_COMMERCIAL = "//div[contains(@class, 'Dialog__root_whiteCloseIcon')]/*[name()= 'svg']";
  private static final String EMPTY_FOLDER_MESSAGE = "//div[text()='В этой папке нет содержимого']";
  private static Logger logger = Logger.getLogger(CloudPage.class);

  public CloudPage() {
    super();
    logger.debug("CloudPage constructor finished");
  }

  //создаем папку в облаке
  public void createNewFolder(String name) {
    Log.debugStart(logger, name);

    waitVisible(CREATE).click();
    waitVisible(NEW_FOLDER).click();
    waitVisible(FOLDER_NAME).sendKeys(name);
    waitVisible(CREATE_FOLDER_BUTTON).click();
    logger.debug("PREFIX " + NEW_FOLDER_BREADCRUMB_PREFIX);
    waitVisible(NEW_FOLDER_BREADCRUMB_PREFIX + name + NEW_FOLDER_BREADCRUMB_POSTFIX);

    Log.debugEnd(logger);
  }

  //проверяем, что папка создана
  public boolean isFolderExist(String folderName) {
    logger.debug("isFolderExist(" + folderName + ") start");
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
    logger.debug("isFolderExist(" + folderName + ") end. return:" + result);
    return result;
  }

  //удаляем папку
  public void removeFolder(String name) {
    logger.debug("removeFolder(" + name + ") start");
    String xpathString = FOLDER_PATH_PREFIX + name + FOLDER_PATH_POSTFIX;
    waitVisible(xpathString).click();
    waitVisible(DELETE_FOLDER).click();
    waitVisible(APPROVE_FOLDER_REMOVE).click();
    waitVisible(REMOVE_MESSAGE);
    //закрываем диалог
    waitVisible(CLOSE_DIALOG).click();
    logger.debug("removeFolder(" + name + ") end");
  }

  // загрузить файл
  public void uploadFile(String filePath) {
    logger.debug("removeFolder(" + filePath + ") start");
    waitVisible(UPLOAD).click();
    getDriver().findElement(By.xpath(FILE_PATH_INPUT)).sendKeys(filePath);
    //ждем пока закончится загрузка(10с)
    waitVisible(UPLOAD_MESSAGE);
    logger.debug("removeFolder(" + filePath + ") end");
  }

  //проверяем, что файл загружен
  public boolean isFileUploaded(String fileName) {
    logger.debug("isFileUploaded(" + fileName + ") start");
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
    logger.debug("isFileUploaded(" + fileName + ") end. return:" + result);
    return result;
  }

  // перетягивание файла в папку
  public void moveFileToFolder(String folderName, String fileName) {
    logger.debug("moveFileToFolder(" + folderName + ", " + fileName + ") start");
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
    logger.debug("moveFileToFolder(" + folderName + ", " + fileName + ") end");
  }

  // переходим в указанную папку
  public void goToFolder(String nameFolder) {
    logger.debug("goToFolder(" + nameFolder + ") start");
    waitStaleness(FOLDER_NAME_PREFIX + nameFolder + FOLDER_NAME_POSTFIX);
    waitVisible(FOLDER_NAME_PREFIX + nameFolder + FOLDER_NAME_POSTFIX).click();
    waitVisible(NEW_FOLDER_BREADCRUMB_PREFIX + nameFolder + NEW_FOLDER_BREADCRUMB_POSTFIX);
    logger.debug("goToFolder(" + nameFolder + ") end");
  }

  // переходим в корневую папку
  public void goToRootFolder() {
    logger.debug("goToRootFolder() start");
    waitVisible(ROOT_FOLDER).click();
    waitVisible(ROOT_FOLDER_BREADCRUMBS);
    logger.debug("goToRootFolder() start");
  }

  //возвращает URL на расшаренный файл
  public String getShareLink(String fileName) {
    logger.debug("getShareLink(" + fileName + ") start");
    WebElement file = waitVisible(SHARE_PREFIX + fileName + SHARE_POSTFIX);
    file.findElement(By.xpath(SELECT_FILE_CHECKBOX)).click();
    file.findElement(By.xpath(PUBLISH_BUTTON)).click();
    String result = waitVisible(COPY_LINK).getAttribute("value");
    logger.debug("getShareLink(" + fileName + ") end. return:" + result);
    return result;
  }

  // проверяем есть ли ссылка на скачивание расшаренного файла
  public boolean isSharedLinkExist(String fileName) {
    logger.debug("isSharedLinkExist(" + fileName + ") start");
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
    logger.debug("isSharedLinkExist(" + fileName + ") end. return:" + result);
    return result;
  }

  //закрываем рекламный диалог
  public void closeDialog() {
    logger.debug("closeDialog() start");
    waitVisible(CLOSE_DIALOG_COMMERCIAL).click();
    logger.debug("closeDialog() end");
  }

  //проверям пустая ли папка
  public boolean isFolderEmpty() {
    logger.debug("isFolderEmpty() start");
    boolean result = false;
    try {
      waitVisible(EMPTY_FOLDER_MESSAGE);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    logger.debug("isFolderEmpty() end. return:" + result);
    return result;
  }
}
