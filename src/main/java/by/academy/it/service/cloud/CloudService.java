package by.academy.it.service.cloud;

import static by.academy.it.page.Constants.CLOUD_URL;

import by.academy.it.framework.Browser;
import by.academy.it.page.cloud.CloudPage;
import by.academy.it.service.mail.LoginService;

public class CloudService {

  private LoginService loginService;
  private Browser browser;
  private CloudPage cloudPage;

  public CloudService() {
    loginService = new LoginService();
    browser = Browser.getInstance();
    cloudPage = new CloudPage();
  }

  public void enterCloud() {
    loginService.doLogin();
    browser.open(CLOUD_URL);
  }

  public void openCloud() {
    browser.open(CLOUD_URL);
  }

  public void createNewFolder(String name) {
    cloudPage.createNewFolder(name);
  }

  public boolean isFolderExist(String folderName) {
    return cloudPage.isFolderExist(folderName);
  }

  public boolean isFolderEmpty() {
    return cloudPage.isFolderEmpty();
  }

  //закрываем рекламный диалог
  public void closeDialog() {
    cloudPage.closeDialog();
  }

  //удаляем папку
  public void removeFolder(String name) {
    cloudPage.removeFolder(name);
  }

  // переходим в корневую папку
  public void goToRootFolder() {
    cloudPage.goToRootFolder();
  }

  // загрузить файл
  public void uploadFile(String filePath) {
    cloudPage.uploadFile(filePath);
  }

  //проверяем, что файл загружен
  public boolean isFileUploaded(String fileName) {
    return cloudPage.isFileUploaded(fileName);
  }

  // перетягивание файла в папку
  public void moveFileToFolder(String folderName, String fileName) {
    cloudPage.moveFileToFolder(folderName, fileName);
  }

  // переходим в указанную папку
  public void goToFolder(String nameFolder) {
    cloudPage.goToFolder(nameFolder);
  }

  //возвращает URL на расшаренный файл
  public String getShareLink(String fileName) {
    return cloudPage.getShareLink(fileName);
  }

  public boolean isSharedLinkExist(String fileName, String sharedFileURL) {
    browser.open(sharedFileURL);
    return cloudPage.isSharedLinkExist(fileName);
  }
}
