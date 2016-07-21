package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.pageObject;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class DraftsPage {

	private static final String XPATH = "(//span[@title=";
	private static final String BRACKET = "])[1]";
	private static final char QUOTE_2 = '"';
	private static final char QUOTE_1 = '"';
	private static final Logger LOG = Logger.getLogger(DraftsPage.class);

	private WebDriver driver;

	@FindBy(xpath = "(//input [@class='b-messages-head__checkbox' and  @type='checkbox'])[2]")
	private WebElement checkBoxDrafts;

	@FindBy(xpath = "(//*[text()='Удалить'])[1]")
	private WebElement buttonDelete;

	@FindBy(xpath = "//a[@title='Отправленные' and @href='#sent']")
	private WebElement sendedLetterButton;

	public DraftsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public LetterPage clickAtCheckingDraft(String recipient, String subject, String textOfLetter) {
		Actions actions = new Actions(driver);
		WebElement lastDraft = driver.findElement(By.xpath(XPATH + QUOTE_1 + subject + QUOTE_2 + BRACKET));
		actions.click(lastDraft).build().perform();
		LOG.info("click at the draft with subject: '" + subject + "'");
		return new LetterPage(driver);
	}

	public DraftsPage deleteDrafts() {
		Actions actions = new Actions(driver);
		actions.click(checkBoxDrafts).build().perform();
		LOG.info("click on button 'Черновики(checkbox)'");
		actions.click(buttonDelete).build().perform();
		LOG.info("click on button 'Удалить'");
		return this;
	}

	public SendedLettersPage clickSendedLettersButton() {
		Actions actions = new Actions(driver);
		actions.click(sendedLetterButton).build().perform();
		return new SendedLettersPage(driver);
	}

}
