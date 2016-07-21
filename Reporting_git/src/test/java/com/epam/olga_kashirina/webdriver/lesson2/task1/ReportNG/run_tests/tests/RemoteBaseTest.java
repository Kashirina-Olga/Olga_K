package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.run_tests.tests;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.uncommons.reportng.HTMLReporter;

import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.pageObject.LoginPage;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.webdriver.Driver;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.utils.ScreenShot;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.utils.ScreenShotListener;

 @Listeners({ HTMLReporter.class, ScreenShotListener.class })
 public class RemoteBaseTest {

	FileInputStream fileInput;
	Properties propertyStart = new Properties();
	protected static WebDriver driver;

	static {
		ScreenShot.deleteAll();
		System.setProperty("org.uncommons.reportng.escape-output", "false");

	}
	
	public  WebDriver getDriver() {
		return driver;
	}

	
	@BeforeTest()
	public void startBrowser() throws MalformedURLException {
		Driver.getWebDriverInstance().get("https://mail.yandex.ru/");
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.manage().window().maximize();
	}

	public LoginPage navigate(String url) {
		driver.get(url);
		return new LoginPage(driver);
	}

	// @AfterClass(alwaysRun = true)
	// public void closeBrowser() {
	// if (driver!=null){
	// driver.quit();
	// }
	// }
}
