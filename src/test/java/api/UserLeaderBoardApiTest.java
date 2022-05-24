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

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * This TestNG class tests various api of leaderboard page
 * 
 * @author ARASKAR
 *
 */
public class UserLeaderBoardApiTest extends ApiTestBase {

	@BeforeTest
	public void beforeTest() {
		ApiTestBase apitestBase = new ApiTestBase();
	}

	@DataProvider
	public Object[][] getUserData() throws EncryptedDocumentException, IOException {
		Object[][] data = Utility.getTestData("usercreds");
		return data;

	}

	/**
	 * Testcase to test the api that returns a list of user
	 * 
	 * @throws FileNotFoundException
	 */
	@Test(priority = 1)
	public void getAllUserTest() throws FileNotFoundException {
		extentTest = extentReports.startTest("GetAllUserTest");
		rawResponse = given().spec(ApiUtility.generateRequestSpecification())
				.header("Authorization", prop.getProperty("token")).when().get(prop.getProperty("leaderBoardEndPoint"))
				.then().log().all().and().assertThat().statusCode(STATUS_CODE_200).and().extract().response();

	}

	/**
	 * Testcase to test creation of user into the leaderboard
	 * 
	 * @param uname
	 * @param userpwd
	 * @throws FileNotFoundException
	 */
	@Test(priority = 2, dataProvider = "getUserData")
	public void addnewUserTest(String uname, String userpwd) throws FileNotFoundException {
		extentTest = extentReports.startTest("AddNewUserTest");
		String date = new SimpleDateFormat("ddmmss").format(new Date());
		uname = uname + date;
		leaderBoarduservalue = uname;
		passwordValue = userpwd;

		rawResponse = given().spec(ApiUtility.generateRequestSpecification())
				.header("Authorization", prop.getProperty("token"))
				.body(ApiUtility.userPayload("username", uname, "score", "20")).when()
				.post(prop.getProperty("leaderBoardEndPoint")).then().log().all().and().assertThat()
				.statusCode(STATUS_CODE_201).and().extract().response();

		String status = ApiUtility.getValue(rawResponse, "status");
		System.out.println(status);
		Assert.assertEquals(status, "success");

	}

	/**
	 * Testcase to test update of user into the leaderboard
	 * 
	 * @throws FileNotFoundException
	 */
	@Test(priority = 3)
	public void updateUserTest() throws FileNotFoundException {
		extentTest = extentReports.startTest("UpdateUserTest");

		rawResponse = given().spec(ApiUtility.generateRequestSpecification())
				.header("Authorization", prop.getProperty("token")).
				body(ApiUtility.userPayload("username", leaderBoarduservalue, "score", "200")).when()
				.put(prop.getProperty("leaderBoardEndPoint")).then().log().all().and().assertThat()
				.statusCode(STATUS_CODE_204).and().extract().response();

	}

	/**
	 * Testcase to test deletion of user into the leaderboard
	 * 
	 * @throws FileNotFoundException
	 */
	@Test(priority = 3, enabled = false)
	public void deleteUserTest() throws FileNotFoundException {
		extentTest = extentReports.startTest("DeleteeUserTest");
		rawResponse = given().spec(ApiUtility.generateRequestSpecification())
				.header("Authorization", prop.getProperty("token")).when()
				.delete(prop.getProperty("leaderBoardEndPoint")).then().log().all().and().assertThat()
				.statusCode(STATUS_CODE_204).and().extract().response();
	}

	/**
	 * Test case to test the user authenticated transactions.
	 * 
	 * @throws FileNotFoundException
	 */
	@Test(priority = 4)
	public void getAuthenticatedUserTest() throws FileNotFoundException {
		extentTest = extentReports.startTest("GetAuthenticatedUserTest");
		rawResponse = given().spec(ApiUtility.generateRequestSpecification()).header("Authorization", authntoken)
				.pathParam("name", uservalue).when().get(prop.getProperty("leaderBoardEndPoint") + "/{name}").then()
				.log().all().and().assertThat().statusCode(STATUS_CODE_200).and().extract().response();
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
