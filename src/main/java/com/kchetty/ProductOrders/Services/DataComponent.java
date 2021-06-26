package com.kchetty.ProductOrders.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class DataComponent {

	/*
	 * public static <T> T objMapper(String data, Class<?> type, T format) { try {
	 * return (T) new ObjectMapper().readValue(data, type); } catch (Exception e) {
	 * 
	 * } return null; }
	 */

	@SuppressWarnings("unchecked")
	public static <T> T httpCall(String Uri, HttpEntity<?> request, HttpMethod type, Class<?> restype, T t) {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<?> response = restTemplate.exchange(Uri, type, request, restype);
		t = (T) response.getBody();

		return t;
	}

	@SuppressWarnings("unchecked")
	public static <T> T httpCallNew(String Uri, HttpEntity<?> request, HttpMethod type, Class<?> restype) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		ResponseEntity<?> response = restTemplate.exchange(Uri, type, request, restype);
		return (T) response.getBody();
	}

	@SuppressWarnings("unchecked")
	public static <T> T httpGet(String Uri, HttpEntity<?> request, HttpMethod type, Class<?> restype, T t) {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		ResponseEntity<?> response = restTemplate.getForEntity(Uri, restype);

		t = (T) response.getBody();

		return t;
	}

	public static <T> HttpEntity<T> setRequest(MediaType mediaType, T body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);

		if (body != null)
			return new HttpEntity<T>(body, headers);
		else
			return new HttpEntity<T>(headers);
	}

	public static String setEpochToDate(String value) throws ParseException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS", Locale.ENGLISH);
			sdf.setTimeZone(TimeZone.getTimeZone("CET"));
			return sdf.format(new java.util.Date(Long.parseLong(value) * 1000));
		} catch (Exception e) {

		}
		return value;
	}

	public static Map<String, Object> setNotification(String status, String message) {
		Map<String, Object> notfication = new HashMap<>();
		notfication.put("status", status);
		notfication.put("showNotification", "true");
		notfication.put("message", message);
		notfication.put("statusCode", "200");

		if (status.equals("error"))
			notfication.put("statusCode", "404");

		return notfication;
	}

	public static Map<String, Object> setNotification(String status, String message, String statusCode) {
		Map<String, Object> notfication = new HashMap<>();
		notfication.put("status", status);
		notfication.put("message", message);
		notfication.put("statusCode", "200");

		if (status.equals("error"))
			notfication.put("statusCode", statusCode);

		return notfication;
	}

	public static Map<String, Object> setException(String message) {
		if (message.equals("409 Conflict"))
			return setNotification("error", "Username Already Exist");
		else
			return setNotification("error", message);
	}

	public static Map<String, Object> errorResponse(String message) {
		Map<String, Object> notification = new HashMap<>();
		Map<String, Object> responseMap = new HashMap<>();

		if (message == null)
			message = "NULL pointer error!";

		notification.put("notification", DataComponent.setNotification("error", message));
		responseMap.put("responseMap", notification);
		return responseMap;
	}

	public static Map<String, Object> errorResponse(String message, String beanIds) {
		Map<String, Object> notification = new HashMap<>();
		Map<String, Object> responseMap = new HashMap<>();

		if (message == null)
			message = "NULL pointer error!";

		notification.put("notification", DataComponent.setNotification("error", message));

		if (!beanIds.equals("")) {
			String[] beans = beanIds.split(",");

			for (String beanStr : beans) {
				String[] bean = beanStr.split("\\.");

				if (bean.length == 1)
					notification.put(bean[0], "");
				else if (bean.length == 2)
					notification.put(bean[0], (bean[1].equals("[]") ? new ArrayList<>() : bean[1]));
				else if (bean.length == 3) {
					Map<String, Object> beanVal = new HashMap<>();
					beanVal.put(bean[1], (bean[2].equals("[]") ? new ArrayList<>() : ""));
					notification.put(bean[0], beanVal);
				}
			}
		}

		responseMap.put("responseMap", notification);
		return responseMap;
	}

	public static Map<String, Object> errorResponseWithValueJson(String message) {
		Map<String, Object> notification = new HashMap<>();
		Map<String, Object> responseMap = new HashMap<>();
		Map<String, Object> valJsn = new HashMap<>();

		if (message == null)
			message = "NULL pointer error!";

		notification.put("notification", DataComponent.setNotification("error", message));
		valJsn.put("ValueJson", notification);
		responseMap.put("responseMap", valJsn);
		return responseMap;
	}

	public static Map<String, Object> validationResponse(String message, String beanClass, Map<String, Object> data) {
		Map<String, Object> classDetails = new HashMap<>();
		Map<String, Object> responseMap = new HashMap<>();
		classDetails.put("notification", DataComponent.setNotification("error", message));

		if (data != null)
			classDetails.put(beanClass, data);

		responseMap.put("responseMap", classDetails);
		return responseMap;
	}

	public static Map<String, Object> validationResponse(String message, Map<String, Object> data) {
		Map<String, Object> classDetails = new HashMap<>();
		Map<String, Object> responseMap = new HashMap<>();
		classDetails.put("notification", DataComponent.setNotification("error", message));
		if (data != null)
			classDetails.putAll(data);
		responseMap.put("responseMap", classDetails);
		return responseMap;
	}

	public static Map<String, Object> positiveResponse(String message) {
		Map<String, Object> notification = new HashMap<>();
		Map<String, Object> responseMap = new HashMap<>();

		if (message == null)
			message = "Success!";

		notification.put("notification", DataComponent.setNotification("success", message));
		responseMap.put("responseMap", notification);
		return responseMap;
	}

	public static <T> Map<String, Object> response(T t) {
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("responseMap", t);
		return responseMap;
	}

	public static <T> Map<String, Object> response(String beanClass, T t) {
		Map<String, Object> beanClassMap = new HashMap<>();
		beanClassMap.put(beanClass, t);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("responseMap", beanClassMap);

		return responseMap;
	}

	public static <T> Map<String, Object> responseResult(String rootName, String beanClass, T t) {
		Map<String, Object> beanClassMap = new HashMap<>();
		beanClassMap.put(beanClass, t);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put(rootName, beanClassMap);

		return responseMap;
	}

	public static <T> Map<String, Object> response(String beanClass, String beanId, T t) {

		Map<String, Object> beanIdMap = new HashMap<>();
		beanIdMap.put(beanId, t);

		Map<String, Object> beanClassMap = new HashMap<>();
		beanClassMap.put(beanClass, beanIdMap);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("responseMap", beanClassMap);

		return responseMap;
	}

	public static <T> Map<String, Object> response(String beanClass, String beanId, List<String> displayFields, T t) {

		Map<String, Object> beanIdMap = new HashMap<>();
		beanIdMap.put(beanId, t);
		beanIdMap.put("displayfields", displayFields);

		Map<String, Object> beanClassMap = new HashMap<>();
		beanClassMap.put(beanClass, beanIdMap);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("responseMap", beanClassMap);

		return responseMap;
	}

	public static boolean checkField(Map<String, Object> filter, String fieldName, String valueCheck) {
		return filter == null || !filter.containsKey(fieldName) || filter.get(fieldName) == null
				|| filter.get(fieldName).toString().equals(valueCheck);
	}

	public static String setData(JSONObject data, String fieldName) {
		if (data != null && data.has(fieldName) && data.get(fieldName) != null && !data.get(fieldName).toString().equalsIgnoreCase("na")) {
			return data.get(fieldName).toString().trim();
		}
		return "";
	}

	public static String setMapData(Map<String, Object> data, String fieldName) {
		if (data != null && data.containsKey(fieldName) && data.get(fieldName) != null) {
			return data.get(fieldName).toString().trim();
		}
		return "";
	}

	public static String setDataCond(Map<String, Object> data, String fieldName, String yes, String no) {
		if (data != null && data.containsKey(fieldName) && data.get(fieldName) != null
				&& !data.get(fieldName).toString().equalsIgnoreCase("")) {
			return yes;
		}
		return no;
	}

	/*
	 * private static void recursive(JSONObject setData, JSONObject data, String[]
	 * search, List<RecursiveDynamic> fields, int level, boolean IsArrayLoop) throws
	 * Exception { Iterator<String> keys = data.keySet().iterator();
	 * 
	 * while (keys.hasNext()) { String key = keys.next().toString(); int rootIndex =
	 * Arrays.asList(search).indexOf(key);
	 * 
	 * if (rootIndex > -1) { List<RecursiveDynamic> f = fields.stream().filter(o ->
	 * o.parentName.equals(search[rootIndex])) .collect(Collectors.toList());
	 * 
	 * // fields.stream().sorted((p1, p2)->p1.dataType.compareTo(p2.dataType));
	 * 
	 * if (level == 0 && f.get(0).dataType == "simple")
	 * setData.put(search[rootIndex], data.get(key)); else if (f.get(0).dataType ==
	 * "obj" && data.get(key) instanceof JSONObject) { String[] keyValues =
	 * f.get(0).keyPull;
	 * 
	 * for (int keyIndex = 0; keyIndex < keyValues.length; keyIndex++) { if
	 * (!IsArrayLoop) setData.put(keyValues[keyIndex],
	 * data.getJSONObject(key).get(keyValues[keyIndex])); else if
	 * (f.get(0).arrayNeedType == 0) setData.put(key,
	 * data.getJSONObject(key).get(keyValues[keyIndex])); else { if
	 * (setData.has(key)) setData.put(key, setData.get(key).toString() + "," +
	 * data.getJSONObject(key).get(keyValues[keyIndex])); else { setData.put(key,
	 * data.getJSONObject(key).get(keyValues[keyIndex])); } }
	 * 
	 * } } else if (f.get(0).arrayNeedType > 0 && f.get(0).dataType == "array" &&
	 * data.get(key) instanceof JSONObject) { String[] keyValues = f.get(0).keyPull;
	 * 
	 * for (int keyIndex = 0; keyIndex < keyValues.length; keyIndex++) { if
	 * (setData.has(keyValues[keyIndex])) setData.put(keyValues[keyIndex],
	 * setData.get(keyValues[keyIndex]).toString() + "," +
	 * data.getJSONObject(key).get(keyValues[keyIndex])); else {
	 * setData.put(keyValues[keyIndex],
	 * data.getJSONObject(key).get(keyValues[keyIndex])); } } } else if
	 * (f.get(0).dataType == "array" && data.get(key) instanceof JSONArray) {
	 * String[] keyValues = f.get(0).keyPull; JSONArray arrayValues =
	 * data.getJSONArray(key); JSONArray setValue = new JSONArray();
	 * 
	 * if (!IsArrayLoop) { for (int rowIndex = 0; rowIndex < arrayValues.length();
	 * rowIndex++) { JSONObject value = new JSONObject(); for (int keyIndex = 0;
	 * keyIndex < keyValues.length; keyIndex++) { value.put(keyValues[keyIndex],
	 * arrayValues.getJSONObject(rowIndex).get(keyValues[keyIndex])); }
	 * 
	 * if (f.get(0).arrayNameList != null && f.get(0).arrayNameList.size() > 0) {
	 * recursive(value, arrayValues.getJSONObject(rowIndex), search,
	 * f.get(0).arrayNameList, (level + 1), true); }
	 * 
	 * setValue.put(value); } } else { for (int rowIndex = 0; rowIndex <
	 * arrayValues.length(); rowIndex++) { for (int keyIndex = 0; keyIndex <
	 * keyValues.length; keyIndex++) {
	 * 
	 * if (setData.has(keyValues[keyIndex])) setData.put(keyValues[keyIndex],
	 * setData.get(keyValues[keyIndex]).toString() + "," + (arrayValues
	 * .getJSONObject(rowIndex).get(keyValues[keyIndex]).toString())); else {
	 * setData.put(keyValues[keyIndex],
	 * (arrayValues.getJSONObject(rowIndex).get(keyValues[keyIndex]).toString())); }
	 * 
	 * } } }
	 * 
	 * if (!IsArrayLoop) setData.put(key, setValue); } }
	 * 
	 * if (IsArrayLoop && data.get(key) instanceof JSONArray) { JSONArray arrayJSON
	 * = data.getJSONArray(key); for (int rowIndex = 0; rowIndex <
	 * arrayJSON.length() && arrayJSON.get(rowIndex) instanceof JSONObject;
	 * rowIndex++) { recursive(setData, arrayJSON.getJSONObject(rowIndex), search,
	 * fields, (level + 1), IsArrayLoop); } }
	 * 
	 * if (data.get(key) instanceof JSONObject) { recursive(setData,
	 * data.getJSONObject(key), search, fields, (level + 1), IsArrayLoop); } } }
	 */

	public static String SetEpochToDate(String value) throws Exception {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			return sdf.format(new java.util.Date(Long.parseLong(value) * 1000));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String setDateToEpochOld(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

		if (date.contains("-")) {
			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			date = sdf.format(df.parse(date));
		}

		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return Long.toString((sdf.parse(date).getTime() / 1000));
	}

	public static String setDateToEpoch(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
		if (!date.substring(0, 4).contains("-")) {
			df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		}
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		date = sdf.format(df.parse(date));

		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return Long.toString((sdf.parse(date).getTime() / 1000));
	}

}

