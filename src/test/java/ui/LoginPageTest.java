package ui;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.pages.BasePage;
import com.qa.pages.BattleFieldPage;
import com.qa.pages.LoginPage;
import com.qa.pages.UserRegistrationPage;
import com.qa.testbase.TestBase;

public class LoginPageTest extends TestBase {
	public BasePage basePage;
	public UserRegistrationPage userRegistrationPage;
	public LoginPage loginPage;
	public BattleFieldPage battleFieldPage;
	
	@Parameters({"browser"})
	@BeforeMethod (alwaysRun=true)
	public void beforeMethod(String browserName)
	{
		TestBase testBase=new TestBase();
		initialization(prop.getProperty("url"),browserName);
		basePage=new BasePage();
		loginPage=basePage.openLoginPage();
		//userRegistrationPage=new UserRegistrationPage();
		//Assert.assertTrue(userRegistrationPage.signUpBtn.isDisplayed(),"Sign Up button not available");
		
		//create public method to fetch web elements. Make web elements private
			
	}
	
	@Test
	public void logintoHerokuappTest() throws InterruptedException
	{
		battleFieldPage=loginPage.performLogin();
		String title=driver.getTitle();
		Assert.assertEquals(title, "COVID-19 THE GAME");
	}
}
