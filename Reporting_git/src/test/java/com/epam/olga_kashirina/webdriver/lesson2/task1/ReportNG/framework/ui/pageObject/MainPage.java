package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.pageObject;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;


public class MainPage {
	
	@FindBy(how = How.XPATH, using = "//a[@data-metric='Меню сервисов:Выход']")
	private WebElement exitButton;
	
	@FindBy(how = How.LINK_TEXT, using = "seleniumexample@yandex.ru")
	private WebElement mailLink;
	
	@FindBy(xpath = "//*[text()='Написать']")
	private WebElement writeLetterButton;
	
	@FindBy(xpath = "(//a[@title='Черновики'])[1]")
	private WebElement draftsButton;
	
	private  WebDriver driver;
	
	public MainPage (WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public Boolean isExitButtonPresented(){
		return new FluentWait<WebDriver>(driver)
				.withTimeout(15, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.until(new ExpectedCondition<Boolean>() {
					
			public Boolean apply(WebDriver input) {
				return exitButton.isDisplayed();
			}

		});
	}
	
	public Boolean isMailLinkPresented(){
		return new FluentWait<WebDriver>(driver)
				.withTimeout(15, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.until(new ExpectedCondition<Boolean>() {
					
			public Boolean apply(WebDriver input) {
				return mailLink.isDisplayed();
			}

		});
	}
	
	public LetterPage clickWriteLetterButton(){
		Actions action = new Actions(driver);
		action.click(writeLetterButton).build().perform();
		
		new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS).pollingEvery(3, TimeUnit.SECONDS)
		.ignoring(NoSuchElementException.class).until(new ExpectedCondition<WebElement>() {

			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.xpath("//div[@class='b-mail-input__yabbles']"));
			}

		});
		
		return new LetterPage(driver);
	}
	
	
	public DraftsPage clickDraftsButton() {
		Actions actions = new Actions(driver);
		actions.click(draftsButton).build().perform();
		return new DraftsPage(driver);

	}
}
