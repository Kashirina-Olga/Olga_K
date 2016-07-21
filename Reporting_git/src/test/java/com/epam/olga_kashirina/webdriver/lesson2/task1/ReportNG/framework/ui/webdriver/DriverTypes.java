package com.epam.olga_kashirina.webdriver.lesson2.task1.ReportNG.framework.ui.webdriver;

public enum DriverTypes {
	
	FIREFOX("firefox"), IE("internet explorer");
	
	private String driverName;

	
	
	private DriverTypes(String driverName) {
		this.driverName = driverName;
	}


	public String getDriverName() {
		return this.driverName;
	}

	
	
	

}
