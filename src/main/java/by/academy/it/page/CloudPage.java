package by.academy.it.page;

import by.academy.it.model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CloudPage extends BasePage {

  public CloudPage(WebDriver driver) {
    super(driver);
  }

  public void doLogin(User user){
    waitVisible("//a[contains(text(),'Войдите')]").click();
    LoginPage loginPage = new LoginPage(getDriver());
    loginPage.doLogin(user);
  }
  //создаем папку в облаке
  public  void createNewFolder(String name){
    waitVisible("//div[text()='Создать']").click();
    waitVisible("//div[text()='Папку']").click();
    waitVisible("//input[@placeholder='Введите имя папки']").sendKeys(name);
    waitVisible("//button[text()='Создать']").click();
  }
  //проверяем, что папка создана
  public boolean isFolderExist(String folderName) {
    boolean result = false;
    try {
      //генерим динамический xPath для переданного в параметре метода имени папки
      String xpathString = "//*[@data-id='/" + folderName + "']/parent::a";
      waitVisible(xpathString);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
  }

  public  void removeFolder(String name){
    String xpathString = "//*[@data-id='/" + name + "']/parent::a";
    waitVisible(xpathString).click();
    waitVisible("//div[text()='Удалить']").click();
    waitVisible("//button[@data-name='remove']").click();
    waitVisible("//span[text()='Удалено: 1 папка']");
    //закрываем диалог
    waitVisible("//div[@class='layer_trashbin-tutorial']//button[@data-name='close']").click();

  }
}
