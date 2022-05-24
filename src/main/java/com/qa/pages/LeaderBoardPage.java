package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.testbase.TestBase;

/**
 * 
 * @author AJIT RASKAR Class for leader board page
 */
public class LeaderBoardPage extends TestBase {

	@FindBy(id = "leaderboard_link")
	private WebElement continueFighting;

	public WebElement getContinueFighting() {
		return continueFighting;
	}

	/**
	 * Initializes elements on leader board page
	 */
	public LeaderBoardPage() {
		PageFactory.initElements(driver, this);
	}

}
