package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.pageObject;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

public class LetterPage {

	private static final String LETTER_SUBJECT_FIELD = "//span [contains (@class,'js-message-subject')]";
	private static final String LETTER_BODY_FIELD = "//div[@class='b-message-body__content' and @data-lang='1']/p";
	private static final String THE_SUBJECT_OF_DRAFT_DOES_NOT_COINCIDE_WITH_THE_SUBJECT_OF_LETTER = "The subject of draft does not coincide with the subject of letter";
	private static final String THE_BODY_OF_DRAFT_DOES_NOT_COINCIDE_WITH_THE_BODY_OF_LETTER = "The body of draft does not coincide with the body of letter";
	private static final String VALUE = "value";
	private static final String BODY_FIELD = "compose-send";
	private static final String SUBJECT_FIELD = "compose-subj";
	private static final Logger LOG = Logger.getLogger(LetterPage.class);

	private WebDriver driver;

	@FindBy(xpath = "//div[@class='b-mail-input__yabbles']")
	private WebElement senderField;

	@FindBy(id = "compose-subj")
	private WebElement subjectField;

	@FindBy(id = "compose-send")
	private WebElement bodyField;

	@FindBy(xpath = "//span[contains(@class, 'message__actions__helper_saved')]")
	private WebElement saveInDraftLink;

	// @FindBy(xpath = "//a[@title='Входящие (Ctrl + i)']")
	// private WebElement inboxButton;

	@FindBy(xpath = "(//a[@title='Черновики'])[1]")
	private WebElement draftsButton;

	@FindBy(xpath = "(//button[@title = 'Отправить письмо (Ctrl + Enter)'])[1]")
	private WebElement sendButton;

	@FindBy(xpath = "//a[@title='Отправленные' and @href='#sent']")
	private WebElement sendedLetterButton;

	public LetterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public Boolean isSubjectFieldPresented() {
		return new FluentWait<WebDriver>(driver).withTimeout(15, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class).until(new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver input) {
						return subjectField.isDisplayed();
					}

				});
	}

	public Boolean isSaveInDraftsLinkPresented() {
		return new FluentWait<WebDriver>(driver).withTimeout(15, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class).until(new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver input) {
						return saveInDraftLink.isDisplayed();
					}

				});
	}

	public LetterPage setFieldsOfLetter(String recipient, String subject, String textOfLetter) {
		Actions action = new Actions(driver);
		action.click(senderField);
		LOG.info("click on field 'Кому'");
		action.sendKeys(senderField, recipient).build().perform();
		LOG.info("send key '" + recipient + "' on field 'Кому'");
		action.sendKeys(subjectField, subject).build().perform();
		LOG.info("send key '" + subject + "' on field 'Тема'");
		WebElement bodyField = (WebElement) ((JavascriptExecutor) driver)
				.executeScript("return document.getElementById('compose-send');");
		bodyField.sendKeys(textOfLetter);
		LOG.info("send key '" + textOfLetter + "' on field 'Текст письма'");

		return this;
	}

	public MainPage clickInboxButton() {

		WebElement inboxButton = driver.findElement(By.xpath("//a[@title='Входящие (Ctrl + i)']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", inboxButton);
		return new MainPage(driver);
	}

	public void assertDrafts(String subject, String textOfLetter) {

		Assert.assertEquals(driver.findElement(By.id(BODY_FIELD)).getAttribute(VALUE), textOfLetter,
				THE_BODY_OF_DRAFT_DOES_NOT_COINCIDE_WITH_THE_BODY_OF_LETTER);

		Assert.assertEquals(driver.findElement(By.id(SUBJECT_FIELD)).getAttribute(VALUE), subject,
				THE_SUBJECT_OF_DRAFT_DOES_NOT_COINCIDE_WITH_THE_SUBJECT_OF_LETTER);

	}

	public void assertLetters(String subject, String textOfLetter) {
		Assert.assertEquals(driver.findElement(By.xpath(LETTER_BODY_FIELD)).getText(), textOfLetter,
				THE_BODY_OF_DRAFT_DOES_NOT_COINCIDE_WITH_THE_BODY_OF_LETTER);

		Assert.assertEquals(driver.findElement(By.xpath(LETTER_SUBJECT_FIELD)).getText(), subject,
				THE_SUBJECT_OF_DRAFT_DOES_NOT_COINCIDE_WITH_THE_SUBJECT_OF_LETTER);
	}

	public DraftsPage clickDraftsButton() {
		Actions actions = new Actions(driver);
		actions.click(draftsButton).build().perform();
		return new DraftsPage(driver);
	}

	public SendedLettersPage sendLetter() {
		Actions actions = new Actions(driver);
		actions.click(sendButton).build().perform();
		LOG.info("click on button 'Отправить'");
		return new SendedLettersPage(driver);
	}

	public SendedLettersPage clickSendedLettersButton() {
		Actions actions = new Actions(driver);
		actions.click(sendedLetterButton).build().perform();
		return new SendedLettersPage(driver);
	}

}
