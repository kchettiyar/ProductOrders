package com.kchetty.ProductOrders.Services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kchetty.ProductOrders.Beans.CustomerRequest;


@Service
public class CustomerService {

	@Value("${updateCustomer}")
	private String updateCustomer;

	@Value("${businessCustomerDetails}")
	private String businessCustomerDetails;

	@Value("${customerDetailsById}")
	private String customerdetails;

	@Value("${customerDetailsByEmail}")
	private String customerDetailsByEmail;

	@Value("${resetPassword}")
	private String resetPassword;

	@Value("${serviceUrl}")
	private String serviceUrl;

	public Map<String, Object> saveBusinessCustomerThrough(Map<String, Object> customerRequest) throws Exception {
		if (DataComponent.checkField(customerRequest, "customer", "{}"))
			return DataComponent.errorResponse("Empty data!");

		String keycloakUserId = DataComponent.setMapData(customerRequest, "keycloakUserId");

		return saveBusinessCustomer(
				new ObjectMapper().convertValue(customerRequest.get("customer"), CustomerRequest.class),
				keycloakUserId);
	}

	public Map<String, Object> saveBusinessCustomer(CustomerRequest customerRequest, String keycloakUserId)
			throws Exception {
		if (customerRequest == null)
			return DataComponent.errorResponse("Empty data!");

		String message = "", Uri = "";
		HttpMethod _httpMethod;

		if (!keycloakUserId.equalsIgnoreCase(""))
			customerRequest.setKeycloakUserId(keycloakUserId);

		Map<String, Object> body = new ObjectMapper().convertValue(customerRequest, Map.class);
		body.remove("customerId");
		body.remove("dtagId");
		body.remove("customerAccountId");
		
		if(body.containsKey("gender") && !body.get("gender").toString().equalsIgnoreCase("na")) {
			body.replace("gender", genderConvert(false,body.get("gender").toString()));
		}

		if (customerRequest.getCustomerId() != null && customerRequest.getCustomerId() != "") {
			Uri = updateCustomer + customerRequest.getCustomerId();
			_httpMethod = HttpMethod.PUT;

		} else {
			body.remove("keycloakUserId");
			// body.put("password", "Test@12345");
			Uri = businessCustomerDetails;
			_httpMethod = HttpMethod.POST;
		}

		System.out.println(new ObjectMapper().writeValueAsString(body));

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpEntity<Map<String, Object>> request = DataComponent.setRequest(MediaType.APPLICATION_JSON, body);
		ResponseEntity<?> response = restTemplate.exchange(Uri, _httpMethod, request, String.class);

		if (response.getStatusCode().equals(HttpStatus.CREATED) || response.getStatusCode().equals(HttpStatus.ACCEPTED)
				|| response.getStatusCode().equals(HttpStatus.OK)) {
			if (response.getBody() != null && !response.getBody().toString().equals("")) {
				return DataComponent.positiveResponse(response.getBody().toString());
			} else {
				return DataComponent.positiveResponse("Customer created successfully.");
			}

		} else {

			if (response.getBody() != null && !response.getBody().toString().equals("")) {
				return DataComponent.errorResponse(response.getBody().toString());
			} else {
				return DataComponent.errorResponse("Something went wrong.");
			}

		}
	}

	public Map<String, Object> resetPassword(Map<String, Object> request) throws Exception {

		if (DataComponent.checkField(request, "emailId", ""))
			return DataComponent.errorResponse("Invalid emailId");

		if (DataComponent.checkField(request, "userId", ""))
			return DataComponent.errorResponse("Invalid UserId");

		HttpEntity<?> requestEnt = HttpRequest.setRequest(MediaType.APPLICATION_JSON, request);

		return DataComponent
				.positiveResponse(DataComponent.httpCallNew(resetPassword, requestEnt, HttpMethod.POST, String.class));

	}

	public Map<String, Object> getCustomerByCustomerId(Map<String, Object> data) throws Exception {
		if (DataComponent.checkField(data, "customerId", ""))
			return DataComponent.errorResponse("Invalid CustomerId!");

		return getCustomerBy(data, "customerId");
	}

	public Map<String, Object> getCustomerByEmailId(Map<String, Object> data) throws Exception {
		if (DataComponent.checkField(data, "emailId", ""))
			return DataComponent.errorResponse("Invalid emailId!");
		return getCustomerBy(data, "emailId");
	}

	public Map<String, Object> getCustomers() {
		// List<Object> data = DataComponent.httpCallNew(serviceUrl, null,
		// HttpMethod.GET, List.class);

		// new ObjectMapper().readValue(data.toString(), List.class);

		return extractCustomers(
				new JSONArray(DataComponent.httpCallNew(serviceUrl, null, HttpMethod.GET, String.class).toString()));
	}

	private Map<String, Object> extractCustomers(JSONArray customers) {
		List<Map<String, Object>> result = new ArrayList<>();
		for (int i = 0; i < customers.length(); i++) {
			JSONObject c = customers.getJSONObject(i);
			Map<String, Object> data = new HashMap<>();

			if (c.has("customerManagementRequest")) {
				JSONObject cc = c.getJSONObject("customerManagementRequest");
				data.put("fullName", "");
				data.put("fullName1", "");
				data.put("action", "View Contact");
				if (cc.has("contactDetails")) {
					JSONArray contactDetails = cc.getJSONArray("contactDetails");

					if (contactDetails.length() > 0) {
						data.put("fullName", (DataComponent.setData(contactDetails.getJSONObject(0), "firstName") + " "
								+ DataComponent.setData(contactDetails.getJSONObject(0), "lastName")));

						data.put("fullName1", data.get("fullName"));
					}

					data.put("organizationName", "");
					if (cc.getJSONObject("partyDetails").has("organization")) {
						data.put("organizationName", DataComponent.setData(
								cc.getJSONObject("partyDetails").getJSONObject("organization"), "organizationName"));
					}
				}

				if (cc.has("customer")) {
					data.put("customerId", DataComponent.setData(cc.getJSONObject("customer"), "id"));
					data.put("customerType", DataComponent.setData(cc.getJSONObject("customer"), "customerType"));
					data.put("status", DataComponent.setData(cc.getJSONObject("customer"), "status"));

					if (data.get("customerType").toString().equalsIgnoreCase("business")) {
						data.put("fullName", "");
						data.put("fullName1", data.get("organizationName"));
					}

					Map<String, Object> color = new HashMap<>();
					color.put("action", "");

					if (data.get("customerType").toString().equalsIgnoreCase("individual")) {
						data.put("organizationName", "");
						color.put("action", "disp_none");
					}

					data.put("viewColorCodeMap", color);
				}
				
				if (cc.has("customerAccount")) {
					data.put("customerAccountId", DataComponent.setData(cc.getJSONObject("customerAccount"), "customerAccountId"));
				}

				if (cc.has("contactDetails")) {
					JSONObject contactDetail = cc.getJSONArray("contactDetails").getJSONObject(0);
					data.put("emailId",
							contactDetail.getJSONArray("emailAddress").getJSONObject(0).getString("emailAddress"));
				}
			}

			result.add(data);
		}

		List<String> displayTable = new ArrayList<>();
		displayTable.add("customerType");
		displayTable.add("customerId");
		displayTable.add("customerAccountId");
		displayTable.add("fullName");
		displayTable.add("organizationName");
		displayTable.add("status");
		displayTable.add("action");

		Map<String, Object> returnData = new HashMap<>();
		returnData.put("dataList", result);
		returnData.put("displayfields", displayTable);
		returnData.put("searchText", " ");

		return DataComponent.response("customerDetailsClass", returnData);

	}

	private Map<String, Object> getCustomerBy(Map<String, Object> data, String key) {
		String uri = "", input = "";
		HttpMethod _httpMethod;

		if (key.equalsIgnoreCase("emailId")) {

			input = data.get("emailId").toString();
			uri = customerDetailsByEmail;
			_httpMethod = HttpMethod.POST;
		} else {
			input = data.get("customerId").toString();
			uri = customerdetails + "/" + data.get("customerId").toString();
			_httpMethod = HttpMethod.GET;
		}

		HttpEntity<String> request = DataComponent.setRequest(MediaType.APPLICATION_JSON, input);
		Map<String, Object> respose = DataComponent.httpCallNew(uri, request, _httpMethod, Map.class);

		Map<String, Object> productDetails = new HashMap<>();
		productDetails.put("productType", DataComponent.setMapData(data, "productType"));
		productDetails.put("offerCategory", DataComponent.setMapData(data, "offerCategory"));
		productDetails.put("offerCategoryLabel", DataComponent.setMapData(data, "offerCategoryLabel"));

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("stepWizardBeanClass", setCustomerDataNew(new JSONObject(respose)));
		responseMap.put("productDetails", productDetails);

		// return DataComponent.response("stepWizardBeanClass", );
		return DataComponent.response(responseMap);
	}

	private Map<String, Object> setCustomerDataOld(JSONObject data) {
		Map<String, Object> returnData = new HashMap<>();
		CustomerRequest customerRequest = new CustomerRequest();

		customerRequest.setDtagId(DataComponent.setData(data, "dtagId"));
		customerRequest.setCustomerId(DataComponent.setData(data, "dtagId"));
		customerRequest.setCrmid(DataComponent.setData(data, "crmid"));
		customerRequest.setOrderLimit(DataComponent.setData(data, "orderLimit"));
		customerRequest.setIban(DataComponent.setData(data, "iban"));
		customerRequest.setBic(DataComponent.setData(data, "bic"));
		customerRequest.setCustomerType(DataComponent
				.setData(data.getJSONObject("customermanagement").getJSONObject("customer"), "customerType"));
		customerRequest.setStatus(
				DataComponent.setData(data.getJSONObject("customermanagement").getJSONObject("customer"), "status"));
		customerRequest.setTitle(DataComponent.setData(data, "title"));
		customerRequest.setIdentificationInfo(DataComponent.setData(data, "identificationInfo"));
		customerRequest.setLegalForm(DataComponent.setData(data, "legalForm"));
		customerRequest.setVatId(DataComponent.setData(data, "vatId"));
		customerRequest.setTradeRegistrationNo(DataComponent.setData(data, "tradeRegistrationNo"));
		customerRequest.setLocationRegistration(DataComponent.setData(data, "locationRegistration"));
		customerRequest.setDateOfRegistration(DataComponent.setData(data, "dateOfRegistration"));
		customerRequest.setCourtOfRegistration(DataComponent.setData(data, "courtOfRegistration"));
		customerRequest.setAccountHolder(DataComponent.setData(data, "accountHolder"));
		customerRequest.setOrganizationName(DataComponent.setData(
				data.getJSONObject("customermanagement").getJSONObject("partyDetails").getJSONObject("organization"),
				"organizationName"));

		customerRequest.setPaymentMethod(DataComponent.setData(
				data.getJSONObject("customermanagement").getJSONObject("paymentPlan").getJSONObject("paymentMethod"),
				"paymentMethodName"));

		customerRequest.setConditionOfPayment(DataComponent.setData(
				data.getJSONObject("customermanagement").getJSONObject("paymentPlan").getJSONObject("paymentMethod"),
				"description"));

		customerRequest.setBank(DataComponent.setData(
				data.getJSONObject("customermanagement").getJSONObject("paymentPlan").getJSONObject("paymentDetail"),
				"bankName"));

		customerRequest.setBankAc(DataComponent.setData(
				data.getJSONObject("customermanagement").getJSONObject("paymentPlan").getJSONObject("paymentDetail"),
				"bankAccountNumber"));

		JSONObject individual = data.getJSONObject("customermanagement").getJSONObject("partyDetails")
				.getJSONObject("individual");

		customerRequest.setFamilyName(DataComponent.setData(individual, "lastName"));
		customerRequest.setGivenName(DataComponent.setData(individual, "firstName"));

		customerRequest.setBirthDate("");
		if (individual.has("birthdate") && individual.get("birthdate") != null
				&& individual.get("birthdate").toString() != "" && individual.get("birthdate").toString() != "NA") {
			customerRequest.setBirthDate(individual.get("birthdate").toString());
		}

		customerRequest.setNationality(DataComponent.setData(individual, "nationality"));

		customerRequest.setTelephoneNo("");
		customerRequest.setEmailId("");

		if (data.getJSONObject("customermanagement").has("contactDetails")) {
			JSONArray contact = data.getJSONObject("customermanagement").getJSONArray("contactDetails");
			if (contact.length() > 0) {
				customerRequest.setTelephoneNo(DataComponent
						.setData(contact.getJSONObject(0).getJSONArray("contactNumber").getJSONObject(0), "number"));

				customerRequest.setEmailId(DataComponent.setData(
						contact.getJSONObject(0).getJSONArray("contactNumber").getJSONObject(0), "emailAddress"));

				if (contact.getJSONObject(0).has("postalAddress")) {
					JSONObject address = contact.getJSONObject(0).getJSONArray("postalAddress").getJSONObject(0);

					customerRequest.setPostalAddress(DataComponent.setData(address, "addressType"));
					customerRequest.setCity(DataComponent.setData(address, "city"));
					customerRequest.setPostalCode(DataComponent.setData(address, "postCode"));
					customerRequest.setCountry(DataComponent.setData(address, "country"));
				}
			}
			returnData = new ObjectMapper().convertValue(customerRequest, Map.class);
			returnData.put("fullName",
					(returnData.get("givenName").toString() + " " + returnData.get("familyName").toString()));

			returnData.put("gender", individual.get("gender").toString());
			returnData.put("genderText", genderConvert(false, individual.get("gender").toString().toLowerCase()));

			if (returnData.containsKey("birthDate"))
				returnData.put("birthDateText", SetEpochToDate(returnData.get("birthDate").toString()));

			Map<String, Object> color = new HashMap<>();
			color.put("username", "disabled");
			color.put("password", "disabled");
			color.put("status", "");

			if (!returnData.get("status").toString().toLowerCase().equals("draft"))
				color.put("status", "disabled");

			color.put("password", "disabled");
			returnData.put("viewColorCodeMap", color);
		}

		return returnData;
	}

	private Map<String, Object> setCustomerData(JSONObject data) {
		Map<String, Object> returnData = new HashMap<>();
		CustomerRequest customerRequest = new CustomerRequest();

		customerRequest.setDtagId(DataComponent.setData(data, "customerId"));
		customerRequest.setCustomerId(DataComponent.setData(data, "customerId"));
		customerRequest.setCrmid(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "crmid"));
		customerRequest.setOrderLimit(DataComponent.setData(data, "orderLimit"));
		customerRequest.setIban(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentDetail"), "iban"));
		customerRequest.setBic(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentDetail"), "bic"));
		customerRequest.setCustomerType(DataComponent
				.setData(data.getJSONObject("customerManagementRequest").getJSONObject("customer"), "customerType"));
		customerRequest.setStatus(DataComponent
				.setData(data.getJSONObject("customerManagementRequest").getJSONObject("customer"), "status"));
		customerRequest.setTitle(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("individual"), "title"));
		customerRequest.setIdentificationInfo(DataComponent.setData(
				data.getJSONObject("customerManagementRequest").getJSONObject("partyDetails")
						.getJSONObject("organization").getJSONObject("organizationIdentification"),
				"identificationInfo"));
		customerRequest.setLegalForm(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "legalForm"));
		customerRequest.setVatId(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "vatId"));
		customerRequest.setTradeRegistrationNo(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "tradeRegistrationNo"));
		customerRequest.setLocationRegistration(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "locationRegistration"));
		customerRequest.setDateOfRegistration(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "dateOfRegistration"));
		customerRequest.setCourtOfRegistration(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "courtOfRegistration"));
		customerRequest.setAccountHolder(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentDetail"), "accountHolder"));
		customerRequest.setOrganizationName(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "organizationName"));

		customerRequest.setPaymentMethod(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentMethod"), "paymentMethodName"));

		customerRequest.setConditionOfPayment(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentMethod"), "description"));

		customerRequest.setBank(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentDetail"), "bankName"));

		customerRequest.setBankAc(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentDetail"), "bankAccountNumber"));

		JSONObject individual = data.getJSONObject("customerManagementRequest").getJSONObject("partyDetails")
				.getJSONObject("individual");

		customerRequest.setFamilyName(DataComponent.setData(individual, "lastName"));
		customerRequest.setGivenName(DataComponent.setData(individual, "firstName"));

		customerRequest.setBirthDate("");
		if (individual.has("birthdate") && individual.get("birthdate") != null
				&& individual.get("birthdate").toString() != "" && individual.get("birthdate").toString() != "NA") {
			customerRequest.setBirthDate(individual.get("birthdate").toString());
		}

		customerRequest.setNationality(DataComponent.setData(individual, "nationality"));

		customerRequest.setTelephoneNo("");
		customerRequest.setEmailId("");

		if (data.getJSONObject("customerManagementRequest").has("contactDetails")) {
			JSONArray contact = data.getJSONObject("customerManagementRequest").getJSONArray("contactDetails");
			if (contact.length() > 0) {
				customerRequest.setTelephoneNo(DataComponent
						.setData(contact.getJSONObject(0).getJSONArray("contactNumber").getJSONObject(0), "number"));

				customerRequest.setEmailId(DataComponent.setData(
						contact.getJSONObject(0).getJSONArray("emailAddress").getJSONObject(0), "emailAddress"));

				if (contact.getJSONObject(0).has("postalAddress")) {
					JSONObject address = contact.getJSONObject(0).getJSONArray("postalAddress").getJSONObject(0);

					customerRequest.setPostalAddress(DataComponent.setData(address, "addressType"));
					customerRequest.setCity(DataComponent.setData(address, "city"));
					customerRequest.setPostalCode(DataComponent.setData(address, "postCode"));
					customerRequest.setCountry(DataComponent.setData(address, "country"));
				}
			}
			returnData = new ObjectMapper().convertValue(customerRequest, Map.class);
			returnData.put("fullName",
					(returnData.get("givenName").toString() + " " + returnData.get("familyName").toString()));

			returnData.put("gender", individual.get("gender").toString());
			returnData.put("genderText", genderConvert(false, individual.get("gender").toString().toLowerCase()));

			if (returnData.containsKey("birthDate"))
				returnData.put("birthDateText", SetEpochToDate(returnData.get("birthDate").toString()));

			Map<String, Object> color = new HashMap<>();
			color.put("username", "disabled");
			color.put("password", "disabled");
			color.put("status", "");

			if (!returnData.get("status").toString().toLowerCase().equals("draft"))
				color.put("status", "disabled");

			color.put("password", "disabled");
			returnData.put("viewColorCodeMap", color);
		}

		return returnData;
	}

	private Map<String, Object> setCustomerDataNew(JSONObject data) {
		Map<String, Object> returnData = new HashMap<>();
		CustomerRequest customerRequest = new CustomerRequest();

		customerRequest.setDtagId(DataComponent.setData(data, "customerId"));
		customerRequest.setCustomerId(DataComponent.setData(data, "customerId"));
		
		customerRequest.setCustomerAccountId(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("customerAccount"),"customerAccountId"));
		
		customerRequest.setCrmid(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "crmid"));
		customerRequest.setOrderLimit(DataComponent.setData(data, "orderLimit"));
		customerRequest.setIban(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentDetail"), "iban"));
		customerRequest.setBic(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentDetail"), "bic"));
		customerRequest.setCustomerType(DataComponent
				.setData(data.getJSONObject("customerManagementRequest").getJSONObject("customer"), "customerType"));
		customerRequest.setStatus(DataComponent
				.setData(data.getJSONObject("customerManagementRequest").getJSONObject("customer"), "status"));
		customerRequest.setIdentificationInfo(DataComponent.setData(
				data.getJSONObject("customerManagementRequest").getJSONObject("partyDetails")
						.getJSONObject("organization").getJSONObject("organizationIdentification"),
				"identificationInfo"));
		customerRequest.setLegalForm(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "legalForm"));
		customerRequest.setVatId(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "vatId"));
		customerRequest.setTradeRegistrationNo(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "tradeRegistrationNo"));
		customerRequest.setLocationRegistration(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "locationRegistration"));
		customerRequest.setDateOfRegistration(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "dateOfRegistration"));
		customerRequest.setCourtOfRegistration(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "courtOfRegistration"));
		customerRequest.setAccountHolder(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentDetail"), "accountHolder"));
		customerRequest.setOrganizationName(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("partyDetails").getJSONObject("organization"), "organizationName"));

		customerRequest.setPaymentMethod(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentMethod"), "paymentMethodName"));

		customerRequest.setConditionOfPayment(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentMethod"), "description"));

		customerRequest.setBank(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentDetail"), "bankName"));

		customerRequest.setBankAc(DataComponent.setData(data.getJSONObject("customerManagementRequest")
				.getJSONObject("paymentPlan").getJSONObject("paymentDetail"), "bankAccountNumber"));

		customerRequest.setTelephoneNo("");
		customerRequest.setEmailId("");

		if (data.getJSONObject("customerManagementRequest").has("contactDetails")) {

			JSONArray contact = data.getJSONObject("customerManagementRequest").getJSONArray("contactDetails");
			if (contact.length() > 0) {

				JSONObject contactDetails = contact.getJSONObject(0);

				customerRequest.setTitle(DataComponent.setData(contactDetails, "title"));
				customerRequest.setFamilyName(DataComponent.setData(contactDetails, "lastName"));
				customerRequest.setGivenName(DataComponent.setData(contactDetails, "firstName"));
				customerRequest.setBirthDate(DataComponent.setData(contactDetails, "birthdate"));
				

				customerRequest.setGender(genderTextConvert(DataComponent.setData(contactDetails, "gender")));

				customerRequest.setNationality(DataComponent.setData(contactDetails, "nationality"));

				customerRequest.setTelephoneNo(
						DataComponent.setData(contactDetails.getJSONArray("contactNumber").getJSONObject(0), "number"));

				customerRequest.setEmailId(DataComponent
						.setData(contactDetails.getJSONArray("emailAddress").getJSONObject(0), "emailAddress"));

				if (contactDetails.has("postalAddress")) {
					JSONObject address = contactDetails.getJSONArray("postalAddress").getJSONObject(0);

					customerRequest.setPostalAddress(DataComponent.setData(address, "addressType"));
					customerRequest.setCity(DataComponent.setData(address, "city"));
					customerRequest.setPostalCode(DataComponent.setData(address, "postCode"));
					customerRequest.setCountry(DataComponent.setData(address, "country"));
				}
			}
			returnData = new ObjectMapper().convertValue(customerRequest, Map.class);
			returnData.put("fullName",
					(returnData.get("givenName").toString() + " " + returnData.get("familyName").toString()));

			returnData.put("genderText", genderConvert(false, returnData.get("gender").toString().toLowerCase()));

			returnData.put("birthDateText", "");
			if (returnData.containsKey("birthDate") && !returnData.get("birthDate").toString().equals(""))
				returnData.put("birthDateText", SetEpochToDate(returnData.get("birthDate").toString()));

			Map<String, Object> color = new HashMap<>();
			color.put("username", "disabled");
			color.put("password", "disabled");
			color.put("status", "");

			if (!returnData.get("status").toString().toLowerCase().equals("draft"))
				color.put("status", "disabled");

			color.put("password", "disabled");
			returnData.put("viewColorCodeMap", color);
		}

		return returnData;
	}

	private String genderConvert(boolean isToText, String value) {
		return value.equals("1") ? "Male" : value.equals("2") ? "Female" : value.equals("3") ? "Diverse" : "";
	}
	
	private String genderTextConvert(String value) {
		return value.equals("Male") ? "1" : value.equals("Female") ? "2" : value.equals("Diverse") ? "3" : "";
	}

	private String SetEpochToDate(String value) {
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
			// sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			sdf.setTimeZone(TimeZone.getTimeZone("CET"));
			return sdf.format(new java.util.Date(Long.parseLong(value) * 1000));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}
