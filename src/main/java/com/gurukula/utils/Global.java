package com.gurukula.utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.Properties;

public class Global {
	public WebDriver driver;
	public static Map<String, String> configMap;

	public Global() {
		this.getProperty();
	}

	public void getProperty() {
		configMap = new HashMap<String, String>();
		Properties prop = new Properties();

		try {
			FileInputStream inputStream = new FileInputStream("config.properties");
			prop.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in loading config.properties...! " + e.getMessage());

		}
		for (final Entry<Object, Object> entry : prop.entrySet()) {
			configMap.put((String) entry.getKey(), (String) entry.getValue());
			// System.out.println(entry.getKey() + " " + entry.getValue());
		}

	}

	// Setup function
	public WebDriver setup() {

		if (Global.configMap.get("test.browser").equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
			driver = new ChromeDriver();

		} else if (Global.configMap.get("test.browser").equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			System.out.println("Check browser settings in config file!");
		}

		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Global.configMap.get("app.implicitwait")),
				TimeUnit.SECONDS);

		driver.manage().window().maximize();
		driver.get(Global.configMap.get("app.url"));

		return driver;

	}

	// Cleanup function
	public void cleanup() {
		driver.quit();
	}
}
