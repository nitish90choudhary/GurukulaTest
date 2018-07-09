package com.gurukula.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import com.gurukula.utils.Global;

public class LoginPage extends BasicPage {

	public LoginPage(WebDriver driver) {
		super(driver);

	}

	@FindBy(xpath = ".//*[@id='username']")
	private WebElement txtbxLogin;

	@FindBy(xpath = ".//*[@id='password']")
	private WebElement txtbxPassword;

	@FindBy(xpath = ".//*[@id='rememberMe']")
	private WebElement chkbxAutomaticLogin;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement btnAuthenticate;

	@FindBy(css = "strong")
	private WebElement lblLoginErrorMessage;

	@FindBy(linkText = "Did you forget your password?")
	private WebElement linkForgot;

	@FindBy(xpath = "//form/div/div/p")
	private List<WebElement> lblFrgtEmailErrorMsg;

	@FindBy(xpath = "/html/body/div[3]/div[1]/div/div/div/div[1]/strong")
	private WebElement lblResetPwdError;

	@FindBy(linkText = "Register a new account")
	private WebElement linkRegister;

	@FindBy(xpath = "//div[2]/div/div")
	private WebElement lblLoggedUserSuccess;

	// Forgot password Page Elements
	@FindBy(name = "email")
	private WebElement txtbxForgotPwdEmail;

	@FindBy(xpath = "//button[text()='Reset password']")
	private WebElement btnResetPassword;
	// Action methods

	public void enterUsername(String username) {
		txtbxLogin.clear();
		txtbxLogin.sendKeys(username);
	}

	public void enterPassword(String password) {
		txtbxPassword.clear();
		txtbxPassword.sendKeys(password);
	}

	public void clickAuthenticate() {
		btnAuthenticate.click();
	}

	public void checkAuthenticateCheckbox() {

		chkbxAutomaticLogin.click();

	}

	public boolean isCheckedAuthenticate() {
		return btnAuthenticate.isSelected();
	}

	public void enterForgotPasswordEmail(String frgtPswdEmail) {
		txtbxForgotPwdEmail.clear();
		txtbxForgotPwdEmail.sendKeys(frgtPswdEmail);
	}

	public String getFrgtEmailErrorMessage() {
		String msg = "";
		for (WebElement element : lblFrgtEmailErrorMsg) {
			if (element.getAttribute("class").equals("help-block ng-scope")) {

				msg = element.getText();
				// System.out.println("Displayed and text: " + msg);
			}

		}
		Reporter.log("Verification of Confirm Password Error Message " + msg + "->");
		return msg;
	}

	public void clearForgotPwdEmail() {
		txtbxForgotPwdEmail.clear();
	}

	public String getRestPwdError() {

		return lblResetPwdError.getText();
	}

	public void clickResetPassword() {
		btnResetPassword.click();
	}

	public void clickFogotPwdLink()

	{
		linkForgot.click();

	}

	public void login(String username, String pwd) {

		enterUsername(username);
		enterPassword(pwd);
		clickAuthenticate();
	}

	public String getLoginErroMessage() {
		if (waitForElementVisibility(lblLoginErrorMessage))
			return lblLoginErrorMessage.getText();
		else
			return "Element not visible";
	}

	public String getLoginSuccessMsg() {
		return lblLoggedUserSuccess.getText();
	}

	public void gotoLoginPage() {
		driver.get(Global.configMap.get("app.url") + "login");
	}
}
