package com.kchetty.ProductOrders.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.kchetty.ProductOrders.Utility.ErrorLogs;

public class HttpRequest {
	
	@Autowired
	ErrorLogs errorLogs;

	public static <T> T httpCall(String Uri, HttpEntity<?> request, HttpMethod type, Class<?> restype, T t) {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		if (type == HttpMethod.POST)
			t = (T) restTemplate.postForObject(Uri, request, restype);
		else {
			ResponseEntity<?> response = restTemplate.exchange(Uri, type, request, restype);
			t = (T) response.getBody();
		}
		
		

		return t;
	}

	public static <T> HttpEntity<T> setRequest(MediaType mediaType, T body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		return new HttpEntity<T>(body, headers);
	}

}
