package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.utils;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.Reporter;

public class ReportAppender extends AppenderSkeleton{

	@Override
	protected void append(LoggingEvent arg0) {
		String message = this.layout.format(arg0);
		message = message.replaceAll("\n", "<br>");
		Reporter.log(message);
	}

	@Override
	public void close() {
		
	}

	@Override
	public boolean requiresLayout() {
		return true;
	}

}
