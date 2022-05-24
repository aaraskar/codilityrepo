package ui;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.pages.BasePage;
import com.qa.pages.BattleFieldPage;
import com.qa.pages.BusPage;
import com.qa.pages.LoginPage;
import com.qa.pages.UserRegistrationPage;
import com.qa.testbase.TestBase;
import com.qa.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

/**
 * TestNG class for performing challenges on battle field page
 * 
 * @author ARASKAR
 *
 */
public class PerformChallengeTest extends TestBase {
	public BasePage basePage;
	public UserRegistrationPage userRegistrationPage;
	public LoginPage loginPage;
	public BattleFieldPage battleFieldPage;
	public BusPage busPage;

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String browserName) {
		TestBase testBase = new TestBase();
		initialization(prop.getProperty("url"), browserName);
		basePage = new BasePage();
		loginPage = basePage.openLoginPage();
		battleFieldPage = loginPage.performLogin();

	}

	/**
	 * This test case performs challenges mentioned in battle field page
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void performTask() throws InterruptedException {
		extentTest = extentReports.startTest("PerformChallengeTest");
		busPage = battleFieldPage.startTakeTheBusChallenge();
		int numberofTasksDone = busPage.performTasks();
		Assert.assertTrue(numberofTasksDone > 0);
//		Assert.assertEquals(numberofTasksDone, 3);

	}

	/**
	 * Captures the result of test case execution
	 * 
	 * @param result
	 * @throws IOException
	 */
	@AfterMethod
	public void afterMethodConfig(ITestResult result) throws IOException {

//		tearDown(result,extentTest,driver,extentReports);  
		System.out.println("Inside afterMethodConfig");
		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, result.getName() + ": FAILED");
			extentTest.log(LogStatus.FAIL, "Exception is : " + result.getThrowable());
			String path = Utility.getScreenShot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(path));
		}

		if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(LogStatus.SKIP, result.getName() + " : SKIPPED");
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, result.getName() + " : PASSED");
		}
		extentReports.endTest(extentTest);

		driver.quit();
	}

}
