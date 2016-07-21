package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;


public class LoginPage {

	private  WebDriver driver;
	
	@FindBy(name = "login")
	private WebElement loginInput;
	@FindBy(name = "passwd")
	private WebElement passwordInput;
	@FindBy(how = How.XPATH, using = "//*[text()='Войти']")
	private WebElement submitButton;

	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public LoginPage setLogin(String login) {
		loginInput.clear();
		Actions action = new Actions(driver);
		action.sendKeys(loginInput, login).build().perform();
		return this;
	}

	public LoginPage setPassword(String password) {
		passwordInput.clear();
		Actions action = new Actions(driver);
		action.sendKeys(passwordInput, password).build().perform();
		return this;
	}

	public MainPage clickSubmitButton() {
		Actions action = new Actions(driver);
		action.click(submitButton).build().perform();
		return new MainPage(driver);
	}

}
