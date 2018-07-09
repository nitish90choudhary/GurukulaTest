package com.gurukula.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gurukula.pages.BranchPage;
import com.gurukula.pages.HomePage;
import com.gurukula.pages.LoginPage;
import com.gurukula.pages.MenuPage;
import com.gurukula.utils.Global;

public class BranchTest {

	private Global gbl;
	private WebDriver driver;

	LoginPage loginpage;
	HomePage homepage;
	MenuPage menupage;
	BranchPage branchpage;

	@BeforeTest
	public void startTest() {
		gbl = new Global();
		driver = gbl.setup();
		loginpage = new LoginPage(driver);
		homepage = new HomePage(driver);
		menupage = new MenuPage(driver);
		branchpage = new BranchPage(driver);
		homepage.clickLogin();
		loginpage.login("admin", "admin");

	}

	@Test(dataProvider = "dataForBranchTest", enabled = true)
	public void M4_TS01_Create_newBranch(String branchName, String branchCode) {

		menupage.clickBranch();

		branchpage.clickCreatNewBranch();

		branchpage.enterBranchName(branchName);
		branchpage.enterBranchCode(branchCode);

		branchpage.clickSave();

		// Verify Newly created branch
		String branchDetails[] = branchpage.getNewlyCreatedBranch();
		Reporter.log("Verification of Newly created Branch Name");
		assertEquals(branchDetails[0], branchName);
		Reporter.log("Verification of Newly created Branch Code");
		assertEquals(branchDetails[1], branchCode);
	}

	@Test(dataProvider = "dataForBranchErrorHandlingTest", enabled = true)
	public void M4_TS02_Verify_BranchCreationError_Message_Handling(String id, String branchName, String branchCode,
			String msg) {

		menupage.clickBranch();

		branchpage.clickCreatNewBranch();

		switch (id) {
		case "1":
			assertEquals(branchpage.getBranchNameErrorMessage(), msg);
			break;
		case "2":
			branchpage.enterBranchName(branchName);
			branchpage.enterBranchCode(branchCode);
			assertEquals(branchpage.getBranchNameErrorMessage(), msg);
			break;

		case "3":
			branchpage.enterBranchName(branchName);
			branchpage.enterBranchCode(branchCode);
			assertEquals(branchpage.getBranchNameErrorMessage(), msg);
			break;
		case "4":
			branchpage.enterBranchName(branchName);
			branchpage.enterBranchCode(branchCode);
			assertEquals(branchpage.getBranchNameErrorMessage(), msg);
			break;
		case "5":
			branchpage.enterBranchName(branchName);
			branchpage.enterBranchCode(branchCode);
			assertEquals(branchpage.getBranchCodeErrorMessage(), msg);
			break;
		case "6":
			branchpage.enterBranchName(branchName);
			branchpage.enterBranchCode(branchCode);
			assertEquals(branchpage.getBranchCodeErrorMessage(), msg);
			break;
		case "7":
			branchpage.enterBranchName(branchName);
			branchpage.enterBranchCode(branchCode);
			assertEquals(branchpage.getBranchCodeErrorMessage(), msg);
			break;

		case "8":
			branchpage.enterBranchName(branchName);
			branchpage.enterBranchCode(branchCode);
			assertEquals(branchpage.getBranchCodeErrorMessage(), msg);
			break;

		}
		branchpage.clickCancel();

	}

	@Test(enabled = true)
	public void M4_TS03_Verify_BranchEdit() {
		// Create a new branch
		M4_TS01_Create_newBranch("Original", "ORIG01");
		// Edit the newly created Branch
		branchpage.clickEdit();

		branchpage.enterBranchName("Verify Edit");
		branchpage.enterBranchCode("EDIT01");
		branchpage.clickSave();

		// Verify if Branch is Edited or not

		String branchDetails[] = branchpage.getNewlyCreatedBranch();
		Reporter.log("Verification of recently Edited Branch Name");
		assertTrue(branchDetails[0].equals("Verify Edit"));
		Reporter.log("Verification of Newly Edited Branch Code");
		assertTrue(branchDetails[1].equals("EDIT01"));

	}

	@Test(enabled = true)
	public void M4_TS04_Verify_BranchDelete() {

		// Create a new branch
		M4_TS01_Create_newBranch("Verify Delete", "DEL01");

		// Delete the newly created Branch
		branchpage.clickDelete();
		branchpage.clickConfirmDelete();

		// refresh DOM
		driver.navigate().refresh();

		// Verify if Branch is deleted or not

		String branchDetails[] = branchpage.getNewlyCreatedBranch();
		Reporter.log("Verification of recently deleted Branch Name");
		assertFalse(branchDetails[0].equals("Verify Delete"));
		Reporter.log("Verification of Newly deleted Branch Code");
		assertFalse(branchDetails[1].equals("DEL01"));

	}

	@Test
	public void M4_TS05_Verify_BranchView() {
		// Create a new branch
		M4_TS01_Create_newBranch("Verify Branch View", "VIEW01");

		// Click View button of the newly created Branch
		branchpage.clickView();
		Reporter.log("Verification of recently created Branch Name");
		assertEquals(branchpage.getLblViewBranchName(), "Verify Branch View");

		Reporter.log("Verification of Newly created Branch Code");
		assertEquals(branchpage.getLblViewBranchCode(), "VIEW01");

	}

	@AfterMethod

	public void goToHomePage() {

		menupage.clickHome();

	}

	@AfterTest
	public void finishTest() {
		gbl.cleanup();
	}

	// TEST DATA FOR STAFF TEST
	@DataProvider(name = "dataForBranchTest")
	public Object[][] getBranchData() {
		return new Object[][] { { "Computers", "ITC01" }, { "Computer Networks", "CN01" }, { "C Programming", "CP01" },
				{ "Python Programming", "CP02" }, { "Java Programming", "CP03" }, };
	}

	@DataProvider(name = "dataForBranchErrorHandlingTest")
	public Object[][] getBranchErrorMessageData() {
		return new Object[][] { { "1", "", "", "This field is required." },
				{ "2", "Com", "ITC01", "This field is required to be at least 5 characters." },
				{ "3", "BRANCHNAME BRANCHNAME", "ITC01", "This field cannot be longer than 20 characters." },
				{ "4", "Branch124", "ITC01", "This field should follow pattern ^[a-zA-Z\\s]*$." },
				{ "5", "", "", "This field is required." },
				{ "6", "BRANCHNAME", "I", "This field is required to be at least 2 characters." },
				{ "7", "BRANCHNAME", "ITC01234567", "This field cannot be longer than 10 characters." },
				{ "8", "BRANCHNAME", "abcd01", "This field should follow pattern ^[A-Z0-9]*$." }

		};
	}

}
