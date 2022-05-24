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
 * Class for various api utilities
 * 
 * @author ARASKAR
 *
 */
public class ApiUtility extends ApiTestBase {
	public static RequestSpecification requestSpecification;

	/**
	 * Returns the value of key from the json response
	 * 
	 * @param response
	 * @param key
	 * @return
	 */
	public static String getValue(Response response, String key) {
		String stringResponse = response.asString();
		JsonPath jsonResponse = new JsonPath(stringResponse);
		return jsonResponse.getString(key);

	}

	/**
	 * Creates required payload
	 * 
	 * @param key1
	 * @param value1
	 * @param key2
	 * @param value2
	 * @return map object containing requested payload
	 */

	public static Map<String, Object> userPayload(String key1, String value1, String key2, String value2) {
		Map<String, Object> userInfoPayload = new HashMap<String, Object>();
		userInfoPayload.put(key1, value1);
		userInfoPayload.put(key2, value2);
		return userInfoPayload;
	}

	/**
	 * Generates common request specificiation
	 * 
	 * @return requestspecification
	 * @throws FileNotFoundException
	 */
	public static RequestSpecification generateRequestSpecification() throws FileNotFoundException {

		if (requestSpecification == null) {
			PrintStream requestPrintStream = new PrintStream(new FileOutputStream(
			System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\logs\\requestresponselog.txt"));
			RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
			requestSpecBuilder.setBaseUri(prop.getProperty("baseUri"));
			requestSpecBuilder.addHeader("Content-Type", "application/json");
//		requestSpecBuilder.addHeader("Authorization",prop.getProperty("token"));
			requestSpecBuilder.addFilter(RequestLoggingFilter.logRequestTo(requestPrintStream));
			requestSpecBuilder.addFilter(ResponseLoggingFilter.logResponseTo(requestPrintStream));
			requestSpecification = requestSpecBuilder.build();
			return requestSpecification;
		}
		return requestSpecification;
	}

}
