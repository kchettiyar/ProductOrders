package com.kchetty.ProductOrders.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.kchetty.ProductOrders.Services.DataComponent;
import com.kchetty.ProductOrders.Services.OrderManagmentService;
import com.kchetty.ProductOrders.Utility.ErrorLogs;

@RestController
@RequestMapping(value = "/componentDataService/orderManagmentSelfCareController")
public class OrderManagmentSelfCareController {

	@Autowired
	OrderManagmentService orderManagmentService;

	@Autowired
	ErrorLogs errorLogs;

	@RequestMapping(method = { RequestMethod.POST }, value = { "/placeOrder" }, produces = { "application/json" })
	public ResponseEntity<?> placeOrder(@RequestBody Map<String, Object> request) {
		try {
			return new ResponseEntity<>(orderManagmentService.placeOrder(request), HttpStatus.OK);
		} catch (HttpClientErrorException ex) {
			return new ResponseEntity<>(DataComponent.errorResponse(ex.getResponseBodyAsString()), HttpStatus.OK);
		} catch (Exception ex) {
			errorLogs.saveErrorLog(ex);
			return new ResponseEntity<>(DataComponent.errorResponse(ex.getMessage()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(method = { RequestMethod.POST }, value = { "/searchOrderselfcare" }, produces = {
			"application/json" })
	public ResponseEntity<?> getProductinventories(@RequestBody Map<String, Object> filter) {
		try {
			return new ResponseEntity<>(orderManagmentService.getOrdersByEmailId(filter), HttpStatus.OK);
		} catch (HttpClientErrorException ex) {
			return new ResponseEntity<>(DataComponent.errorResponse(ex.getResponseBodyAsString()), HttpStatus.OK);
		} catch (Exception ex) {
			errorLogs.saveErrorLog(ex);
			return new ResponseEntity<>(DataComponent.errorResponse(ex.getMessage()), HttpStatus.OK);
		}

	}

	@RequestMapping(method = { RequestMethod.POST }, value = { "/searchOrder" }, produces = { "application/json" })
	public ResponseEntity<?> getProductinventoriesByEmail(@RequestBody Map<String, Object> filter) {
		try {
			return new ResponseEntity<>(orderManagmentService.getordersByEmailNew(filter), HttpStatus.OK);
		} catch (HttpClientErrorException ex) {
			return new ResponseEntity<>(DataComponent.errorResponse(ex.getResponseBodyAsString()), HttpStatus.OK);
		}

		catch (Exception ex) {
			errorLogs.saveErrorLog(ex);
			return new ResponseEntity<>(DataComponent.errorResponse(ex.getMessage()), HttpStatus.OK);
		}
	}
}
