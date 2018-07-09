package com.gurukula.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

public class StaffPage extends BasicPage {

	// Constructor
	public StaffPage(WebDriver driver) {
		super(driver);

	}

	// Elements

	@FindBy(xpath = "//button/span[2][text()='Create a new Staff']")
	private WebElement btnCreateNewStaff;

	@FindBy(name = "name")
	private WebElement txtbxName;

	@FindBy(name = "related_branch")
	private WebElement drpdwnBranch;

	@FindBy(xpath = "//button/span[2][text()='Cancel']")
	private WebElement btnCancel;

	@FindBy(xpath = "//button[2]/span[2][text()='Save']")
	private WebElement btnSave;

	@FindBy(id = "searchQuery")
	private WebElement txtbxQuery;

	@FindBy(xpath = "//button/span[2][text()='Search a Staff']")
	private WebElement btnSearchStaff;

	@FindBy(xpath = "//button/span[2][text()='View']")
	private WebElement btnView;

	@FindBy(xpath = "//button/span[2][text()='Edit']")
	private WebElement btnEdit;

	@FindBy(xpath = "//button/span[2][text()='Delete']")
	private WebElement btnDelete;

	@FindBy(xpath = "//button/span[2][text()='Back']")
	private WebElement btnBack;

	@FindBy(xpath = "/html/body/div[3]/div[1]/div/div[3]/div/div/form/div[3]/button[1]")
	private WebElement btnCnfrmCancel;

	@FindBy(xpath = "/html/body/div[3]/div[1]/div/div[3]/div/div/form/div[3]/button[2]")
	private WebElement btnCnfrmDelete;

	@FindBy(xpath = "//*[@id=\"saveStaffModal\"]/div/div/form/div[2]/div[2]/div/p")
	private List<WebElement> lblStaffNameError;

	@FindBy(xpath = "//table/tbody/tr[1]/td[2]/input")
	private WebElement lblViewName;

	@FindBy(xpath = "//table/tbody/tr[2]/td[2]/input")
	private WebElement lblViewCode;
	// Accessing Branch GRID

	@FindBy(xpath = "//table/tbody/tr")
	private List<WebElement> rowStaffTable;

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
	public void clickCreateNewStaff() {
		btnCreateNewStaff.click();
		this.waitForElementVisibility(txtbxName);
		this.waitForElementToBeClickable(txtbxName);
	}

	public void enterStaffName(String branch) {
		txtbxName.clear();
		txtbxName.sendKeys(branch);
	}

	public void selectBranchName(String branchName) {

		new Select(drpdwnBranch).selectByVisibleText(branchName);
	}

	public void clickSave() {
		btnSave.click();
		this.waitForElementToBeClickable(btnCreateNewStaff);
	}

	public void clickCancel() {
		btnCancel.click();
		this.waitForElementVisibility(btnCreateNewStaff);
	}

	public void backToStaffs() {
		btnBack.click();
	}

	public void clickView() {
		driver.findElement(By.xpath("//table/tbody/tr[" + rowStaffTable.size() + "]/td[4]/button[1]")).click();
	}

	public void clickEdit() {
		// click Edit button from last rows
		driver.findElement(By.xpath("//table/tbody/tr[" + rowStaffTable.size() + "]/td[4]/button[2]")).click();
		this.waitForElementVisibility(txtbxName);
	}

	public void clickDelete() {
		// click delete button from last rows

		driver.findElement(By.xpath("//table/tbody/tr[" + rowStaffTable.size() + "]/td[4]/button[3]")).click();

	}

	public void clickConfirmDelete() {
		this.waitForElementVisibility(btnCnfrmDelete);
		btnCnfrmDelete.click();
		this.waitForElementVisibility(btnCreateNewStaff);
	}

	public void clickConfirmCancel() {
		this.waitForElementVisibility(btnCnfrmCancel);
		btnCnfrmCancel.click();
	}

	// Branch Grid
	public String[] getNewlyCreatedStaff() {
		this.waitForElementToBeClickable(btnView);

		int row = rowStaffTable.size();
		String[] staff = new String[2];
		String staffNamexpath = "//table/tbody/tr[" + row + "]/td[2]";
		String staffCodexpath = "//table/tbody/tr[" + row + "]/td[3]";

		staff[0] = driver.findElement(By.xpath(staffNamexpath)).getText();
		staff[1] = driver.findElement(By.xpath(staffCodexpath)).getText();

		return staff;
	}

	// Error message handling

	public String getStaffNameErrorMessage() {
		String msg = "";
		for (WebElement element : lblStaffNameError) {
			if (element.getAttribute("class").equals("help-block ng-scope")) {

				msg = element.getText();
				// System.out.println("Displayed and text: " + msg);
			}

		}
		Reporter.log("Verification of Branch Name Error Message " + msg + "->");
		return msg;
	}

	public String getLblViewStaffName() {
		return lblViewName.getAttribute("value");

	}

	public String getLblViewBranchName() {
		return lblViewCode.getAttribute("value");
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
