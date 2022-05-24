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
import org.testng.annotations.Parameters;

import com.qa.utils.Utility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * Base class for UI test cases
 * 
 * @author AJIT RASKAR
 *
 */
public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static FileInputStream fis;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;
	public static Logger log;
	public static String uservalue;
	public static String passwordValue;

	public TestBase() {
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
	 * Initializes browser driver and values for pageLoadTimeOut and implicit wait
	 * 
	 * @param url
	 * @param browserName
	 */
	public static void initialization(String url, String browserName) {
		if (browserName.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}

		if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setCapability("marionette", true);
			driver = new FirefoxDriver(firefoxOptions);
		}

//		if(prop.getProperty("browser").equalsIgnoreCase("headless"))
//		{
//		
//		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\main\\java\\com\\qa\\driver86\\chromedriver.exe");
//		ChromeOptions op=new ChromeOptions();
//		op.addArguments("--headless");
//		driver=new ChromeDriver(op);
//		
//		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(prop.getProperty("pageLoadTimeOut")),
				TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(prop.getProperty("ImplicitWait")), TimeUnit.SECONDS);
		driver.get(url);

	}

	/**
	 * Initializes extent report for ui test cases
	 */
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		System.out.println("Inside beforeSuite");
		extentReports = new ExtentReports(System.getProperty("user.dir") + "/test-output/ChromeWebReport.html", true);
		extentReports.addSystemInfo("HostName", "Ajit-lap");
		extentReports.addSystemInfo("Environment", "QA-Env");
		extentReports.addSystemInfo("Username", "Ajit Raskar");
	}

	/**
	 * Generates extent report.
	 */
	@AfterSuite(alwaysRun = true)
	public void afterTestCleanup() {
		System.out.println("Inside aftersuite");
		extentReports.flush();
		extentReports.close();
	}

}
