package com.kchetty.ProductOrders.Beans;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "errorLogs")
public class ErrorLog {

	private String className;
	private String methodName;
	private String logMessage;
	private Date logDate;
	
	
	
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getLogMessage() {
		return logMessage;
	}
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	
	
	
}
