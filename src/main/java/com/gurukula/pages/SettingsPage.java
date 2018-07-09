package com.gurukula.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class SettingsPage extends BasicPage {

	public SettingsPage(WebDriver driver) {
		super(driver);
	}

	// elements

	@FindBy(name = "firstName")
	private WebElement txtbxFirstName;

	@FindBy(name = "lastName")
	private WebElement txtbxLastName;

	@FindBy(name = "email")
	private WebElement txtbxEmail;

	@FindBy(name = "langKey")
	private WebElement drpdwnLang;

	@FindBy(xpath = "//div[3]/strong")
	private WebElement lblSettingsErrorMsg;

	@FindBy(xpath = "//form/button[text()='Save']")
	private WebElement btnSave;

	// Email Error Text Label
	@FindBy(xpath = "//div[3]/div/p")
	private List<WebElement> lblEmailError;

	// Methods

	public String getSettingsError() {

		WebDriverWait wait = new WebDriverWait(driver, this.getTimeout());
		wait.until(ExpectedConditions.visibilityOfAllElements(lblSettingsErrorMsg));

		return lblSettingsErrorMsg.getText();
	}

	public void clickSave() {
		if (btnSave.isEnabled()) {
			btnSave.click();
		} else {
			System.out.println("Save button is disabled!");
		}
	}

	public void enterFirstName(String fname) {
		txtbxFirstName.sendKeys(fname);
	}

	public void clearFirstName() {
		txtbxFirstName.clear();
	}

	public void enterLastName(String lname) {
		txtbxLastName.sendKeys(lname);
	}

	public void clearLastName() {
		txtbxLastName.clear();
	}

	public void enterEmailName(String email) {
		txtbxEmail.clear();
		txtbxEmail.sendKeys(email);
	}

	public void clearEmail() {
		txtbxEmail.clear();
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
}
