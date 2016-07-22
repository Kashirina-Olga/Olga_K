package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {

	private static final String PATH_TO_REPORT = "target/surefire-reports/html/";
	private static final String SCREENSHOTS_FOLDER = "screenshots";
	private static final String DEFAULT_MESSAGE = "See ScreenShot";
	private static final Logger LOG = Logger.getLogger(ScreenShot.class);

	public static void deleteAll() {
		File directory = new File(PATH_TO_REPORT + SCREENSHOTS_FOLDER);
		LOG.info("Delete all files from folder" + directory.getAbsolutePath());
		File[] files = directory.listFiles();
		if (files != null && files.length > 0) {
			for (File file : files) {
				if (!file.delete()) {
					LOG.info("Cannot delete file " + file);
				}
			}
		}
	}

	public static void make(WebDriver driver) {
		make(driver, DEFAULT_MESSAGE);
	}

	public static void make(WebDriver driver, String message) {
		if (driver == null) {
			return;
		}

		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFileToDirectory(screenshot, new File(PATH_TO_REPORT + SCREENSHOTS_FOLDER));
			String logMessage = "<a href = '" + SCREENSHOTS_FOLDER + "/" + screenshot.getName() + "'>" + message
					+ "</a>";
			LOG.info(logMessage);
		} catch (Exception e) {
			LOG.error("Error of making a screenshot!" + e);
			throw new RuntimeException(e);
		}
	}
}
