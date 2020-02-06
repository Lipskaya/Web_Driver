package by.academy.it.test;

import by.academy.it.model.User;
import by.academy.it.page.CloudPage;
import by.academy.it.page.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CloudTest extends BaseTest {

  private CloudPage cloudPage;
  private LoginPage loginPage;
  private  String folderName = "new_folder";

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
  @Test(priority = 2,dependsOnMethods = {"newFolderTest"})
  public void removeFolderTest()  {
    cloudPage.removeFolder(folderName);
    Assert.assertFalse(cloudPage.isFolderExist(folderName));

  }

}
