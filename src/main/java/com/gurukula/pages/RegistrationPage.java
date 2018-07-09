package com.gurukula.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

public class RegistrationPage extends BasicPage {

	public RegistrationPage(WebDriver driver) {
		super(driver);

	}

	@FindBy(name = "login")
	private WebElement txtboxRegLogin;

	@FindBy(name = "email")
	private WebElement txtboxregEmail;

	@FindBy(name = "password")
	private WebElement txtboxregPassword;

	@FindBy(name = "confirmPassword")
	private WebElement txtboxregCnfPassword;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement btnRegSubmit;

	// Registeration Error message
	@FindBy(xpath = "//div[2]/strong")
	private WebElement lblRegistrationError;

	// Login Error Text Label
	@FindBy(xpath = "//div[1]/div/p")
	private List<WebElement> lblLoginError;

	// Email Error Text Label
	@FindBy(xpath = "//div[2]/div/p")
	private List<WebElement> lblEmailError;

	// Password Error Text Label
	@FindBy(xpath = "//div[3]/div/p")
	private List<WebElement> lblPasswordError;

	// ConfirmPassword Error Text Label
	@FindBy(xpath = "//div[4]/div/p")
	private List<WebElement> lblConfirmPasswordError;

	// PasswordMismatch Error Text Label
	@FindBy(xpath = "//div[5]")
	private WebElement lblPasswordMismatchError;

	// Password Strength
	@FindBy(xpath = ".//*[@id='strengthBar']/li[1]")
	private WebElement barStrength1;

	@FindBy(xpath = ".//*[@id='strengthBar']/li[2]")
	private WebElement barStrength2;

	@FindBy(xpath = ".//*[@id='strengthBar']/li[3]")
	private WebElement barStrength3;

	@FindBy(xpath = ".//*[@id='strengthBar']/li[4]")
	private WebElement barStrength4;

	@FindBy(xpath = ".//*[@id='strengthBar']/li[5]")
	private WebElement barStrength5;

	/// Page class methods
	public void enterLoginName(String loginName) {
		txtboxRegLogin.clear();
		txtboxRegLogin.sendKeys(loginName);
	}

	public void clearLoginNameField() {
		txtboxRegLogin.clear();

	}

	public void enterEmail(String email) {
		txtboxregEmail.clear();
		txtboxregEmail.sendKeys(email);
	}

	public void enterPassword(String password) {
		txtboxregPassword.clear();
		txtboxregPassword.sendKeys(password);
	}

	public void enterConfirmPassword(String confPassword) {
		txtboxregCnfPassword.clear();
		txtboxregCnfPassword.sendKeys(confPassword);
	}

	public void clickRegister() {
		btnRegSubmit.click();
	}

	public void clearEmailField() {
		txtboxregEmail.clear();
	}

	public void clearPasswordField() {
		txtboxregPassword.clear();
	}

	public void clearCnfPasswordField() {
		txtboxregCnfPassword.clear();
	}

	public boolean isRegisterBtnEnable() {

		return btnRegSubmit.isEnabled();
	}

	public String getPasswordMismatchError() {
		Reporter.log("Verification of Password Mismatch Error Message ->");
		return lblPasswordMismatchError.getText();
	}

	public String getRegistrationError() {
		if (waitForElementVisibility(lblRegistrationError))
			return lblRegistrationError.getText();
		else
			return "Element not visible";

	}

	// Fill Registration form
	public void fillRegistrationForm(String loginName, String email, String pwd, String cnfPwd) {
		Reporter.log("Enter Login Name [" + loginName + "] ->");
		enterLoginName(loginName);

		Reporter.log("Enter Email [" + email + "] ->");
		enterEmail(email);

		Reporter.log("Enter Password [" + pwd + "] ->");
		enterPassword(pwd);

		Reporter.log("Enter Confirm Password [" + cnfPwd + "] ->");
		enterConfirmPassword(cnfPwd);
	}

	public String getPasswordStrength() {

		String[] strength = new String[5];

		// Get CSS property for background
		strength[0] = barStrength1.getCssValue("background");
		strength[1] = barStrength2.getCssValue("background");
		strength[2] = barStrength3.getCssValue("background");
		strength[3] = barStrength4.getCssValue("background");
		strength[4] = barStrength5.getCssValue("background");

		// trim color information
		strength[0] = strength[0].substring(0, strength[0].indexOf(')') + 1);
		strength[1] = strength[1].substring(0, strength[1].indexOf(')') + 1);
		strength[2] = strength[2].substring(0, strength[2].indexOf(')') + 1);
		strength[3] = strength[3].substring(0, strength[3].indexOf(')') + 1);
		strength[4] = strength[4].substring(0, strength[4].indexOf(')') + 1);

		// Decode color value to color

		// System.out.println(decodeColorBar(strength));

		return this.decodeColorBar(strength);

	}

	public String getLoginErrorMessage() {
		String msg = "";
		for (WebElement element : lblLoginError) {
			if (element.getAttribute("class").equals("help-block ng-scope")) {

				msg = element.getText();
				// System.out.println("Displayed and text: " + msg);
			}

		}
		Reporter.log("Verification of Login Error Message " + msg + "->");
		return msg;
	}

	public String getEmailErrorMessage() {
		String msg = "";
		for (WebElement element : lblEmailError) {
			if (element.getAttribute("class").equals("help-block ng-scope")) {

				msg = element.getText();
				// System.out.println("Displayed and text: " + msg);
			}

		}
		Reporter.log("Verification of Email Error Message " + msg + "->");
		return msg;
	}

	public String getPasswordErrorMessage() {
		String msg = "";
		for (WebElement element : lblPasswordError) {
			if (element.getAttribute("class").equals("help-block ng-scope")) {

				msg = element.getText();
				// System.out.println("Displayed and text: " + msg);
			}

		}
		Reporter.log("Verification of Password Error Message " + msg + "->");
		return msg;
	}

	public String getConfirmPasswordErrorMessage() {
		String msg = "";
		for (WebElement element : lblConfirmPasswordError) {
			if (element.getAttribute("class").equals("help-block ng-scope")) {

				msg = element.getText();
				// System.out.println("Displayed and text: " + msg);
			}

		}
		Reporter.log("Verification of Confirm Password Error Message " + msg + "->");
		return msg;
	}

}
