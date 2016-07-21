package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.webdriver.Driver;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.run_tests.tests.RemoteBaseTest;

public class ScreenShotListener implements ITestListener {
	
	private void screenMake(ITestResult result){
		Object instance = result.getInstance();
		
		if (instance==null){
			return;
		}
		
		if(!(instance instanceof RemoteBaseTest)){
			return;
		}
		
		WebDriver driver = Driver.getWebDriverInstance();
		
		if (driver!=null){
			ScreenShot.make(driver, "AutoScreenShot");
		}
	}
	
	@Override
	public void onTestStart(ITestResult result) {

		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
	
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		screenMake(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		screenMake(result);
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
		screenMake(result);
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
