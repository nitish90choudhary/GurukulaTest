package com.gurukula.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gurukula.pages.HomePage;
import com.gurukula.pages.LoginPage;
import com.gurukula.pages.MenuPage;
import com.gurukula.pages.RegistrationPage;
import com.gurukula.utils.Global;

public class RegistrationTest {

	private Global gbl;
	private WebDriver driver;
	LoginPage loginpage;
	HomePage homepage;
	MenuPage menupage;
	RegistrationPage registerpage;

	@BeforeTest
	public void startTest() {
		gbl = new Global();
		driver = gbl.setup();
		homepage = new HomePage(driver);
		menupage = new MenuPage(driver);
		registerpage = new RegistrationPage(driver);
	}

	@Test(dataProvider = "dataForRegisterationTest", enabled = true)
	public void M2_TS01_Verify_NewUser_Registration(String loginName, String email, String password,
			String confPassword) {
		Reporter.log("Click Register a new account link ->");
		homepage.clickRegisterLink();

		registerpage.fillRegistrationForm(loginName, email, password, confPassword);

		Reporter.log("Click Register Button ->");
		registerpage.clickRegister();

		Reporter.log("Verify Success Message");
		assertEquals(registerpage.getRegistrationError(), "Registration Success!");

	}

	@Test(dataProvider = "dataForPwdStrengthTest", enabled = true)
	public void M2_TS02_VerifyPasswordStrength(String loginName, String email, String password, String confPassword,
			String msg) {
		Reporter.log("Click Register a new account link ->");
		homepage.clickRegisterLink();

		registerpage.fillRegistrationForm(loginName, email, password, confPassword);
		assertEquals(registerpage.getPasswordStrength(), msg);
	}

	@Test(dataProvider = "dataForFieldErrorsTest", enabled = true)
	public void M2_TS03_Registeration_ErrorMessageVerification(String id, String loginName, String email, String pwd,
			String cnfPwd, String msg) throws InterruptedException {
		Reporter.log("Click Register a new account link ->");
		homepage.clickRegisterLink();

		switch (id) {
		case "1":
			registerpage.enterLoginName(loginName);
			registerpage.clearLoginNameField();
			assertEquals(registerpage.getLoginErrorMessage(), msg);

			break;
		case "2":
			registerpage.enterLoginName(loginName);
			assertEquals(registerpage.getLoginErrorMessage(), msg);

			break;
		case "3":
			registerpage.enterLoginName(loginName);
			assertEquals(registerpage.getLoginErrorMessage(), msg);
			break;
		case "4":
			registerpage.enterEmail(email);
			assertEquals(registerpage.getEmailErrorMessage(), msg);
			break;
		case "5":
			registerpage.enterEmail(email);
			registerpage.clearEmailField();
			assertEquals(registerpage.getEmailErrorMessage(), msg);
			break;
		case "6":
			registerpage.enterEmail(email);
			assertEquals(registerpage.getEmailErrorMessage(), msg);
			break;
		case "7":
			registerpage.enterEmail(email);
			assertEquals(registerpage.getEmailErrorMessage(), msg);
			break;
		case "8":
			registerpage.enterPassword(pwd);
			registerpage.clearPasswordField();
			assertEquals(registerpage.getPasswordErrorMessage(), msg);
			break;
		case "9":
			registerpage.enterPassword(pwd);
			assertEquals(registerpage.getPasswordErrorMessage(), msg);
			break;
		case "10":
			registerpage.enterPassword(pwd);
			assertEquals(registerpage.getPasswordErrorMessage(), msg);
			break;
		case "11":
			registerpage.enterPassword(pwd);
			assertEquals(registerpage.getPasswordErrorMessage(), msg);
			break;
		case "12":
			registerpage.enterConfirmPassword(cnfPwd);
			registerpage.clearCnfPasswordField();
			assertEquals(registerpage.getConfirmPasswordErrorMessage(), msg);
			break;
		case "13":
			registerpage.enterConfirmPassword(cnfPwd);
			assertEquals(registerpage.getConfirmPasswordErrorMessage(), msg);
			break;

		case "14":
			registerpage.enterConfirmPassword(cnfPwd);
			assertEquals(registerpage.getConfirmPasswordErrorMessage(), msg);
			break;
		case "15":
			registerpage.enterConfirmPassword(cnfPwd);
			assertEquals(registerpage.getConfirmPasswordErrorMessage(), msg);
			break;
		case "16":
			registerpage.fillRegistrationForm(loginName, email, pwd, cnfPwd);
			registerpage.clickRegister();
			assertEquals(registerpage.getPasswordMismatchError(), msg);
			break;

		}

	}

	@Test(enabled = true)
	public void M2_TS04_VerifyRegistrationEnable() {

		homepage.clickRegisterLink();

		registerpage.enterLoginName("user");
		assertFalse(registerpage.isRegisterBtnEnable());

		registerpage.enterEmail("abc@def.com");
		assertFalse(registerpage.isRegisterBtnEnable());

		registerpage.enterPassword("password");
		assertFalse(registerpage.isRegisterBtnEnable());

		registerpage.enterConfirmPassword("password");

		assertTrue(registerpage.isRegisterBtnEnable());

	}

	@AfterMethod
	public void goToHomePage() {

		menupage.clickHome();

	}

	@AfterTest
	public void finishTest() {

		gbl.cleanup();
	}

	// TEST DATA FOR REGISTRATION TEST
	@DataProvider(name = "dataForRegisterationTest")
	public Object[][] getRegistrationData() {
		return new Object[][] { { "nitish", "nitish@opentrash.com", "Test@2018!!!!!", "Test@2018!!!!!" } };
	}

	@DataProvider(name = "dataForPwdStrengthTest")
	public Object[][] getPasswordStrengthData() {
		return new Object[][] {

				{ "nitish", "nitish@opentrash.com", "Test@2018!!!!!", "", "Very Strong" },
				{ "nitish", "nitish@opentrash.com", "Test@2018", "", "Strong" },
				{ "nitish", "nitish@opentrash.com", "12345", "", "Very Weak" },
				{ "nitish", "nitish@opentrash.com", "12345abcde", "", "Weak" }

		};
	}

	@DataProvider(name = "dataForFieldErrorsTest")
	public Object[][] getFieldsData() {
		return new Object[][] { { "1", "A", "email", "password", "confirmpass", "Your login is required." },
				{ "2", "A123", "email", "password", "confirmpass",
						"Your login can only contain lower-case letters and digits." },
				{ "3", "a123456789012345678901234567890123456789012345678901", "", "", "",
						"Your login cannot be longer than 50 characters." },
				{ "4", "user", "email", "", "", "Your e-mail is invalid." },
				{ "5", "user", "a@a.com", "", "", "Your e-mail is required." },
				{ "6", "user", "a@a", "", "", "Your e-mail is required to be at least 5 characters." },
				{ "7", "user", "email1234567890123456789012345678901234567812@com", "", "",
						"Your e-mail cannot be longer than 50 characters." },
				{ "8", "user", "a@abc.com", "password", "", "Your password is required." },
				{ "9", "user", "a@abc.com", "p", "", "Your password is required to be at least 5 characters." },
				{ "10", "user", "a@abc.com", "password1234567890password1234567890password1234567", "",
						"Your password cannot be longer than 50 characters." },
				{ "11", "user", "a@abc.com", "12345$", "",
						"Password should begin with a alphabet and should contain a number and a special character." },
				{ "12", "user", "a@a", "", "112345", "Your confirmation password is required." },
				{ "13", "user", "a@a", "", "password1234567890password1234567890password1234567",
						"Your confirmation password cannot be longer than 50 characters." },
				{ "14", "user", "a@a", "", "12",
						"Your confirmation password is required to be at least 5 characters." },
				{ "15", "user", "a@a", "", "12345$",
						"Password should begin with a alphabet and should contain a number and a special character." },
				{ "16", "user", "a@a.com", "12345", "45678", "The password and its confirmation do not match!" }, };
	}
}
