package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.testbase.TestBase;

/**
 * 
 * @author ARASKAR
 * Class for handling base page of https://responsivefight.herokuapp.com/
 *
 */
public class BasePage extends TestBase {
	/**
	 * Define web elements on BasePage
	 */

	@FindBy(id = "rego")
	private WebElement registerBtn;

	@FindBy(id = "login")
	private WebElement loginBtn;

	public WebElement getRegisterBtn() {
		return registerBtn;
	}

	public WebElement getLoginBtn() {
		return loginBtn;
	}

	/**
	 * Initializes web elements on basepage
	 */
	public BasePage() {
		PageFactory.initElements(driver, this);
	}

	public void openRegistrationPage() {
		registerBtn.click();
	}

	public LoginPage openLoginPage() {
		loginBtn.click();
		return new LoginPage();
	}

}
