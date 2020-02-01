package Itacademy;

import java.util.Arrays;
import java.util.List;
import org.testng.TestNG;

/**
 * Hello world!
 */
public class Runner {

  public static void main(String[] args) {
    TestNG testNG = new TestNG();
    List<String> file = Arrays.asList("./src/main/resources/suite.xml");
    testNG.setTestSuites(file);
    testNG.run();
    System.out.println("stop");
  }
}
