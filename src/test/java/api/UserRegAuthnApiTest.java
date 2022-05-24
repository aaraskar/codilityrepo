package api;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.testbase.ApiTestBase;
import com.qa.testbase.TestBase;
import com.qa.utils.ApiUtility;
import com.qa.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * This TestNG class tests api for registration and authentication of user
 * 
 * @author ARASKAR
 *
 */
public class UserRegAuthnApiTest extends ApiTestBase {

	@BeforeTest
	public void beforeTest() {
		ApiTestBase apitestBase = new ApiTestBase();
	}

	/**
	 * DataProvider to read tetst data from external source
	 * 
	 * @return 2D array containing test data.
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	@DataProvider
	public Object[][] getUserData() throws EncryptedDocumentException, IOException {
		Object[][] data = Utility.getTestData("usercreds");
		return data;
	}

	/**
	 * Test case to test api for registration of user
	 * 
	 * @param uname
	 * @param userpwd
	 * @throws FileNotFoundException
	 */
	@Test(priority = 1, dataProvider = "getUserData")
	public void registerUserTest(String uname, String userpwd) throws FileNotFoundException {
		extentTest = extentReports.startTest("RegisterSingleUserTest");
		String date = new SimpleDateFormat("ddmmss").format(new Date());
		uname = uname + date;
		System.out.println("username is :" + uname);
		uservalue = uname;
		passwordValue = userpwd;
		rawResponse = given().spec(ApiUtility.generateRequestSpecification())
				.header("Authorization", prop.getProperty("token"))
				.body(ApiUtility.userPayload("username", uname, "password", userpwd)).when()
				.post(prop.getProperty("userRegisterEndPoint")).then().log().all().and().assertThat()
				.statusCode(STATUS_CODE_200).and().extract().response();

	}

	/**
	 * Test case to test api for authentication of user
	 * 
	 * @throws FileNotFoundException
	 */
	@Test(priority = 2)
	public void authenticateUserTest() throws FileNotFoundException {
		extentTest = extentReports.startTest("AuthenticateUserTest");
		rawResponse = given().spec(ApiUtility.generateRequestSpecification())
				.header("Authorization", prop.getProperty("token"))
				.body(ApiUtility.userPayload("username", uservalue, "password", passwordValue)).when()
				.post(prop.getProperty("userLoginEndPoint")).then().log().all().and().assertThat()
				.statusCode(STATUS_CODE_200).and().extract().response();
		authntoken = ApiUtility.getValue(rawResponse, "token");
		Assert.assertNotNull(authntoken);
	}

	@AfterMethod
	public void afterMethodConfig(ITestResult result) throws IOException {
		System.out.println("Inside afterMethod");
		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, result.getName() + ": FAILED");
			extentTest.log(LogStatus.FAIL, "Exception is : " + result.getThrowable());

		}

		if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(LogStatus.SKIP, result.getName() + " : SKIPPED");
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, result.getName() + " : PASSED");
		}
		extentReports.endTest(extentTest);

	}

}
