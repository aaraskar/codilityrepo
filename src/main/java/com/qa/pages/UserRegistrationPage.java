package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.qa.testbase.TestBase;

/**
 * Class for User registration page
 * 
 * @author AJIT RASKAR
 *
 */
public class UserRegistrationPage extends TestBase {
	/**
	 * Define elements on user registration page
	 */
	@FindBy(id = "uname")
	private WebElement username;

	@FindBy(id = "pwd")
	private WebElement password;

	@FindBy(id = "psw-repeat")
	private WebElement repeatPassword;

	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement cancelBtn;

	@FindBy(id = "signupbtn")
	private WebElement signUpBtn;

	@FindBy(id = "popup")
	private WebElement passwordNotMatchedLabel;

	public WebElement getUsername() {
		return username;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getRepeatPassword() {
		return repeatPassword;
	}

	public WebElement getCancelBtn() {
		return cancelBtn;
	}

	public WebElement getSignUpBtn() {
		return signUpBtn;
	}

	public WebElement getPasswordNotMatchedLabel() {
		return passwordNotMatchedLabel;
	}

	public UserRegistrationPage() {
		PageFactory.initElements(driver, this);
	}

	/**
	 * This method fills new user details on user registration page
	 * 
	 * @param uname
	 * @param userpwd
	 * @return LoginPage object
	 */
	public LoginPage filluserDetails(String uname, String userpwd) {
		username.sendKeys(uname);
		password.sendKeys(userpwd);
		repeatPassword.sendKeys(userpwd);
		signUpBtn.click();
		return new LoginPage();
	}

	public void filluserDetailswithMisMatchPassword() {
		username.sendKeys(prop.getProperty("sampleusername"));
		password.sendKeys(prop.getProperty("samplepassword"));
		repeatPassword.sendKeys(prop.getProperty("anotherpassword"));
		signUpBtn.click();

	}

}
