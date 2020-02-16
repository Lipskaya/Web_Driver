package by.academy.it.runner.cloud;

import java.util.Arrays;
import java.util.List;
import org.testng.TestNG;

/**
 * CommonRunner for CLOUD tests
 */
public class Runner {

  public static void main(String[] args) {
    TestNG testNG = new TestNG();
    List<String> file = Arrays.asList("./src/main/resources/cloud/suite.xml");
    testNG.setTestSuites(file);
    testNG.run();
  }
}
