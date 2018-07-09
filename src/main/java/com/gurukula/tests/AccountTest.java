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
import com.gurukula.pages.PasswordPage;
import com.gurukula.pages.SessionPage;
import com.gurukula.pages.SettingsPage;
import com.gurukula.pages.StaffPage;
import com.gurukula.utils.Global;

public class AccountTest {
	private Global gbl;
	private WebDriver driver;

	LoginPage loginpage;
	HomePage homepage;
	MenuPage menupage;
	StaffPage staffpage;
	SessionPage sessionpage;
	PasswordPage pwdpage;
	SettingsPage settingpage;

	@BeforeTest
	public void startTest() {
		gbl = new Global();
		driver = gbl.setup();
		loginpage = new LoginPage(driver);
		homepage = new HomePage(driver);
		menupage = new MenuPage(driver);
		staffpage = new StaffPage(driver);
		sessionpage = new SessionPage(driver);
		pwdpage = new PasswordPage(driver);
		settingpage = new SettingsPage(driver);
		homepage.clickLogin();
		loginpage.login("admin", "admin");

	}

	@Test
	public void M3_TS01_Verify_user_settings_change() {

		menupage.clickSettings();
		assertEquals(settingpage.getPageHeader(), "User settings for [admin]");
		settingpage.clearFirstName();
		settingpage.enterFirstName("Nitish");
		settingpage.clickSave();

		assertEquals(settingpage.getSettingsError(), "Sucess!");

	}

	@Test(enabled = true)
	public void M3_TS02_Verify_Change_user_password() {

		menupage.clickPassword();

		assertEquals(pwdpage.getPageHeader(), "Password for [admin]");

		pwdpage.enterNewPassword("Test@2018");
		pwdpage.enterNewCnfPassword("Test@2018");
		pwdpage.clickSave();

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(pwdpage.getChangePwdError(), "Sucess!");

	}

	@Test(dataProvider = "dataForPwdStrengthTest", enabled = true)
	public void M3_TS03_VerifyPasswordStrength_ChangePwdScreen(String id, String newPwd, String newConfPwd,
			String msg) {
		Reporter.log("Click Passwords sub-menu ->");
		menupage.clickPassword();

		switch (id) {
		// Password Strength Test
		case "1":
			pwdpage.enterNewPassword(newPwd);
			assertEquals(pwdpage.getPasswordStrength(), msg);
			break;
		case "2":
			pwdpage.enterNewPassword(newPwd);
			assertEquals(pwdpage.getPasswordStrength(), msg);
			break;
		case "3":
			pwdpage.enterNewPassword(newPwd);
			assertEquals(pwdpage.getPasswordStrength(), msg);
			break;
		case "4":
			pwdpage.enterNewPassword(newPwd);
			assertEquals(pwdpage.getPasswordStrength(), msg);
			break;

		// Password Error Test
		case "5":
			pwdpage.enterNewPassword(newPwd);
			pwdpage.clearNewPassword();
			assertEquals(pwdpage.getPasswordErrorMessage(), msg);
			break;

		case "6":
			pwdpage.enterNewPassword(newPwd);
			assertEquals(pwdpage.getPasswordErrorMessage(), msg);
			break;
		case "7":
			pwdpage.enterNewPassword(newPwd);
			assertEquals(pwdpage.getPasswordErrorMessage(), msg);
			break;
		case "8":
			pwdpage.enterNewPassword(newPwd);
			assertEquals(pwdpage.getPasswordErrorMessage(), msg);
			break;
		// Confirm Password Strength Test

		case "9":
			pwdpage.enterNewPassword(newPwd);
			pwdpage.enterNewCnfPassword(newConfPwd);
			pwdpage.clearNewConfPassword();
			assertEquals(pwdpage.getConfirmPasswordErrorMessage(), msg);
			break;

		case "10":
			pwdpage.enterNewPassword(newPwd);
			pwdpage.enterNewCnfPassword(newConfPwd);
			assertEquals(pwdpage.getConfirmPasswordErrorMessage(), msg);
			break;
		case "11":
			pwdpage.enterNewPassword(newPwd);
			pwdpage.enterNewCnfPassword(newConfPwd);
			assertEquals(pwdpage.getConfirmPasswordErrorMessage(), msg);
			break;
		case "12":
			pwdpage.enterNewPassword(newPwd);
			pwdpage.enterNewCnfPassword(newConfPwd);
			assertEquals(pwdpage.getConfirmPasswordErrorMessage(), msg);
			break;

		case "13":
			pwdpage.enterNewPassword(newPwd);
			pwdpage.enterNewCnfPassword(newConfPwd);
			pwdpage.clickSave();
			assertEquals(pwdpage.getPasswordMismatchError(), msg);
			break;

		}

	}

	@Test(dataProvider = "dataForFieldErrorsTest", enabled = true)
	public void M3_TS04_VerifySettings_EmailFieldErrors(String id, String email, String msg)
			throws InterruptedException {
		Reporter.log("Click Settings sub-menu ->");
		menupage.clickSettings();

		switch (id) {

		case "1":
			settingpage.enterEmailName(email);
			assertEquals(settingpage.getEmailErrorMessage(), msg);
			break;
		case "2":
			settingpage.enterEmailName(email);
			settingpage.clearEmail();
			assertEquals(settingpage.getEmailErrorMessage(), msg);
			break;
		case "3":
			settingpage.enterEmailName(email);
			assertEquals(settingpage.getEmailErrorMessage(), msg);
			break;
		case "4":
			settingpage.enterEmailName(email);
			assertEquals(settingpage.getEmailErrorMessage(), msg);
			break;

		}
	}

	@Test(enabled = true)
	public void M3_TS05_Verify_Invalidate_Session() {

		menupage.clickSessions();
		Assert.assertEquals(staffpage.getPageHeader(), "Active sessions for [admin]");
		sessionpage.clickInvalidate();
		Assert.assertEquals(sessionpage.getSuccessMsg(), "Session invalidated!");

		menupage.clickStaff();
		Assert.assertEquals(staffpage.getPageHeader(), "Staffs");

	}

	@AfterMethod
	public void goToHomePage() {

		menupage.clickHome();

	}

	@AfterTest
	public void finishTest() {
		menupage.clickLogout();
		gbl.cleanup();
	}

	// TEST DATA FOR ACCOUNT TEST

	@DataProvider(name = "dataForPwdStrengthTest")
	public Object[][] getPasswordStrengthData() {
		return new Object[][] { { "1", "Test@2018!!!!!", "", "Very Strong" }, { "2", "Test@2018", "", "Strong" },
				{ "3", "12345", "", "Very Weak" }, { "4", "12345abcde", "", "Weak" },

				{ "5", "password", "", "Your password is required." },
				{ "6", "p", "", "Your password is required to be at least 5 characters." },
				{ "7", "password1234567890password1234567890password1234567", "",
						"Your password cannot be longer than 50 characters." },
				{ "8", "12345$", "",
						"Password should begin with a alphabet and should contain a number and a special character." },
				{ "9", "", "112345", "Your confirmation password is required." },
				{ "10", "", "password1234567890password1234567890password1234567",
						"Your confirmation password cannot be longer than 50 characters." },
				{ "11", "", "12", "Your confirmation password is required to be at least 5 characters." },
				{ "12", "", "12345$",
						"Password should begin with a alphabet and should contain a number and a special character." },
				{ "13", "12345", "45678", "The password and its confirmation do not match!" },

		};
	}

	@DataProvider(name = "dataForFieldErrorsTest")
	public Object[][] getFieldsData() {
		return new Object[][] { { "1", "email", "Your e-mail is invalid." },
				{ "2", "a@a.com", "Your e-mail is required." },
				{ "3", "a@a", "Your e-mail is required to be at least 5 characters." },
				{ "4", "email1234567890123456789012345678901234567812@com",
						"Your e-mail cannot be longer than 50 characters." }, };
	}
}
