package com.gurukula.utils;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestDriver {

	private WebDriver driver;

	Global gbl = new Global();

	@BeforeSuite
	public void setup() throws Exception {

		if (Global.configMap.get("test.browser").equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
			driver = new ChromeDriver();

		} else if (Global.configMap.get("test.browser").equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			System.out.println("Check browser settings in config file!");
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.manage().window().maximize();
		driver.get(Global.configMap.get("app.url"));

	}

	@AfterSuite
	public void cleanup() {
		driver.quit();
	}
}
