package ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.pages.BasePage;
import com.qa.pages.LoginPage;
import com.qa.pages.UserRegistrationPage;
import com.qa.testbase.TestBase;
import com.qa.utils.Utility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

/**
 * This TestNG class tests user registration
 * @author ARASKAR
 *
 */
public class UserRegistrationTest extends TestBase {
	public BasePage basePage;
	public UserRegistrationPage userRegistrationPage;
	public LoginPage loginPage;  

	@Parameters({"browser"})
	@BeforeMethod (alwaysRun=true)
	public void beforeMethod(String browserName)
	{
		System.out.println("Inside beforMethod");
		TestBase testBase=new TestBase();
		initialization(prop.getProperty("url"),browserName);
		basePage=new BasePage();
		basePage.openRegistrationPage();
		userRegistrationPage=new UserRegistrationPage();
		Assert.assertTrue(userRegistrationPage.getSignUpBtn().isDisplayed(),"Sign Up button not available");
	}
	
	/**
	 * Dataprovider method to fetch test data from external source
	 * @return 2D array containing test data
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	@DataProvider
	public Object[][] getUserData() throws EncryptedDocumentException, IOException
	{
		Object[][]data=Utility.getTestData("usercreds");
		return data;
		
	
	}
	
	/**
	 * Test case to test user registration
	 * @param uname
	 * @param userpwd
	 */
	@Test(dataProvider="getUserData")
	public void registerSingleUserTest(String uname,String userpwd)
	{
		extentTest=extentReports.startTest("RegisterSingleUserTest");
		String date=new SimpleDateFormat("ddmmss").format(new Date());
		uname=uname+date;
		uservalue=uname;
		passwordValue=userpwd;
		log.info("Username is : " +uname);
		loginPage=userRegistrationPage.filluserDetails(uname,userpwd);
		Utility.waitForElementTobeVisible(driver, loginPage.getLabel(), 20);
		Assert.assertTrue(loginPage.getLabel().isDisplayed(), "Label-'Login with your warrior name label' is not displayed");
	}

	
	/**
	 * Captures result of test case execution
	 * @param result
	 * @throws IOException
	 */
	@AfterMethod
	public void afterMethodConfig(ITestResult result) throws IOException
	{
		System.out.println("Inside afterMethod");
//		tearDown(result,extentTest,driver,extentReports);  
//		System.out.println("Inside afterMethodConfig");
		if(result.getStatus()==ITestResult.FAILURE)
		{
			extentTest.log(LogStatus.FAIL, result.getName() + ": FAILED");
			extentTest.log(LogStatus.FAIL, "Exception is : " +result.getThrowable());
			String path=Utility.getScreenShot(driver,result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(path));
		}
		
		if(result.getStatus()==ITestResult.SKIP)
		{
			extentTest.log(LogStatus.SKIP, result.getName()+" : SKIPPED");
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			extentTest.log(LogStatus.PASS, result.getName() + " : PASSED");
		}
		extentReports.endTest(extentTest);
		
		driver.quit();
	}
	

}
