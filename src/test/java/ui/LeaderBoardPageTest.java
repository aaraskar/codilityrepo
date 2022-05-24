package ui;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.testbase.TestBase;
import com.qa.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

/**
 * This TestNG class tests leaderboard page
 * 
 * @author ARASKAR
 *
 */
public class LeaderBoardPageTest extends TestBase {
	// Logger log=Logger.getLogger(LeaderBoardPageTest.class);
	@BeforeTest
	public void beforeTest() {
		log = Logger.getLogger(LeaderBoardPageTest.class);
	}

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String browserName) {
		TestBase testBase = new TestBase();
		initialization(prop.getProperty("leaderBoardUrl"), browserName);

	}

	/**
	 * This test checks if the user that has performed some challenges is displayed
	 * on the leaderboard page or not
	 */
	@Test
	public void verifyLeaderBoardTest() {
		extentTest = extentReports.startTest("VerifyLeaderBoardTest");
		log.info("Starting the test: verifyLeaderBoardTest");
		List<WebElement> list = driver.findElements(By.cssSelector("tr td:nth-child(1)"));
		boolean flag = false;
		for (WebElement we : list) {
			if (we.getText().equalsIgnoreCase(uservalue)) {

				flag = true;

				break;
			}
		}
		Assert.assertTrue(flag, "Given element not present on the board");

	}

	/**
	 * This test case verifies the title of leaderboard page
	 */
	@Test
	public void verifyleaderBoardPageTitle() {
		log.info("Starting the test:verifyleaderBoardPageTitle()");
		extentTest = extentReports.startTest("VerifyLeaderBoardPageTitleTest");
		Assert.assertEquals(driver.getTitle(), "COVID-19 THE GAME");
		log.info("Ending the test:verifyleaderBoardPageTitle()");
	}

	
	/**
	 * Captures the result as per the status of test case execution
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
