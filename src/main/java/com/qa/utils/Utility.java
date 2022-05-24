package com.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.testbase.ApiTestBase;
import com.qa.testbase.TestBase;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Provides utility methods for ui test cases
 * 
 * @author AJIT RASKAR
 *
 */
public class Utility extends TestBase {
	public static RequestSpecification requestSpecification;

	/**
	 * This method reads test data from external source like excel file and store it
	 * in 2D array.
	 * 
	 * @param sheetName
	 * @return test data in 2D array
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public static Object[][] getTestData(String sheetName) throws EncryptedDocumentException, IOException {

		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\testdata\\ExternalTestData.xlsx");
		Workbook workBook = WorkbookFactory.create(fis);
		Sheet sheet = workBook.getSheet(sheetName);
		int row = sheet.getLastRowNum();
		int col = sheet.getRow(0).getLastCellNum();
		Object[][] data = new Object[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
			}
		}
		return data;
	}

	/**
	 * Configures explicit wait for element to be visible
	 * 
	 * @param driver
	 * @param element
	 * @param timeOut
	 */
	public static void waitForElementTobeVisible(WebDriver driver, WebElement element, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Takes the screenshot for failed test cases
	 * 
	 * @param driver
	 * @param testCaseName
	 * @return path of screenshot file
	 * @throws IOException
	 */
	public static String getScreenShot(WebDriver driver, String testCaseName) throws IOException {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String date = new SimpleDateFormat("yyyyddmmhhmmss").format(new Date());
		String path = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + testCaseName + date + ".png";
		File destFile = new File(path);// This creates empty file
		FileUtils.copyFile(srcFile, destFile);
		return path;
	}

}
