package com.qa.testbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.qa.utils.Utility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Base class for Api
 * 
 * @author AJIT RASKAR
 *
 */
public class ApiTestBase {

	public static Properties prop;
	public static FileInputStream fis;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;
	public static Logger log;
	public static String uservalue;
	public static String leaderBoarduservalue;
	public static String passwordValue;
	public static int STATUS_CODE_200 = 200;
	public static int STATUS_CODE_201 = 201;
	public static int STATUS_CODE_204 = 204;
	public static int STATUS_CODE_400 = 400;
	public static Response rawResponse;
	public static JsonPath jsonResponse;
	public static String authntoken;

	public ApiTestBase() {
		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\config\\config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prop = new Properties();
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method initializes extent report object
	 */
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		System.out.println("Inside beforeSuite");
		extentReports = new ExtentReports(System.getProperty("user.dir") + "/test-output/ApiReport.html", true);
		extentReports.addSystemInfo("HostName", "Ajit-lap");
		extentReports.addSystemInfo("Environment", "QA-Env");
		extentReports.addSystemInfo("Username", "Ajit Raskar");
		extentReports.addSystemInfo("Type", "API");
	}

	/**
	 * This method writes all captured information on extent report
	 */
	@AfterSuite(alwaysRun = true)
	public void afterTestCleanup() {
		System.out.println("Inside aftersuite");
		extentReports.flush();
		extentReports.close();
	}

}
