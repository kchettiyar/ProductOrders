package com.kchetty.ProductOrders.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.kchetty.ProductOrders.Query.MongoQuery;
import com.kchetty.ProductOrders.Utility.ExecuteMongoQuery;

@Service
public class OrderManagmentService {

	@Value("${mongoHost}")
	private String mongoHost;

	@Value("${mongoPort}")
	private int mongoPort;

	@Value("${dbName}")
	private String dbName;

	@Value("${createProductOrderNew}")
	private String createProductOrderNew;
	
	@Value("${orderManagmentCsrDetails}")
	private String orderManagmentCsrDetails;

	@Value("${customerDetailsByEmail}")
	private String customerDetailsByEmail;

	@Value("${orderManagmentByCustId}")
	private String orderManagmentByCustId;

	ExecuteMongoQuery shell = new ExecuteMongoQuery();
	
	@Autowired
	// RestCall restCall;
	CustomerService customerService;

	@Autowired
	MongoOperations mongo;
	
	public Map<String, Object> serchOrderByCriteria(String filterKey, String filterValue) throws Exception {
		String uri = "";

		if (!filterKey.equals("") && !filterValue.equals(""))
			uri = orderManagmentCsrDetails + "?" + "filterKey=" + filterKey + "&filterValue=" + filterValue;
		else
			uri = orderManagmentCsrDetails;

		HttpEntity<String> request = DataComponent.setRequest(MediaType.APPLICATION_JSON, "");
		List<Object> orders = DataComponent.httpCallNew(uri, request, HttpMethod.GET, List.class);

		return DataComponent.response("orderManagement", "dataList", getOrderDetails(orders));
	}

	public String returnFilterKey(String filterKey) throws Exception {
		if (filterKey.equals("orderId"))
			return "externalId";
		else if (filterKey.equals("offerName"))
			return "offerName";

		return filterKey;
	}

	public Map<String, Object> getordersByEmailNew(Map<String, Object> filter) throws Exception {
		if (DataComponent.checkField(filter, "emailId", "")) {
			return DataComponent.errorResponse("Invalid emailId");
		}

		HttpEntity<String> request = DataComponent.setRequest(MediaType.APPLICATION_JSON,
				filter.get("emailId").toString());

		Map<String, Object> response = DataComponent.httpCallNew(customerDetailsByEmail, request, HttpMethod.POST,
				Map.class);

		if (!response.containsKey("dtagId")) {
			return DataComponent.errorResponse("Invalid emailId");
		}

		String url = orderManagmentByCustId + "?customerId=" + response.get("dtagId").toString();

		if (filter.containsKey("filterKey") && filter.get("filterKey") != null
				&& !filter.get("filterKey").toString().equals("") && filter.containsKey("filterValue")) {
			url += "&filterKey=" + returnFilterKey(filter.get("filterKey").toString()) + "&filterValue="
					+ filter.get("filterValue").toString();
		}

		List<Object> data = DataComponent.httpCallNew(url, null, HttpMethod.GET, List.class);
		return DataComponent.response("orderManagement", "dataList", getOrderDetails(data));
	}

	public Map<String, Object> getOrdersByEmailId(Map<String, Object> filter) throws Exception {

		if (DataComponent.checkField(filter, "emailId", ""))
			return DataComponent.errorResponse("The emailId is incorrect");

		String customerId = new JSONObject(customerService.getCustomerByEmailId(filter)).getJSONObject("responseMap")
				.getJSONObject("stepWizardBeanClass").getString("customerId");

		return serchOrderByCriteria("customerId", customerId);
	}

	public Map<String, Object> getOrdersByFilter(Map<String, Object> filter) throws Exception {
		if (filter.containsKey("filterKey") && filter.containsKey("filterValue") && filter.get("filterKey") != null
				&& filter.get("filterValue") != null && !filter.get("filterKey").toString().equals("")
				&& !filter.get("filterValue").toString().equals("")
				&& !filter.get("filterKey").toString().equals("null")
				&& !filter.get("filterValue").toString().equals("null")) {

			return serchOrderByCriteria(returnFilterKey(filter.get("filterKey").toString()),
					filter.get("filterValue").toString());

		} else if (filter.containsKey("filterKey") && filter.containsKey("filterCalenderValue")
				&& filter.get("filterKey") != null && filter.get("filterCalenderValue") != null
				&& !filter.get("filterKey").toString().equals("")
				&& !filter.get("filterCalenderValue").toString().equals("")
				&& !filter.get("filterKey").toString().equals("null")
				&& !filter.get("filterCalenderValue").toString().equals("null")) {

			return serchOrderByCriteria(returnFilterKey(filter.get("filterKey").toString()),
					filter.get("filterCalenderValue").toString());
		} else {
			return serchOrderByCriteria("", "");
		}

	}

	public List<Object> getOrderDetails(List<Object> orders) throws Exception {

		List<Object> mapLst = new ArrayList<Object>();
		if (orders != null) {
			for (int i = 0; i < orders.size(); i++) {
				Map<String, Object> returnMap = new HashMap<>();
				Map<String, Object> orderMap = new HashMap<String, Object>();
				orderMap = (Map<String, Object>) orders.get(i);

				returnMap.put("orderId", DataComponent.setMapData(orderMap, "externalId"));
				returnMap.put("orderDate", "");
				returnMap.put("orderCompletionDate", "");
				returnMap.put("orderDateEpoch", DataComponent.setMapData(orderMap, "orderDate"));
				returnMap.put("orderCompletionDateEpoch", DataComponent.setMapData(orderMap, "completionDate"));

				if (!returnMap.get("orderDateEpoch").toString().equalsIgnoreCase("")
						&& !returnMap.get("orderDateEpoch").toString().equalsIgnoreCase("string"))
					returnMap.put("orderDate",
							DataComponent.setEpochToDate(returnMap.get("orderDateEpoch").toString()));

				if (!returnMap.get("orderCompletionDateEpoch").toString().equalsIgnoreCase("")
						&& !returnMap.get("orderCompletionDateEpoch").toString().equalsIgnoreCase("string"))
					returnMap.put("orderCompletionDate",
							DataComponent.setEpochToDate(returnMap.get("orderCompletionDateEpoch").toString()));

				if (orderMap.containsKey("relatedParty")) {
					Map<String, Object> relatedParty = (Map<String, Object>) ((List) orderMap.get("relatedParty"))
							.get(0);
					returnMap.put("customerId", DataComponent.setMapData(relatedParty, "id"));
					returnMap.put("customerName", DataComponent.setMapData(relatedParty, "name"));
				}

				if (orderMap.containsKey("orderItem")) {
					List<Map<String, Object>> orderItem = (List<Map<String, Object>>) orderMap.get("orderItem");

					for (Map<String, Object> o : orderItem) {
						if (o.containsKey("productOffering") && !returnMap.containsKey("offerId")) {
							Map<String, Object> productOffering = (Map<String, Object>) o.get("productOffering");
							returnMap.put("offerId", DataComponent.setMapData(productOffering, "id"));
							returnMap.put("offerName", DataComponent.setMapData(productOffering, "name"));
						}
						returnMap.put("productCategory", "");

						if (o.containsKey("orderItem")) {
							List<Object> orderItemInner = new ArrayList<Object>();
							for (Map<String, Object> inner : (List<Map<String, Object>>) o.get("orderItem")) {
								Map<String, Object> innerOut = new HashMap<>();

								innerOut.put("orderId", DataComponent.setMapData(returnMap, "orderId"));
								innerOut.put("orderDate", DataComponent.setMapData(returnMap, "orderDate"));
								innerOut.put("customerId", DataComponent.setMapData(returnMap, "customerId"));
								innerOut.put("customerName", DataComponent.setMapData(returnMap, "customerName"));

								Map<String, Object> productOfferingNest = (Map<String, Object>) inner
										.get("productOffering");

								innerOut.put("offerId", DataComponent.setMapData(productOfferingNest, "id"));
								innerOut.put("offerName", DataComponent.setMapData(productOfferingNest, "name"));

								innerOut.put("productCategory", "");

								if (inner.containsKey("product")) {
									Map<String, Object> product = (Map<String, Object>) inner.get("product");
									returnMap.put("productCategory", DataComponent.setMapData(product, "productType"));
									innerOut.put("productCategory", DataComponent.setMapData(product, "productType"));
								}

								orderItemInner.add(innerOut);
							}
							returnMap.put("innerDataList", orderItemInner);
						}
					}
				}

				returnMap.put("status", DataComponent.setMapData(orderMap, "state"));
				mapLst.add(returnMap);
			}
		}
		return mapLst;
	}
	
	public Map<String, Object> placeOrder(Map<String, Object> filter) throws Exception {
		Map<String, Object> responseMap = new HashMap<>();

		if (filter == null || !filter.containsKey("emailId") || filter.get("emailId") == null
				|| filter.get("emailId").toString().equals("")) {
			return DataComponent.errorResponse("Invalid emailId");
		}

		if (filter.containsKey("amlOfferCode")) {
			List<String> list = new ArrayList<>();
			list.add(filter.get("amlOfferCode").toString());
			filter.put("offerCode", list);
		}

		if (!filter.containsKey("offerCode") || filter.get("offerCode") == null
				|| filter.get("offerCode").toString().isEmpty() || filter.get("offerCode").toString().equals("[]")) {
			return DataComponent.errorResponse("Invalid offerCode");
		}

		List<String> list = (List) filter.get("offerCode");

		if (filter.containsKey("selectedAddOnOffer") && filter.get("selectedAddOnOffer") != null
				&& !filter.get("selectedAddOnOffer").toString().equals("[]")) {
			// String selectedAddOnOffer = new
			// JSONArray(filter.get("selectedAddOnOffer").toString()).toString();

			list.addAll((List) filter.get("selectedAddOnOffer"));
		}

		String offerCode = new JSONArray(list.toString()).toString();

		// String offerCode = new
		// JSONArray(filter.get("offerCode").toString()).toString();

		// System.out.println("DB Query Start Time:- " + new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
		String query = MongoQuery.placeOrderNew.replace("{emailId}", filter.get("emailId").toString())
				.replace("{offerCode}", offerCode);
		// System.out.println("DB Query Call:- ");
		Map<String, Object> orderData = shell.getMongoData(mongoHost, mongoPort, dbName, query);
		orderData.put("processInstanceId", "string");
		// System.out.println("DB Query End Time:- " + new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss.SSS").format(new Date()));

		HttpEntity<?> requestPlaceOrder = DataComponent.setRequest(MediaType.APPLICATION_JSON, orderData);
		String ornNumber = "";

		ornNumber = HttpRequest.httpCall(createProductOrderNew, requestPlaceOrder, HttpMethod.POST, String.class,
				ornNumber);

		if (ornNumber != null && !ornNumber.isEmpty()) {
			Query catalogsQuery = new Query();
			Query customerCatalogsQuery = new Query();
			catalogsQuery.addCriteria(Criteria.where("_id").is(filter.get("emailId").toString()));
			customerCatalogsQuery.addCriteria(Criteria.where("emailId").is(filter.get("emailId").toString()));
			mongo.remove(catalogsQuery, "catalogs");
			mongo.remove(customerCatalogsQuery, "customerCatalogs");
			mongo.remove(catalogsQuery, "customerSim");

			Map<String, Object> colcode = new HashMap<>();
			colcode.put("placeOrder", "disabled");

			Map<String, Object> offRef = new HashMap<>();
			offRef.put("offerReferenceNumber", ornNumber);
			offRef.put("viewColorCodeMap", colcode);

			Map<String, Object> response = new HashMap<>();
			response.put("offerReference", offRef);
			response.putAll(DataComponent.setNotification("success", "Product order created successfully."));

			responseMap.put("responseMap", response);
		}

		return responseMap;
	}
}
