package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.run_tests.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.webdriver.Driver;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.utils.ScreenShot;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.run_tests.mail.bo.Account;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.run_tests.mail.bo.Letter;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.run_tests.mail.service.MailService;

public class YandexTest extends RemoteBaseTest {

	private Account account;
	private MailService mailService = new MailService();
	WebDriver driver = Driver.getWebDriverInstance();
	private static final Logger LOG = Logger.getLogger(YandexTest.class);
	private static final String SPACE = "                    ";

	@Test
	public void loginTest() {
		LOG.info("start 'login test'");
		Properties property = createProperty(
				"src/test/java/com/epam/olga_kashirina/webdriver/lesson2/task1/ReportNG/framework/properties/property_login.properties");
		account = new Account(property.getProperty("LOGIN"), property.getProperty("PASSWORD"),
				property.getProperty("MAIN_URL"));

		//Assert.assertTrue(false);
		LOG.info("start 'loginToMail method'");
		mailService.loginToMail(account);
		LOG.info("finish 'loginToMail method'");
		ScreenShot.make(driver);
		LOG.info("finish 'login test'");
	}

	@DataProvider(name = "parametersForLetter")
	public String[][] getValue() {
		Properties property = createProperty(
				"src/test/java/com/epam/olga_kashirina/webdriver/lesson2/task1/ReportNG/framework/properties/property_letter.properties");

		return new String[][] {
				{ property.getProperty("ADRESS"), property.getProperty("FIRST_SUBJECT"),
						property.getProperty("FIRST_BODY") },
				{ property.getProperty("ADRESS"), property.getProperty("SECOND_SUBJECT"),
						property.getProperty("SECOND_BODY") } };

	}

	private Properties createProperty(String path) {
		FileInputStream fileInput;
		Properties property = new Properties();
		try {
			fileInput = new FileInputStream(path);
			property.load(fileInput);

		} catch (IOException e) {
			System.err.println("Error! File does not exists!");
		}
		return property;
	}

	@Test(dataProvider = "parametersForLetter", dependsOnMethods = "loginTest")
	public void writeLetter(String recipient, String subject, String textOfLetter) {
		LOG.info("start 'writeLetter test'");
		Letter letter = new Letter(recipient, subject, textOfLetter);
		LOG.info("filling the BO - letter:\n" + SPACE + "recipient: " + recipient + " subject: " + subject
				+ " textOfLetter: " + textOfLetter);
		LOG.info("start 'writeMessage method'");
		mailService.writeMessage(letter);
		LOG.info("finish 'writeMessage method'");
		LOG.info("finish 'writeLetter test'");
	}

	@Test(dependsOnMethods = "writeLetter", dataProvider = "parametersForLetter")
	public void checkTheDrafts(String recipient, String subject, String textOfLetter) throws InterruptedException {
		LOG.info("start 'checkTheDrafts test'");
		Letter letter = new Letter(recipient, subject, textOfLetter);
		LOG.info("filling the BO - letter:\n" + SPACE + "recipient: " + recipient + " subject: " + subject
				+ " textOfLetter: " + textOfLetter);
		LOG.info("start 'draftsCheck' method");
		mailService.draftsCheck(letter);
		LOG.info("finish 'draftsCheck' method");
		LOG.info("finish 'checkTheDrafts test'");
	}

	@Test(dependsOnMethods = "checkTheDrafts", dataProvider = "parametersForLetter")
	public void sendLetter(String recipient, String subject, String textOfLetter) throws InterruptedException {
		LOG.info("start 'sendLetter test'");
		Letter letter = new Letter(recipient, subject, textOfLetter);
		LOG.info("filling the BO - letter:\n" + SPACE + "recipient: " + recipient + " subject: " + subject
				+ " textOfLetter: " + textOfLetter);
		LOG.info("start 'sendMessage' method");
		mailService.sendMessage(letter);
		LOG.info("finish 'sendMessage' method");
		LOG.info("finish 'sendLetter test'");
	}

	@Test(dependsOnMethods = "sendLetter", dataProvider = "parametersForLetter")
	public void checkSendedLetters(String recipient, String subject, String textOfLetter) {
		LOG.info("start 'checkSendedLetters test'");
		Letter letter = new Letter(recipient, subject, textOfLetter);
		LOG.info("filling the BO - letter:\n" + SPACE + "recipient: " + recipient + " subject: " + subject
				+ " textOfLetter: " + textOfLetter);
		LOG.info("start 'checkSendedMessage' method");
		mailService.checkSendedMessage(letter);
		LOG.info("finish 'checkSendedMessage' method");
		LOG.info("finish 'checkSendedLetters test'");
	}

	@AfterClass(alwaysRun = true)
	public void cleanTheDrafts() throws InterruptedException {
		LOG.info("start 'cleanTheDrafts test'");
		LOG.info("start 'cleanAllDrafts' method");
		mailService.cleanAllDrafts();
		LOG.info("finish 'cleanAllDrafts' method");
		LOG.info("finish 'cleanTheDrafts test'");
	}

	@AfterClass(dependsOnMethods = "cleanTheDrafts", alwaysRun = true)
	public void cleanTheSendedLetters() throws InterruptedException {
		LOG.info("start 'cleanTheSendedLetters test'");
		LOG.info("start 'cleanAllSendedLetters' method");
		mailService.cleanAllSendedLetters();
		LOG.info("finish 'cleanAllSendedLetters' method");
		LOG.info("finish 'cleanTheSendedLetters test'");
	}

	@AfterClass(dependsOnMethods = "cleanTheSendedLetters", alwaysRun = true)
	public void exitOfYandexMail() {
		LOG.info("start 'exitOfYandexMail test'");
		LOG.info("start 'exitFromMail' method");
		mailService.exitFromMail();
		LOG.info("finish 'exitFromMail' method");
		LOG.info("finish 'exitOfYandexMail test'");
	}

}
