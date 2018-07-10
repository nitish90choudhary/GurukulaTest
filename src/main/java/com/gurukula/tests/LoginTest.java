package com.gurukula.tests;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gurukula.pages.HomePage;
import com.gurukula.pages.LoginPage;
import com.gurukula.pages.MenuPage;
import com.gurukula.utils.Global;

public class LoginTest {

	private Global gbl;
	private WebDriver driver;

	LoginPage loginpage;
	HomePage homepage;
	MenuPage menupage;

	@BeforeTest
	public void startTest() {
		gbl = new Global();
		driver = gbl.setup();
		loginpage = new LoginPage(driver);
		homepage = new HomePage(driver);
		menupage = new MenuPage(driver);
	}

	@Test()
	public void M1_TS01_Login_VerifyApplicationIsUp() {
		Reporter.log("TC01_Verify Application is running");
		Assert.assertTrue(driver.getTitle().contains("gurukula"));

	}

	@Test(dataProvider = "dataForValidLoginTest")
	public void M1_TS02_VerifyLogin(String username, String password) {

		homepage.clickLogin();
		Reporter.log("Step 1 : Click Login Button");
		loginpage.enterUsername(username);
		Reporter.log("Step 2 : Enter Username <" + username + ">");
		loginpage.enterPassword(password);
		Reporter.log("Step 3 : Enter Password <" + password + ">");
		loginpage.clickAuthenticate();
		Reporter.log("Step 4 : Click Authenticate Button");

		assertEquals(loginpage.getLoginSuccessMsg(), "You are logged in as user \"" + username + "\".");
		Reporter.log("Step 5 : Verify Login should be sucessful\n");
		menupage.clickLogout();
		Reporter.log("Step 6 : Logout user");
		menupage.clickHome();
	}

	@Test(dataProvider = "dataForInvalidLoginTest")
	public void M1_TS03_VerifyInvalidLogin(String username, String password) {
		homepage.clickLogin();
		loginpage.enterUsername(username);
		loginpage.enterPassword(password);
		loginpage.clickAuthenticate();

		assertEquals(loginpage.getLoginErroMessage(), "Authentication failed!");

	}

	@Test(dataProvider = "dataForRestPwdTest")
	public void M1_TS04_Verify_Reset_your_password(String id, String frgtPswdEmail, String msg) {

		Reporter.log("TC04_Verify Reset your password functionality");

		homepage.clickLogin();
		loginpage.clickFogotPwdLink();

		switch (id) {
		case "1":
			loginpage.enterForgotPasswordEmail(frgtPswdEmail);
			assertEquals(loginpage.getFrgtEmailErrorMessage(), msg);
			break;

		case "2":
			loginpage.enterForgotPasswordEmail(frgtPswdEmail);
			loginpage.clearForgotPwdEmail();
			assertEquals(loginpage.getFrgtEmailErrorMessage(), msg);
			break;
		case "3":
			loginpage.enterForgotPasswordEmail(frgtPswdEmail);
			assertEquals(loginpage.getFrgtEmailErrorMessage(), msg);
			break;

		case "4":
			loginpage.enterForgotPasswordEmail(frgtPswdEmail);
			assertEquals(loginpage.getFrgtEmailErrorMessage(), msg);
			break;
		case "5":
			loginpage.enterForgotPasswordEmail(frgtPswdEmail);
			loginpage.clickResetPassword();
			assertEquals(loginpage.getRestPwdError(), msg);
			break;
		case "6":
			loginpage.enterForgotPasswordEmail(frgtPswdEmail);
			loginpage.clickResetPassword();
			assertEquals(loginpage.getRestPwdError(), msg);
			break;
		}

	}

	@AfterMethod
	public void goToHomePage() {

		menupage.clickHome();

	}

	@AfterTest
	public void finishTest() {

		gbl.cleanup();
	}

	/// TEST DATA FOR LOGIN TEST

	@DataProvider(name = "dataForInvalidLoginTest")
	public Object[][] getLoginInvalidData() {
		return new Object[][] { { " ", " " }, { "admin", "123456" }, { "user", "admin" }, { "Tom", "123456" } };
	}

	@DataProvider(name = "dataForValidLoginTest")
	public Object[][] getLoginValidData() {
		return new Object[][] { { "admin", "admin" } };
	}

	@DataProvider(name = "dataForRestPwdTest")
	public Object[][] getResetPasswordData1() {
		return new Object[][] { { "1", "email", "Your e-mail is invalid." },
				{ "2", "a@a.com", "Your e-mail is required." },
				{ "3", "a@a", "Your e-mail is required to be at least 5 characters." },
				{ "4", "email1234567890123456789012345678901234567812@com",
						"Your e-mail cannot be longer than 50 characters." },
				{ "5", "admin@localhost.com", "E-Mail address isn't registered!" },
				{ "6", "admin@localhost", "Success!" }, };
	}

}
