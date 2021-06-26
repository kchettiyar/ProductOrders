package com.kchetty.ProductOrders.Beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerRequest {

	public CustomerRequest() {

	}

	// business customer
	private String customerId;
	private String dtagId;
	private String familyName = "NA";
	private String givenName = "NA";
	private String emailId = "NA";
	private String telephoneNo = "NA";
	private String gender = "NA";

	private String crmid = "NA";
	private String orderLimit = "NA";
	private String organizationName = "NA";
	private String bankAc = "NA";
	private String bank = "NA";
	private String title = "NA";
	private String birthDate = "NA";

	private String nationality = "NA";
	private String accountHolder = "NA";

	private String paymentMethod = "NA";
	private String conditionOfPayment = "NA";

	private String iban = "NA";
	private String bic = "NA";
	private String identificationInfo = "NA";
	// private String username = "NA";
	// private String password = "NA";

	private String postalAddress = "NA";
	private String city = "NA";
	private String country = "NA";
	private String postalCode = "NA";
	private String customerType;
	private String status;

	private String identificationType = "NA";
	private String legalForm = "NA";
	private String vatId = "NA";
	private String tradeRegistrationNo = "NA";
	private String locationRegistration = "NA";
	private String dateOfRegistration = "NA";
	private String courtOfRegistration = "NA";
	private String keycloakUserId;
	
	private String customerAccountId;

	private String setNAValue(String value) {
		return value == null ? "NA" : value;
	}

	public String getKeycloakUserId() {
		return keycloakUserId;
	}

	public void setKeycloakUserId(String keycloakUserId) {
		this.keycloakUserId = keycloakUserId;
	}

	public String getLegalForm() {
		return legalForm;
	}

	public void setLegalForm(String legalForm) {
		this.legalForm = setNAValue(legalForm);
	}

	public String getVatId() {
		return vatId;
	}

	public void setVatId(String vatId) {
		this.vatId = setNAValue(vatId);
	}

	public String getTradeRegistrationNo() {
		return tradeRegistrationNo;
	}

	public void setTradeRegistrationNo(String tradeRegistrationNo) {
		this.tradeRegistrationNo = setNAValue(tradeRegistrationNo);
	}

	public String getLocationRegistration() {
		return locationRegistration;
	}

	public void setLocationRegistration(String locationRegistration) {
		this.locationRegistration = setNAValue(locationRegistration);
	}

	public String getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(String dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public String getCourtOfRegistration() {
		return courtOfRegistration;
	}

	public void setCourtOfRegistration(String courtOfRegistration) {
		this.courtOfRegistration = setNAValue(courtOfRegistration);
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = setNAValue(identificationType);
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = setNAValue(customerType);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = setNAValue(status);
	}

	public String getDtagId() {
		return dtagId;
	}

	public void setDtagId(String dtagId) {
		this.dtagId = setNAValue(dtagId);
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = setNAValue(customerId);
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = setNAValue(birthDate);
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = setNAValue(familyName);
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = setNAValue(givenName);
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = setNAValue(emailId);
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = setNAValue(telephoneNo);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = setNAValue(gender);
	}

	/*
	 * public String getCRMID() { return CRMID; }
	 * 
	 * public void setCRMID(String cRMID) { CRMID = cRMID; }
	 */

	public String getOrderLimit() {
		return orderLimit;
	}

	public void setOrderLimit(String orderLimit) {
		this.orderLimit = setNAValue(orderLimit);
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = setNAValue(organizationName);
	}

	public String getBankAc() {
		return bankAc;
	}

	public void setBankAc(String bankAc) {
		this.bankAc = setNAValue(bankAc);
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = setNAValue(bank);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = setNAValue(title);
	}

	/*
	 * public String getDob() { return dob; }
	 */
	/*
	 * public void setDob(String dob) { this.dob = dob; }
	 */

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = setNAValue(nationality);
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = setNAValue(accountHolder);
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = setNAValue(paymentMethod);
	}

	public String getConditionOfPayment() {
		return conditionOfPayment;
	}

	public void setConditionOfPayment(String conditionOfPayment) {
		this.conditionOfPayment = setNAValue(conditionOfPayment);
	}

	/*
	 * public String getIBAN() { return IBAN; }
	 * 
	 * public void setIBAN(String iBAN) { IBAN = iBAN; }
	 * 
	 * public String getBIC() { return BIC; }
	 * 
	 * public void setBIC(String bIC) { BIC = bIC; }
	 */

	public String getIdentificationInfo() {
		return identificationInfo;
	}

	public void setIdentificationInfo(String identificationInfo) {
		this.identificationInfo = setNAValue(identificationInfo);
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = setNAValue(postalAddress);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = setNAValue(city);
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = setNAValue(country);
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = setNAValue(postalCode);
	}

	public String getCrmid() {
		return crmid;
	}

	public void setCrmid(String crmid) {
		this.crmid = setNAValue(crmid);
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = setNAValue(iban);
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = setNAValue(bic);
	}

	public String getCustomerAccountId() {
		return customerAccountId;
	}

	public void setCustomerAccountId(String customerAccountId) {
		this.customerAccountId = setNAValue(customerAccountId);
	}
	
	

	/*
	 * public CustomerRequest(String familyName, String givenName, String emailId,
	 * String telephoneNo, String gender, String cRMID, String orderLimit, String
	 * organizationName, String bankAc, String bank, String title, String dob,
	 * String nationality, String accountHolder, String paymentMethod, String
	 * conditionOfPayment, String iBAN, String bIC, String identificationInfo,
	 * String username, String password, String role, String postalAddress, String
	 * city, String country, String postalCode) { super(); this.familyName =
	 * familyName; this.givenName = givenName; this.emailId = emailId;
	 * this.telephoneNo = telephoneNo; this.gender = gender; CRMID = cRMID;
	 * this.orderLimit = orderLimit; this.organizationName = organizationName;
	 * this.bankAc = bankAc; this.bank = bank; this.title = title; // this.dob =
	 * dob; this.nationality = nationality; this.accountHolder = accountHolder;
	 * this.paymentMethod = paymentMethod; this.conditionOfPayment =
	 * conditionOfPayment; IBAN = iBAN; BIC = bIC; this.identificationInfo =
	 * identificationInfo; this.username = username; this.password = password;
	 * this.role = role; this.postalAddress = postalAddress; this.city = city;
	 * this.country = country; this.postalCode = postalCode; }
	 */

}
