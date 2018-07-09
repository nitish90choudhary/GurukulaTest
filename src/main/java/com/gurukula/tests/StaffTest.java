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

import com.gurukula.pages.HomePage;
import com.gurukula.pages.LoginPage;
import com.gurukula.pages.MenuPage;
import com.gurukula.pages.StaffPage;
import com.gurukula.utils.Global;

public class StaffTest {

	private Global gbl;
	private WebDriver driver;

	LoginPage loginpage;
	HomePage homepage;
	MenuPage menupage;
	StaffPage staffpage;

	@BeforeTest
	public void startTest() {
		gbl = new Global();
		driver = gbl.setup();
		loginpage = new LoginPage(driver);
		homepage = new HomePage(driver);
		menupage = new MenuPage(driver);
		staffpage = new StaffPage(driver);
		homepage.clickLogin();
		loginpage.login("admin", "admin");

	}

	@Test(dataProvider = "dataForStaffTest", enabled = true)
	public void M5_TS01_Create_new_staff(String staffName, String branchName) {

		menupage.clickStaff();

		staffpage.clickCreateNewStaff();

		staffpage.enterStaffName(staffName);

		staffpage.selectBranchName(branchName);

		staffpage.clickSave();
		driver.navigate().refresh();

		// Verify Newly created branch
		String staffDetails[] = staffpage.getNewlyCreatedStaff();
		Reporter.log("Verification of Newly created Staff Name");
		assertEquals(staffDetails[0], staffName);
		Reporter.log("Verification of Newly created Branch Names");
		assertEquals(staffDetails[1], branchName);

	}

	@Test(dataProvider = "dataForStaffErrorHandlingTest", enabled = true)
	public void M5_TS02_Verify_StaffCreationError_Message_Handling(String id, String staffName, String msg) {
		menupage.clickStaff();

		staffpage.clickCreateNewStaff();

		switch (id) {
		case "1":
			assertEquals(staffpage.getStaffNameErrorMessage(), msg);
			break;
		case "2":
			staffpage.enterStaffName(staffName);
			assertEquals(staffpage.getStaffNameErrorMessage(), msg);
			break;

		case "3":
			staffpage.enterStaffName(staffName);
			assertEquals(staffpage.getStaffNameErrorMessage(), msg);
			break;

		}

		staffpage.clickCancel();

	}

	@Test(enabled = true)
	public void M5_TS03_Verify_StaffEdit() {
		// Create a new Staff
		M5_TS01_Create_new_staff("Mr Original", "Computers");
		// Edit the newly created staff
		staffpage.clickEdit();

		staffpage.enterStaffName("Mr Edited Staff");
		staffpage.selectBranchName("Verify Edit");

		staffpage.clickSave();

		// Verify if Staff is Edited or not

		String staffDetail[] = staffpage.getNewlyCreatedStaff();
		Reporter.log("Verification of recently Edited Staff Name");
		assertTrue(staffDetail[0].equals("Mr Edited Staff"));
		Reporter.log("Verification of Newly Edited Branch Code");
		assertTrue(staffDetail[1].equals("Verify Edit"));

	}

	@Test
	public void M5_TS04_Verify_StaffDelete() {
		// Create a new Staff
		M5_TS01_Create_new_staff("Mr To be Deleted", "Python Programming");
		// Delete the newly created staff
		staffpage.clickDelete();
		staffpage.clickConfirmDelete();

		// refresh DOM
		driver.navigate().refresh();

		// Verify if Staff is deleted or not

		String staffDetails[] = staffpage.getNewlyCreatedStaff();
		Reporter.log("Verification of recently deleted Staff Name");
		assertFalse(staffDetails[0].equals("Mr To be Deleted"));
		Reporter.log("Verification of recently deleted Branch Code");
		assertFalse(staffDetails[1].equals("Python Programming"));

	}

	@Test
	public void M5_TS05_Verify_StaffView() {
		// Create a new Staff
		M5_TS01_Create_new_staff("Verify Branch View", "C Programming");

		// Click View button of the newly created Staff
		staffpage.clickView();
		Reporter.log("Verification of recently created Staff Name");
		assertEquals(staffpage.getLblViewStaffName(), "Verify Branch View");

		Reporter.log("Verification of Newly created Branch Name");
		assertEquals(staffpage.getLblViewBranchName(), "C Programming");

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
	@DataProvider(name = "dataForStaffTest")
	public Object[][] getStaffData() {
		return new Object[][] { { "Mr Adam Smith", "Computers" }, { "Mrs Elizabeth Sen", "Computer Networks" },
				{ "Dr Peter Kokes", "Python Programming" }, { "Prof Stefan Ring", "Computer Networks" },
				{ "Mr Richard Stamm", "Java Programming" }, { "Mr Empty", "" }, };
	}

	@DataProvider(name = "dataForStaffErrorHandlingTest")
	public Object[][] getStaffErrorMessageData() {
		return new Object[][] { { "1", "name", "This field is required." },
				{ "2", "ProfessorName ProfessorName ProfessorName Professor",
						"This field cannot be longer than 50 characters." },
				{ "3", "Prof Adam1235", "This field should follow pattern ^[a-zA-Z\\s]*$." },

		};
	}

}
