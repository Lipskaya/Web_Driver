package by.academy.it.runner;

import java.util.Arrays;
import java.util.List;
import org.testng.TestNG;
import org.testng.collections.Lists;

/**
 * CommonRunner for all test siute files
 */
public class CommonRunner {

  public static void main(String[] args) {
    TestNG testng = new TestNG();
    List<String> suites = Lists.newArrayList();
    suites.add("src/main/resources/mail/suite.xml");
    suites.add("src/main/resources/cloud/suite.xml");
    testng.setTestSuites(suites);
    testng.run();
  }
}
