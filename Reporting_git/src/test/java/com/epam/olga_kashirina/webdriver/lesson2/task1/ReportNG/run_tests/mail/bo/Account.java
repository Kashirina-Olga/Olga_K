package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.run_tests.mail.bo;

public class Account {

	
	private String login = "seleniumexample";
	private String password = "seleniumexample1";
	private String mainUrl = "https://mail.yandex.ru/";
	
	public Account(String login, String password, String mainUrl) {
		super();
		this.login = login;
		this.password = password;
		this.mainUrl = mainUrl;
	}
	
	public Account() {
		
	}

	public String getMainUrl() {
		return mainUrl;
	}
	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
