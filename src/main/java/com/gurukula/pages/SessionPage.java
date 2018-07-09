package com.gurukula.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SessionPage extends BasicPage {
	// Constructor
	public SessionPage(WebDriver driver) {
		super(driver);

	}

	// Elements

	@FindBy(xpath = "//button[text()='Invalidate']")
	private WebElement btnInvalidate;

	@FindBy(css = "strong")
	private WebElement lblMessage;

	// Methods

	public void clickInvalidate() {
		btnInvalidate.click();
	}
	
	public String getSuccessMsg()
	{
		return lblMessage.getText();
	}

}
