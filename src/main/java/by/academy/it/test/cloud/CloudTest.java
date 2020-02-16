package by.academy.it.test.cloud;

import by.academy.it.service.cloud.CloudService;
import by.academy.it.test.BaseTest;
import java.io.File;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CloudTest extends BaseTest {

  private CloudService cloudService = new CloudService();

  private String folderName = "new_folder";
  private File file = new File("src/main/resources/HelloWorld.txt");

  @BeforeTest
  public void beforeTest() {
    cloudService.enterCloud();
  }

  @Test(priority = 1)
  public void newFolderTest() {
    cloudService.createNewFolder(folderName);
    Assert.assertTrue(cloudService.isFolderExist(folderName));
  }

  @Test(dependsOnMethods = {"newFolderTest", "dragAndDropTest", "shareLinkTest"})
  public void removeFolderTest() {
    cloudService.openCloud();
    cloudService.closeDialog();
    cloudService.removeFolder(folderName);
    Assert.assertTrue(cloudService.isFolderEmpty());
  }

  @Test(priority = 3)
  public void uploadFileTest() {
    cloudService.goToRootFolder();
    cloudService.uploadFile(file.getAbsolutePath());
    Assert.assertTrue(cloudService.isFileUploaded(file.getName()));
  }

  @Test(dependsOnMethods = {"newFolderTest", "uploadFileTest"})
  public void dragAndDropTest() {
    cloudService.goToRootFolder();
    cloudService.moveFileToFolder(folderName, file.getName());
    cloudService.goToFolder(folderName);
    Assert.assertTrue(cloudService.isFileUploaded(file.getName()));
  }

  @Test(dependsOnMethods = {"dragAndDropTest"})
  public void shareLinkTest() {
    String sharedFileURL = cloudService.getShareLink(file.getName());
    Assert.assertTrue(cloudService.isSharedLinkExist(file.getName(), sharedFileURL));
  }
}
