package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.testbase.TestBase;
import com.qa.utils.Utility;

/**
 * 
 * @author AJIT RASKAR This is the class for Bus challenge
 */
public class BusPage extends TestBase {

	@FindBy(id = "bus_timer_start")
	private WebElement busTimerStart;

	@FindBy(xpath = "//button[contains(@id,'start')]")
	private WebElement timer;

	@FindBy(id = "bus_answer_1")
	private WebElement busAnswer1;

	@FindBy(xpath = "//a[contains(@id,'answer_1')]")
	private WebElement answer1;

	@FindBy(xpath = "//a[contains(@id,'answer_2')]")
	private WebElement answer2;

	@FindBy(id = "close_correct_modal_btn")
	private WebElement tryTheNextBattle;

	public WebElement getBusTimerStart() {
		return busTimerStart;
	}

	public WebElement getTimer() {
		return timer;
	}

	public WebElement getBusAnswer1() {
		return busAnswer1;
	}

	public WebElement getAnswer1() {
		return answer1;
	}

	public WebElement getAnswer2() {
		return answer2;
	}

	public WebElement getTryTheNextBattle() {
		return tryTheNextBattle;
	}

	public BusPage() {
		PageFactory.initElements(driver, this);

	}

	/**
	 * This method performs various tasks on battle field page
	 * 
	 * @return numberOfTasksDone
	 * @throws InterruptedException
	 */
	public int performTasks() throws InterruptedException {
		boolean flag = true;
		int numberOfTasksDone = 1;
		while (numberOfTasksDone <= 3) {

			timer.click();

			if (timer.isDisplayed()) {
				timer.click();
			}

			if (numberOfTasksDone == 3) {
				Utility.waitForElementTobeVisible(driver, answer2, 20);
				answer2.click();
				break;
			} else {
				Utility.waitForElementTobeVisible(driver, answer1, 20);
				answer1.click();
			}
			numberOfTasksDone++;

			Utility.waitForElementTobeVisible(driver, tryTheNextBattle, 20);
			tryTheNextBattle.click();

		}
		System.out.println("numberOfTasksDone is : " + numberOfTasksDone);
		return numberOfTasksDone;

	}

}
