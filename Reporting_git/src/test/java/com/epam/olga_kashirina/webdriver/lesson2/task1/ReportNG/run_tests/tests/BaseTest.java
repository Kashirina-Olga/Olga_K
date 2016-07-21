package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.run_tests.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;

import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.pageObject.LoginPage;

public class BaseTest {

	protected static WebDriver driver;
	
	public static WebDriver getDriver() {
		return driver;
	}


	@BeforeTest()
	public void startBrowser() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	public LoginPage navigate(String mainUrl) {
		driver.get(mainUrl);
		return new LoginPage(driver);
	}
	
	 @AfterClass(alwaysRun=true)
	 public void closeBrowser(){
	 driver.quit();
	 }
}
