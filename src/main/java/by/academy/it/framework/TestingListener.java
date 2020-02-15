package by.academy.it.framework;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestingListener implements ITestListener {
  private long methodStartTime;

  @Override
  public void onStart(ITestContext iTestContext) {
    Log.info("\n" + "[TEST STARTED]" + iTestContext.getName());
  }

  @Override
  public void onFinish(ITestContext iTestContext) {
    Log.info("[TEST ENDED]" + iTestContext.getName()
        + "\n" + "PASSED: " + iTestContext.getPassedTests().size()
        + "\n" + "SKIPPED: " + iTestContext.getSkippedTests().size()
        + "\n" + "FAILED: " + iTestContext.getFailedTests().size());
  }

  @Override
  public void onTestStart(ITestResult iTestResult) {
    methodStartTime = iTestResult.getStartMillis();
    Log.info("[TEST METHOD STARTED]" + iTestResult.getName());
  }

  @Override
  public void onTestSuccess(ITestResult iTestResult) {
    Log.info("[TEST METHOD SUCCESSFULLY COMPLETED]" + iTestResult.getName() + " SPENT TIME(ms): "
        + (iTestResult.getEndMillis() - methodStartTime));
  }

  @Override
  public void onTestFailure(ITestResult iTestResult) {
//    Log.error("[TEST METHOD FAILED]" + iTestResult.getName() + " SPENT TIME(ms): "
//        + (iTestResult.getEndMillis() - methodStartTime), iTestResult.getThrowable());
//    Browser.getInstance().screenshot();
  }

  @Override
  public void onTestSkipped(ITestResult iTestResult) {
    Log.info("[TEST METHOD SKIPPED]" + iTestResult.getName());
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    Log.info("[TEST METHOD FAILED WITHIN SUCCESS PERCENTAGE]" + iTestResult.getName()+ " SPENT TIME(ms): "
        + (iTestResult.getEndMillis() - methodStartTime));
  }
}
