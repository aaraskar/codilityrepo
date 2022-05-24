package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.testbase.TestBase;

/**
 * 
 * @author ARASKAR Class for the page where all battles are listed
 */
public class BattleFieldPage extends TestBase {
	/**
	 * Define web elements on Battlefield page
	 */

	@FindBy(id = "bus")
	private WebElement takeTheBus;

	@FindBy(id = "restaurant")
	private WebElement goToPublicPlace;

	@FindBy(id = "office")
	private WebElement goToOffice;

	@FindBy(id = "news")
	private WebElement areYouGame;

	public WebElement getTakeTheBus() {
		return takeTheBus;
	}

	public WebElement getGoToPublicPlace() {
		return goToPublicPlace;
	}

	public WebElement getGoToOffice() {
		return goToOffice;
	}

	public WebElement getAreYouGame() {
		return areYouGame;
	}

	/**
	 * Initialize the web elements on battle field page
	 */
	public BattleFieldPage() {
		PageFactory.initElements(driver, this);
	}

	public BusPage startTakeTheBusChallenge() {
		takeTheBus.click();
		return new BusPage();
	}

	public void startGoToPublicPlaceChallenge() {
		goToPublicPlace.click();
	}

	public void startGoToOfficeChallenge() {
		goToOffice.click();
	}
}
