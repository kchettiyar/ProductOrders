package com.kchetty.ProductOrders.Utility;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kchetty.ProductOrders.Beans.ErrorLog;
import com.kchetty.ProductOrders.Repos.ErrorLogRepository;

@Service
public class ErrorLogs {

	@Autowired
	ErrorLogRepository errorLogRepository;

	public void saveErrorLog(Exception ex) {
		try {
			// ex.printStackTrace();
			ErrorLog errorLog = new ErrorLog();
			errorLog.setClassName(ex.getStackTrace()[0].getClassName());
			errorLog.setMethodName(
					ex.getStackTrace()[0].getMethodName() + "(" + ex.getStackTrace()[0].getLineNumber() + ")");
			errorLog.setLogMessage(ex.getMessage());
			errorLog.setLogDate(new Date());
			errorLogRepository.save(errorLog);
		} catch (Exception e) {
		}

	}
}
