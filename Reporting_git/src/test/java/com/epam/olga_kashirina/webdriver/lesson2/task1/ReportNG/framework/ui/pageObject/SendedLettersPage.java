package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.pageObject;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
//import com.epam.olga_kashirina.webdriver.lesson2.task1.SeleniumAdvanced.tests.RemoteBaseTest;

public class SendedLettersPage {
	
	private static final String XPATH = "(//span[@title=";
	private static final String BRACKET = "])[1]";
	private static final char QUOTE_2 = '"';
	private static final char QUOTE_1 = '"';
	private static final Logger LOG = Logger.getLogger(SendedLettersPage.class);
	private  WebDriver driver;
	
	@FindBy(xpath = "(//*[text()='Письмо успешно отправлено'])[1]")
	public  WebElement sendLetterText;
	
	@FindBy(xpath = "(//a[@title='Черновики'])[1]")
	private WebElement draftsButton;
	
	//@FindBy(xpath = "(//label[@class='b-messages-head__title'])[2]")
	@FindBy(xpath = "(//*[text()='Отправленные'])[3]")
	//@FindBy(xpath = "(//input[@class='b-messages-head__checkbox'])[2]")
	private WebElement checkBoxSendedLetters;
	
	@FindBy(xpath = "//a[@title='Удалить (Delete)']")
	private WebElement buttonDeleteSendedLetters;
	
	@FindBy(xpath = "//span[@class='header-user-pic b-mail-dropdown__handle']")
	private WebElement exitIcon;
	
	@FindBy(xpath = "//a[@data-metric='Меню сервисов:Выход']")
	private WebElement exitButton;
	
	@FindBy(xpath = "//a[@title='Отправленные' and @href='#sent']")
	private WebElement sendedLetterButton;

	public SendedLettersPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public Boolean isLetterSended() {
		return new FluentWait<WebDriver>(driver).withTimeout(15, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.SECONDS).ignoring(NoSuchElementException.class)
				.until(new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver input) {
						return sendLetterText.isDisplayed();
					}

				});
	}
	
	public DraftsPage clickDraftsButton() {
		Actions actions = new Actions(driver);
		actions.click(draftsButton).build().perform();
		return new DraftsPage(driver);
	}
	
	public SendedLettersPage deleteSendedLetters(){
		Actions actions = new Actions(driver);
		actions.click(checkBoxSendedLetters).build().perform();
		LOG.info("click on button 'Отправленные(checkbox)'");
		actions.click(buttonDeleteSendedLetters).build().perform();
		LOG.info("click on button 'Удалить'");
		return this;
	}
	
	public LetterPage clickAtCheckingLetter(String subject){
		Actions actions = new Actions(driver);
		WebElement lastDraft = driver.findElement(By.xpath(XPATH + QUOTE_1 + subject + QUOTE_2 + BRACKET));
		actions.click(lastDraft).build().perform();
		LOG.info("click on element " + XPATH + QUOTE_1 + subject + QUOTE_2 + BRACKET);
		return new LetterPage(driver);
	}
	
	public SendedLettersPage clickSendedLettersButton(){
		Actions actions = new Actions(driver);
		actions.click(sendedLetterButton).build().perform();
		return this;
	}
	
	public void exit() {
		Actions actions = new Actions(driver);
		actions.click(exitIcon).build().perform();
		LOG.info("click on exit icon");
		actions.click(exitButton).build().perform();
		LOG.info("click on button 'Выход'");
	
	}

}
