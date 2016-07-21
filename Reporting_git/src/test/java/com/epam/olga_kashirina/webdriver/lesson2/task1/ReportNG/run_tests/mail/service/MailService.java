package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.run_tests.mail.service;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.pageObject.DraftsPage;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.pageObject.LetterPage;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.pageObject.LoginPage;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.pageObject.MainPage;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.pageObject.SendedLettersPage;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.webdriver.Driver;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.run_tests.mail.bo.Account;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.run_tests.mail.bo.Letter;


public class MailService {
	
	private static final String DRAFTS_IS_SAVED_TEXT = "//span[contains(@class, 'message__actions__helper_saved')]";
	private static final String DRAFTS_BUTTON = "(//a[@title='Черновики'])[1]";
	private static final String BODY_OF_LETTER = "//div[@class='b-message-body__content' and @data-lang='1']/p";
	private static final String CHECKING_SENDED_LETTERS = " (//*[text()='Отправленные' ])[3]";
	private static final String WRITE_LETTER_BUTTON = "//*[text()='Написать']";
	private static final String LETTER_IS_SENDED_TEXT = "(//*[text()='Письмо успешно отправлено'])[1]";
	private static final String DRAFTS_CHECK = "(//input [@class='b-messages-head__checkbox' and  @type='checkbox'])[2]";
	private static final String DRAFTS_IS_EMPTY = "//*[text()='В папке «Черновики» нет писем.']";
	private static final String VALUE = "value";
	private static final String BODY_FIELD = "compose-send";
	private static final Logger LOG = Logger.getLogger(MailService.class);
	
	WebDriver driver = Driver.getWebDriverInstance();
	
	private void addFluentWait(final String xpath) {
		new FluentWait<WebDriver>(driver).withTimeout(15, TimeUnit.SECONDS).pollingEvery(3, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class).until(new ExpectedCondition<WebElement>() {

					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.xpath(xpath));
					}

				});
	}
	
	public void loginToMail(Account account){
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setLogin(account.getLogin());
		LOG.info("set login from account:" + account.getLogin());
		loginPage.setPassword(account.getPassword());
		LOG.info("set password from account:" + account.getPassword());
		MainPage mainPage = loginPage.clickSubmitButton();
		LOG.info("click on button 'Войти' ");
		Boolean flagOfMailLink = mainPage.isMailLinkPresented();
		Assert.assertTrue(flagOfMailLink, "Mail link does not presented!");
		LOG.info("check the mail link presented");
	}
	

	public void writeMessage(Letter letter){
		MainPage mainPage = new MainPage(driver);
		LetterPage letterPage = mainPage.clickWriteLetterButton();
		LOG.info("click on button 'Написать'");
		LOG.info("start 'setFieldsOfLetter method'");
		letterPage.setFieldsOfLetter(letter.getRecipient(), letter.getSubject(), letter.getBody());
		LOG.info("finish 'setFieldsOfLetter method'");
		Boolean flagOfSubjectField = letterPage.isSubjectFieldPresented();
		Assert.assertTrue(flagOfSubjectField, "Subject field does not presented!");
		LOG.info("check the subject field presented");
		addFluentWait(DRAFTS_IS_SAVED_TEXT);

		Boolean flagOfDraftsLink = letterPage.isSaveInDraftsLinkPresented();
		Assert.assertTrue(flagOfDraftsLink, "Drafts link does not presented!");
		LOG.info("check the drafts link presented");
		letterPage.clickInboxButton();
		LOG.info("click on button 'Входящие'");

		addFluentWait(DRAFTS_BUTTON);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void draftsCheck(Letter letter) throws InterruptedException{
		MainPage mainPage = new MainPage(driver);
		DraftsPage draftsPage = mainPage.clickDraftsButton();
		LOG.info("click on button 'Черновики'");
		addFluentWait(DRAFTS_CHECK);
		LOG.info("start 'clickAtCheckingDraft method'");
		LetterPage letterPage = draftsPage.clickAtCheckingDraft(letter.getRecipient(), letter.getSubject(),
				letter.getBody());
		LOG.info("finish 'clickAtCheckingDraft method'");
		new FluentWait<WebDriver>(driver).withTimeout(20, TimeUnit.SECONDS).pollingEvery(3, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class).until(new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver driver) {
						return !driver.findElement(By.id(BODY_FIELD)).getAttribute(VALUE).isEmpty();
					}

				});

		//Thread.sleep(3000);
		LOG.info("start 'assertDrafts method'"); 
		letterPage.assertDrafts(letter.getSubject(), letter.getBody());
		LOG.info("finish 'assertDrafts method'");

	}
	
	public void sendMessage(Letter letter) throws InterruptedException{
		LetterPage letterPage = new LetterPage(driver);
		MainPage mainPage = letterPage.clickInboxButton();
		LOG.info("click on button 'Входящие'");
		addFluentWait(WRITE_LETTER_BUTTON);

		LetterPage letterPage2 = mainPage.clickWriteLetterButton();
		LOG.info("click on button 'Написать'");
		LOG.info("start 'setFieldsOfLetter method'");
		letterPage2.setFieldsOfLetter(letter.getRecipient(), letter.getSubject(),
				letter.getBody());
		LOG.info("finish 'setFieldsOfLetter method'");
		LOG.info("start 'sendLetter' method");
		letterPage2.sendLetter();
		LOG.info("finish 'sendLetter' method");

		addFluentWait(LETTER_IS_SENDED_TEXT);
		Thread.sleep(3000);
	}
	
	public void checkSendedMessage(Letter letter){
		DraftsPage draftsPage = new DraftsPage(driver);
		SendedLettersPage sendedLettersPage = draftsPage.clickSendedLettersButton();
		LOG.info("click on button 'Отрпавленные'");
		addFluentWait(CHECKING_SENDED_LETTERS);

		driver.navigate().refresh();
		LOG.info("start 'clickAtCheckingLetter method'");
		LetterPage letterPage = sendedLettersPage.clickAtCheckingLetter(letter.getSubject());
		LOG.info("finish 'clickAtCheckingLetter method'");

		new FluentWait<WebDriver>(driver).withTimeout(20, TimeUnit.SECONDS).pollingEvery(3, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class).until(new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver driver) {
						return !driver.findElement(By.xpath(BODY_OF_LETTER)).getText().isEmpty();
					}

				});
		LOG.info("start 'assertLetters' method");
		letterPage.assertLetters(letter.getSubject(), letter.getBody());
		LOG.info("finish 'assertLetters' method");
	}
	
	public void cleanAllDrafts(){
		LetterPage letterPage = new LetterPage(driver);
		DraftsPage draftsPage = letterPage.clickDraftsButton();
		LOG.info("click on button 'Черновики'");
		addFluentWait(DRAFTS_CHECK);
		LOG.info("start 'deleteDrafts' method");
		draftsPage.deleteDrafts();
		LOG.info("finish 'deleteDrafts' method");
		addFluentWait(DRAFTS_IS_EMPTY);
	}
	
	public void cleanAllSendedLetters() throws InterruptedException{
		DraftsPage draftsPage = new DraftsPage(driver);
		SendedLettersPage sendedLettersPage = draftsPage.clickSendedLettersButton();
		LOG.info("click on button 'Отправленные'");
		Thread.sleep(3000);
		LOG.info("start 'deleteSendedLetters' method");
		sendedLettersPage.deleteSendedLetters();
		LOG.info("finish 'deleteSendedLetters' method");
	}
	
	public void exitFromMail(){
		SendedLettersPage sendedLettersPage = new SendedLettersPage(driver);
		LOG.info("start 'deleteSendedLetters' method");
		sendedLettersPage.exit();
		LOG.info("finish 'deleteSendedLetters' method");
	}
}
