package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.testbase.TestBase;
import com.qa.utils.Utility;

/**
 * 
 * @author AJIT RASKAR This class is for login page
 */
public class LoginPage extends TestBase {

	/**
	 * Define web elements on login page
	 */
	@FindBy(xpath = "//a[text()='Login warrior']")
	private WebElement LoginWarrior;

	@FindBy(xpath = "//h1[text()='Login with your warrior name']")
	private WebElement label;

	@FindBy(id = "worrior_username")
	private WebElement username;
	@FindBy(id = "worrior_pwd")
	private WebElement password;

	@FindBy(id = "start")
	private WebElement start;

	public WebElement getLoginWarrior() {
		return LoginWarrior;
	}

	public WebElement getLabel() {
		return label;
	}

	public WebElement getUsername() {
		return username;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getStart() {
		return start;
	}

	/**
	 * This method initializes elements on login page
	 */
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}

	/**
	 * This method perform login operation
	 * 
	 * @return BattleFieldPage object
	 */
	public BattleFieldPage performLogin() {
		username.sendKeys(uservalue);
		password.sendKeys(passwordValue);
		LoginWarrior.click();
		Utility.waitForElementTobeVisible(driver, start, 20);
		start.click();
		return new BattleFieldPage();

	}

}
