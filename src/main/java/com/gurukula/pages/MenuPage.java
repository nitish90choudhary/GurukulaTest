package com.gurukula.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuPage extends BasicPage {

	public MenuPage(WebDriver driver) {
		super(driver);
	}

	// Menus displayed before login
	@FindBy(xpath = ".//*[@id='navbar-collapse']//a/span[2][text()='Home']")
	private WebElement mHome;

	@FindBy(xpath = ".//*[@id='navbar-collapse']//span/span[2][text()='Account']")
	private WebElement mAccount;

	@FindBy(xpath = ".//*[@id='navbar-collapse']//a/span[2][text()='Authenticate'")
	private WebElement mAuthenticate;

	@FindBy(xpath = ".//*[@id='navbar-collapse']//a/span[2][text()='Register']")
	private WebElement mRegister;

	// Menus displayed after login

	@FindBy(xpath = ".//*[@id='navbar-collapse']/ul//span[2][text()='Entities']")
	private WebElement mEntities;
	// Sub menu
	@FindBy(xpath = ".//*[@id='navbar-collapse']/ul//span[2][text()='Branch']")
	private WebElement mBranch;
	// Sub menu
	@FindBy(xpath = ".//*[@id='navbar-collapse']/ul//span[2][text()='Staff']")
	private WebElement mStaff;

	@FindBy(xpath = ".//*[@id='navbar-collapse']//a/span[2][text()='Settings']")
	private WebElement mSettings;

	@FindBy(xpath = ".//*[@id='navbar-collapse']//a/span[2][text()='Password']")
	private WebElement mPassword;

	@FindBy(xpath = ".//*[@id='navbar-collapse']//a/span[2][text()='Sessions']")
	private WebElement mSessions;

	@FindBy(xpath = ".//*[@id='navbar-collapse']/ul/li[3]/ul/li[4]/a/span[2]")
	private WebElement mLogout;

	// Action methods
	public void clickHome() {

		this.waitForElementVisibility(mHome);
		mHome.click();

	}

	public void clickAccount() {
		mAccount.click();
	}

	public void clickAuthenticate() {
		mAuthenticate.click();

	}

	public void clickRegister() {
		mRegister.click();

	}

	public void clickEntities() {
		mEntities.click();
	}

	public void clickBranch() {
		clickEntities();

		mBranch.click();

	}

	public void clickStaff() {
		clickEntities();
		mStaff.click();

	}

	public void clickSettings() {
		clickAccount();
		mSettings.click();

	}

	public void clickPassword() {
		clickAccount();
		mPassword.click();

	}

	public void clickSessions() {

		clickAccount();
		mSessions.click();

	}

	public void clickLogout() {
		clickAccount();
		mLogout.click();

	}

}
