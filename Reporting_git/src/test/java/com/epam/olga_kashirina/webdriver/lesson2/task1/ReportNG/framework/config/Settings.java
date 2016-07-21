package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.config;

import org.kohsuke.args4j.Option;

public class Settings {

	@Option(name = "--testng", usage = "set path to testNG xml", required = true)
	public String testNgPath;

	@Option(name = "--mainUrl", usage = "set connection url")
	public String mainUrl;

	@Option(name = "--login", usage = "set connection user login", required = true)
	public String login;

	@Option(name = "--password", usage = "set connection password", required = true)
	public String password;

	@Override
	public String toString() {
		return "\ntestNgPath: " + testNgPath + "\nmainUrl: " + mainUrl + "\nlogin: " + login + "\npassword: " + password;
	}

}
