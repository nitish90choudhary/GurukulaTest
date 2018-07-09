package com.gurukula.pages;

import org.openqa.selenium.*;

import org.openqa.selenium.support.FindBy;

public class HomePage extends BasicPage {

	// Constructor
	public HomePage(WebDriver driver) {
		super(driver);

	}

	@FindBy(linkText = "login")
	private WebElement linkLogin;

	@FindBy(linkText = "Register a new account")
	private WebElement linkRegister;

	// Click login link
	public void clickLogin() {
		linkLogin.click();
	}

	// Click Register link
	public void clickRegisterLink() {
		linkRegister.click();
	}

}
