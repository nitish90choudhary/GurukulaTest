package com.gurukula.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

public class BranchPage extends BasicPage {

	// Constructor
	public BranchPage(WebDriver driver) {
		super(driver);

	}

	// Elements

	@FindBy(xpath = "//button/span[2][text()='Create a new Branch']")
	private WebElement btnCreateNewBranch;

	@FindBy(name = "name")
	private WebElement txtbxName;

	@FindBy(name = "code")
	private WebElement txtbxCode;

	@FindBy(xpath = "//button[1]/span[2][text()='Cancel']")
	private WebElement btnCancel;

	@FindBy(xpath = "//button[2]/span[2][text()='Save']")
	private WebElement btnSave;

	@FindBy(id = "searchQuery")
	private WebElement txtbxQuery;

	@FindBy(xpath = "//button/span[2][text()='Search a Branch']")
	private WebElement btnSearchBranch;

	@FindBy(xpath = "//button/span[2][text()='Back']")
	private WebElement btnBack;

	@FindBy(xpath = "/html/body/div[3]/div[1]/div/div[3]/div/div/form/div[3]/button[1]")
	private WebElement btnCnfrmCancel;

	@FindBy(xpath = "//table/tbody/tr[1]/td[2]/input")
	private WebElement lblViewName;

	@FindBy(xpath = "//table/tbody/tr[2]/td[2]/input")
	private WebElement lblViewCode;

	// Accessing Branch GRID

	@FindBy(xpath = "//table/tbody/tr")
	private List<WebElement> rowBranchTable;

	// Error elements

	@FindBy(xpath = "//*[@id=\"deleteBranchConfirmation\"]/div/div/form/div[3]/button[2]/span[2]")
	private WebElement btnCnfrmDelete;

	@FindBy(xpath = "//div[@id='saveBranchModal']/div/div/form/div[2]/div[2]/div/p")
	private List<WebElement> lblBranchNameError;

	@FindBy(xpath = "//div[@id='saveBranchModal']/div/div/form/div[2]/div[3]/div/p")
	private List<WebElement> lblBranchCodeError;

	// Pagination field
	@FindBy(linkText = ">")
	private WebElement lnkNext;

	@FindBy(linkText = "<")
	private WebElement lnkPrevious;

	@FindBy(linkText = ">>")
	private WebElement lnkLastPage;

	@FindBy(linkText = "<<")
	private WebElement lnkFirstPage;
	// Methods

	public void clickCreatNewBranch() {
		btnCreateNewBranch.click();

		this.waitForElementVisibility(txtbxName);

	}

	public void enterBranchName(String branch) {
		txtbxName.clear();
		txtbxName.sendKeys(branch);
	}

	public void enterBranchCode(String code) {
		txtbxCode.clear();
		txtbxCode.sendKeys(code);
	}

	public void clickSave() {
		this.waitForElementToBeClickable(btnSave);
		btnSave.click();
		this.waitForElementVisibility(btnCreateNewBranch);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickCancel() {
		btnCancel.click();
		this.waitForElementVisibility(btnCreateNewBranch);
	}

	public void backToBranches() {
		btnBack.click();
	}

	public void clickView() {
		driver.findElement(By.xpath("//table/tbody/tr[" + rowBranchTable.size() + "]/td[4]/button[1]")).click();
	}

	public void clickEdit() {
		// click Edit button from last rows
		driver.findElement(By.xpath("//table/tbody/tr[" + rowBranchTable.size() + "]/td[4]/button[2]")).click();
		this.waitForElementVisibility(txtbxName);
	}

	public void clickDelete() {
		// click delete button from last rows

		driver.findElement(By.xpath("//table/tbody/tr[" + rowBranchTable.size() + "]/td[4]/button[3]")).click();

	}

	public String getLblViewBranchName() {
		return lblViewName.getAttribute("value");

	}

	public String getLblViewBranchCode() {
		return lblViewCode.getAttribute("value");
	}

	public void clickConfirmDelete() {
		this.waitForElementVisibility(btnCnfrmDelete);
		btnCnfrmDelete.click();
	}

	public void clickConfirmCancel() {
		btnCnfrmCancel.click();
	}

	// Error text messages
	public String getBranchNameErrorMessage() {
		String msg = "";
		for (WebElement element : lblBranchNameError) {
			if (element.getAttribute("class").equals("help-block ng-scope")) {

				msg = element.getText();
				// System.out.println("Displayed and text: " + msg);
			}

		}
		Reporter.log("Verification of Branch Name Error Message " + msg + "->");
		return msg;
	}

	public String getBranchCodeErrorMessage() {
		String msg = "";
		for (WebElement element : lblBranchCodeError) {
			if (element.getAttribute("class").equals("help-block ng-scope")) {

				msg = element.getText();
				// System.out.println("Displayed and text: " + msg);
			}

		}
		Reporter.log("Verification of Branch Code Error Message " + msg + "->");
		return msg;
	}

	// Branch Grid
	public String[] getNewlyCreatedBranch() {
		int row = rowBranchTable.size();
		String[] branch = new String[2];
		String branchNamexpath = "//table/tbody/tr[" + row + "]/td[2]";
		String branchCodexpath = "//table/tbody/tr[" + row + "]/td[3]";
		branch[0] = driver.findElement(By.xpath(branchNamexpath)).getText();
		branch[1] = driver.findElement(By.xpath(branchCodexpath)).getText();

		return branch;
	}

	// Pagination Methods

	public void clickNextPage() {
		lnkNext.click();
	}

	public void clickPreviousPage() {
		lnkPrevious.click();
	}

	public void clickLastPage() {
		lnkLastPage.click();
	}

	public void clickFirstPage() {
		lnkFirstPage.click();
	}
}
