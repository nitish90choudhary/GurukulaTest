package com.gurukula.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gurukula.utils.Global;

public class BasicPage {
	private static final int TIMEOUT = Integer.parseInt(Global.configMap.get("app.explicitwait"));
	protected WebDriver driver;
	protected WebDriverWait wait;

	BasicPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h2")
	private WebElement lblPageHeader;

	// Checks if element is visible or not
	public boolean waitForElementVisibility(WebElement element) {

		wait = new WebDriverWait(driver, TIMEOUT);
		wait.until(ExpectedConditions.visibilityOfAllElements(element));

		if (element.isDisplayed())
			return true;
		else
			return false;
	}

	public boolean waitForElementToBeClickable(WebElement element) {

		wait = new WebDriverWait(driver, TIMEOUT);
		wait.until(ExpectedConditions.elementToBeClickable(element));

		if (element.isEnabled())
			return true;
		else
			return false;
	}

	public int getTimeout() {
		return TIMEOUT;
	}

	// Checks if element is enabled or not
	public boolean isElementEnabled(String xpath) {
		wait = new WebDriverWait(driver, TIMEOUT);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		WebElement element = driver.findElement(By.xpath(xpath));
		if (element.isEnabled())
			return true;
		else
			return false;
	}

	public boolean containsString(String s) {
		if (driver.getPageSource().contains(s))
			return true;
		else
			return false;
	}

	//

	public String getPageHeader() {
		return lblPageHeader.getText();
	}

	// Method to decode color code to Password Strength
	public String decodeColorBar(String[] code) {
		String color = "";

		for (int i = 0; i < code.length; i++) {

			switch (code[i]) {

			case "rgb(221, 221, 221)":
				color += "W";
				break;
			case "rgb(153, 255, 0)":
				color += "L";
				break;

			case "rgb(255, 0, 0)":
				color += "R";
				break;
			case "rgb(0, 255, 0)":
				color += "G";
				break;

			case "rgb(255, 153, 0)":
				color += "O";
				break;

			}

		}

		// System.out.println("CODE: " + color);
		if (color.equals("RWWWW"))
			return "Very Weak";
		else if (color.equals("OOWWW"))
			return "Weak";
		else if (color.equals("LLLLW"))
			return "Strong";
		else if (color.equals("GGGGG"))
			return "Very Strong";
		else
			return "Unknown";
	}

}
