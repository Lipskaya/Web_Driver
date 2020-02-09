package by.academy.it.test;

import by.academy.it.model.User;
import by.academy.it.page.CloudPage;
import by.academy.it.page.LoginPage;
import java.io.File;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CloudTest extends BaseTest {
  private CloudPage cloudPage;
  private LoginPage loginPage;
  private String folderName = "new_folder";
  private File file = new File("src/main/resources/HelloWorld.txt");

  @BeforeTest
  public void beforeTest() {
    getDriver().get(LOGIN_URL);
    cloudPage = new CloudPage(getDriver());
    loginPage = new LoginPage(getDriver());
    User user = new User();
    user.setLogin(LOGIN);
    user.setPassword(PASSWORD);
    loginPage.doLogin(user);
    getDriver().get(CLOUD_URL);
  }

  @Test(priority = 1)
  public void newFolderTest() {
    cloudPage.createNewFolder(folderName);
    Assert.assertTrue(cloudPage.isFolderExist(folderName));
  }

  @Test(dependsOnMethods = {"newFolderTest", "dragAndDropTest", "shareLinkTest"})
  public void removeFolderTest() {
    getDriver().get(CLOUD_URL);
    cloudPage.closeDialog();
    cloudPage.removeFolder(folderName);
    Assert.assertTrue(cloudPage.isFolderEmpty());
  }

  @Test(priority = 3)
  public void uploadFileTest() {
    cloudPage.goToRootFolder();
    cloudPage.uploadFile(file.getAbsolutePath());
    Assert.assertTrue(cloudPage.isFileUploaded(file.getName()));
  }

  @Test(dependsOnMethods = {"newFolderTest", "uploadFileTest"})
  public void dragAndDropTest() {
    cloudPage.goToRootFolder();
    cloudPage.moveFileToFolder(folderName, file.getName());
    cloudPage.goToFolder(folderName);
    Assert.assertTrue(cloudPage.isFileUploaded(file.getName()));
  }

  @Test(dependsOnMethods = {"dragAndDropTest"})
  public void shareLinkTest() {
    String sharedFileURL = cloudPage.getShareLink(file.getName());
    driver.get(sharedFileURL);
    Assert.assertTrue(cloudPage.isSharedLinkExist(file.getName()));
  }
}
