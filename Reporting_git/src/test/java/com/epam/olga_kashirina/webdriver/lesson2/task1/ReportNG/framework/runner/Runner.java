package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.runner;

import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.testng.Reporter;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.config.Settings;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.exceptions.TestNgRunException;
import com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.webdriver.Driver;

public class Runner {


	protected TestNG testng = new TestNG();
	private String testNgConfig;
	private static final Logger LOG = Logger.getLogger(Runner.class);
	
	public static void main(String[] args) {
		try {
			new Runner(args).run();
		} catch (Exception exc) {
			Reporter.log(exc.getMessage(), 2, true);
		} finally {
			Driver.getWebDriverInstance().close();
		}
		
	}
	

	private void run() {
		try {
			XmlSuite xmlSuite = new Parser(testNgConfig).parseToList().get(0);
			this.testng.setCommandLineSuite(xmlSuite);
			this.testng.run();
		} catch (Exception ex) {
			throw new TestNgRunException("Error running TetNg suite!" + ex.getMessage());
		}
		
	}


	public Runner(String[] args) {
		Settings settings = new Settings();
		CmdLineParser parser = new CmdLineParser(settings);
		try {
			parser.parseArgument(args);
			testNgConfig = settings.testNgPath;
			LOG.info("Settings:" + settings);
			
		} catch (CmdLineException e) {
			//System.err.println("error:"+ e.toString());
			LOG.info("error:"+ e.toString());
			parser.printUsage(System.out);
		}
	}
	
	
}
