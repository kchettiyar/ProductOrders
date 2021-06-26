package com.kchetty.ProductOrders.Query;

public class MongoQuery {

	public static String offerPrice3 = "db.getCollection('catalogs').aggregate([\r\n"
			+ "{$match: {\"_id\": \"{emailId}\"}}, \r\n" + "{$unwind: \"$Catalogs\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook\"},\r\n" + "{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.prices\"},\r\n"
			+ "{$match: {\"Catalogs.priceBook.offerPrices.products.prices.recurringCharge.priceType\": \"recurringCharge\"}},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\"},\r\n"
			+ "{$project: {\r\n" + "    \"currency\": \"$Catalogs.priceBook.currency\",\r\n"
			+ "    \"baseUrl\": \"$baseUrl\",\r\n" + "    \"defaultImage\": \"$defaultImage\",\r\n"
			+ "    \"offerList\": {\r\n"
			+ "            \"offerName\": \"$Catalogs.priceBook.offerPrices.offerName\",\r\n"
			+ "            \"offerDescription\": { $slice: [ { $split: [ \"$Catalogs.priceBook.offerPrices.offerDescription\", \"|\" ] }, 0, 5 ] } ,\r\n"
			+ "            \"offerPricingStatus\": \"$Catalogs.priceBook.offerPrices.offerPricingStatus\",\r\n"
			+ "            \"offerCode\": \"$Catalogs.priceBook.offerPrices.offerCode\",\r\n"
			+ "            \"productName\": \"$Catalogs.priceBook.offerPrices.products.productName\",\r\n"
			+ "            \"productCode\": \"$Catalogs.priceBook.offerPrices.products.productCode\",\r\n"
			+ "            \"productType\": \"$Catalogs.priceBook.offerPrices.products.productType\",\r\n"
			+ "            \"price\": \"$Catalogs.priceBook.offerPrices.products.prices.recurringCharge.price\"\r\n"
			+ "        },\r\n" + "    \"content_url\": {$cond: {\r\n"
			+ "        if: {$eq: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\", \"content_url\"]}, \r\n"
			+ "        then: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\", \r\n"
			+ "        else: null}}\r\n" + "    }},\r\n" + "{$group: {\r\n"
			+ "     \"_id\": { \"emailId\": \"$_id\", \"currency\": \"$currency\", \"baseUrl\": \"$baseUrl\", \"defaultImage\": \"$defaultImage\", \"offerList\": \"$offerList\" },\r\n"
			+ "     \"content_url\": {$push: \"$content_url\"} \r\n" + "     }},\r\n" + "{$addFields: {\r\n"
			+ "    \"_id.offerList.content_url\": {$cond: {\r\n"
			+ "        if: {$eq: [{$size: {$filter: {input: \"$content_url\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]},\r\n"
			+ "        then: {$concat: [\"$_id.baseUrl\", \"T_Logo_bg.png\"]},\r\n"
			+ "        else:  {$concat: [\"$_id.baseUrl\", \"$_id.offerList.offerCode\", \"/\", {$arrayElemAt: [{$filter: {input: \"$content_url\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}, 0]}]}   \r\n"
			+ "        }}\r\n" + "    }},\r\n" + "{$group: {\r\n"
			+ "     \"_id\": { \"emailId\": \"$_id.emailId\", \"currency\": \"$_id.currency\", \"offerList\": \"$offerList\" },\r\n"
			+ "     \"offer\": {$push: \"$_id.offerList\"}\r\n" + "     }},\r\n" + "{$project: {\r\n"
			+ "    \"_id\": 0,\r\n" + "    \"result\": {\r\n" + "        \"offerDetails\": {\r\n"
			+ "            \"currency\": \"$_id.currency\",\r\n" + "            \"offerList\": \"$offer\"\r\n"
			+ "            }\r\n" + "        }\r\n" + "    }}\r\n" + "])";

	public static String offerPrice4 = "db.getCollection('catalogs').aggregate([\r\n"
			+ "{$match: {\"_id\": \"{emailId}\"}}, \r\n" + "{$unwind: \"$Catalogs\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook\"},\r\n" + "{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.prices\"},\r\n"
			+ "{$match: {\"Catalogs.priceBook.offerPrices.products.prices.recurringCharge.priceType\": \"recurringCharge\"}},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\"},\r\n"
			+ "{$project: {\r\n" + "    \"currency\": \"$Catalogs.priceBook.currency\",\r\n"
			+ "    \"baseUrl\": \"$baseUrl\",\r\n" + "    \"defaultImage\": \"$defaultImage\",\r\n"
			+ "    \"offerList\": {\r\n"
			+ "            \"offerName\": \"$Catalogs.priceBook.offerPrices.offerName\",\r\n"
			+ "            \"offerDescription\": { $slice: [ { $split: [ \"$Catalogs.priceBook.offerPrices.offerDescription\", \"|\" ] }, 0, 5 ] } ,\r\n"
			+ "            \"offerPricingStatus\": \"$Catalogs.priceBook.offerPrices.offerPricingStatus\",\r\n"
			+ "            \"offerCode\": \"$Catalogs.priceBook.offerPrices.offerCode\",\r\n"
			+ "            \"productName\": \"$Catalogs.priceBook.offerPrices.products.productName\",\r\n"
			+ "            \"productCode\": \"$Catalogs.priceBook.offerPrices.products.productCode\",\r\n"
			+ "            \"productType\": \"$Catalogs.priceBook.offerPrices.products.productType\",\r\n"
			+ "            \"price\": \"$Catalogs.priceBook.offerPrices.products.prices.recurringCharge.price\"\r\n"
			+ "        },\r\n" + "    \"content_url\": {$cond: {\r\n"
			+ "        if: {$eq: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\", \"content_url\"]}, \r\n"
			+ "        then: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\", \r\n"
			+ "        else: null}}\r\n" + "    }},\r\n" + "{$group: {\r\n"
			+ "     \"_id\": { \"emailId\": \"$_id\", \"currency\": \"$currency\", \"baseUrl\": \"$baseUrl\", \"defaultImage\": \"$defaultImage\", \"offerList\": \"$offerList\" },\r\n"
			+ "     \"content_url\": {$push: \"$content_url\"} \r\n" + "     }},\r\n" + "{$addFields: {\r\n"
			+ "    \"_id.offerList.content_url\": {$cond: {\r\n"
			+ "        if: {$eq: [{$size: {$filter: {input: \"$content_url\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]},\r\n"
			+ "        then: {$concat: [\"$_id.baseUrl\", \"T_Logo_bg.png\"]},\r\n"
			+ "        else:  {$concat: [\"$_id.baseUrl\", {$arrayElemAt: [{$filter: {input: \"$content_url\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}, 0]}]}   \r\n"
			+ "        }}\r\n" + "    }},\r\n" + "{$group: {\r\n"
			+ "     \"_id\": { \"emailId\": \"$_id.emailId\", \"currency\": \"$_id.currency\", \"offerList\": \"$offerList\" },\r\n"
			+ "     \"offer\": {$push: \"$_id.offerList\"}\r\n" + "     }},\r\n" + "{$project: {\r\n"
			+ "    \"_id\": 0,\r\n" + "    \"result\": {\r\n" + "        \"offerDetails\": {\r\n"
			+ "            \"currency\": \"$_id.currency\",\r\n" + "            \"offerList\": \"$offer\"\r\n"
			+ "            }\r\n" + "        }\r\n" + "    }}\r\n" + "])";

	public static String offerPriceAddon = "db.getCollection('catalogs').aggregate([\r\n"
			+ "{$match: {\"_id\": \"{emailId}\"}}, \r\n" + "{$unwind: \"$Catalogs\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook\"},\r\n" + "{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.prices\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\"},\r\n"
			+ "{$project: {\r\n" + "    \"data\": {\r\n" + "        \"currency\": \"$Catalogs.priceBook.currency\",\r\n"
			+ "        \"baseUrl\": \"$baseUrl\",\r\n" + "        \"oa\": {$cond: {\r\n"
			+ "            if: {$gt: [\"$Catalogs.priceBook.offerPrices.offerAssociation\", null]},\r\n"
			+ "            then: {$reduce: { input: \"$Catalogs.priceBook.offerPrices.offerAssociation\", initialValue: \"\",\r\n"
			+ "                in: {$concat: [\"$$value\", {$cond: {if: {$eq: [\"$$value\", \"\"]}, then: \"\", else: \",\"}}, \"$$this.offerCode\"]}\r\n"
			+ "                }},\r\n" + "            else: null }},\r\n" + "        \"offerList\": {\r\n"
			+ "                \"offerName\": \"$Catalogs.priceBook.offerPrices.offerName\",\r\n"
			+ "                \"offerDescription\": { $slice: [ { $split: [ \"$Catalogs.priceBook.offerPrices.offerDescription\", \"|\" ] }, 0, 5 ] } ,\r\n"
			+ "                \"offerPricingStatus\": \"$Catalogs.priceBook.offerPrices.offerPricingStatus\",\r\n"
			+ "                \"offerCode\": \"$Catalogs.priceBook.offerPrices.offerCode\",\r\n"
			+ "                \"offerType\": \"$Catalogs.priceBook.offerPrices.offerType\",\r\n"
			+ "                \"productName\": \"$Catalogs.priceBook.offerPrices.products.productName\",\r\n"
			+ "                \"productCode\": \"$Catalogs.priceBook.offerPrices.products.productCode\",\r\n"
			+ "                \"productType\": \"$Catalogs.priceBook.offerPrices.products.productType\",\r\n"
			+ "                \"price\": \"$Catalogs.priceBook.offerPrices.products.prices.recurringCharge.price\",\r\n"
			+ "                \"addon\": {\r\n"
			+ "                    \"offerCode\": \"$Catalogs.priceBook.offerPrices.offerCode\",\r\n"
			+ "                    \"offerName\": \"$Catalogs.priceBook.offerPrices.offerName\",\r\n"
			+ "                    \"price\": \"$Catalogs.priceBook.offerPrices.products.prices.onetimeCharge.price\"\r\n"
			+ "                    },\r\n" + "            }},\r\n" + "    \"content_url\": {$cond: { \r\n"
			+ "        if: {$eq: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\", \"content_url\"]}, \r\n"
			+ "        then: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\", \r\n"
			+ "        else: null}}}},\r\n"
			+ "{$group: { \"_id\": \"$data\", \"content_url\": {$push: \"$content_url\"}}},\r\n" + "{$addFields: {\r\n"
			+ "    \"_id.offerList.content_url\": {$cond: {\r\n"
			+ "        if: {$eq: [{$size: {$filter: {input: \"$content_url\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]},\r\n"
			+ "        then: {$concat: [\"$_id.baseUrl\", \"T_Logo_bg.png\"]},\r\n"
			+ "        else:  {$concat: [\"$_id.baseUrl\", {$arrayElemAt: [{$filter: {input: \"$content_url\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}, 0]}]}   \r\n"
			+ "        }},\r\n"
			+ "    \"_id.offerList.isActualUrl\": {$cond: { if: {$eq: [{$size: {$filter: {input: \"$content_url\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]}, then: 'No', else: 'Yes' }},\r\n"
			+ "    \"_id.offerList.oas\": {$split: [\"$_id.oa\", \",\"]}\r\n" + "        }},\r\n"
			+ "{$group: { \"_id\": { \"c\": \"$_id.currency\", \"oc\": \"$_id.offerList.offerCode\" }, \"offer\": {$push: \"$_id.offerList\"}}},\r\n"
			+ "{$addFields: {\r\n" + "    \"data\": {$cond: {\r\n"
			+ "        if: {$gt: [{$size: {$filter: {input: \"$offer\", as: \"d\", cond: {$ne: [ \"$$d.isActualUrl\", 'Yes']}}}}, 0]},\r\n"
			+ "        then: {$arrayElemAt: [{$filter: {input: \"$offer\", as: \"d\", cond: {$ne: [ \"$$d.isActualUrl\", 'Yes']}}}, 0]},\r\n"
			+ "        else: {$arrayElemAt: [\"$offer\", 0]}}}}},\r\n"
			+ "{$group: { \"_id\": { \"currency\": \"$_id.c\" }, \"offer\": {$push: \"$data\"}}},\r\n"
			+ "{$project: { \"_id\": 0, \"currency\": \"$_id.currency\", \"offerList\": \"$offer\" }}\r\n" + "])";

	public static String offerPriceAddonNew = "db.getCollection('catalogs').aggregate([\r\n"
			+ "{$match: {\"_id\": \"{emailId}\"}}, \r\n" + "{$unwind: \"$Catalogs\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook\"},\r\n" + "{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.prices\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\"},\r\n"
			+ "{$project: {\r\n" + "    \"data\": {\r\n" + "        \"currency\": \"$Catalogs.priceBook.currency\",\r\n"
			+ "        \"baseUrl\": \"$baseUrl\",\r\n" + "        \"oa\": {$cond: {\r\n"
			+ "            if: {$gt: [\"$Catalogs.priceBook.offerPrices.offerAssociation\", null]},\r\n"
			+ "            then: {$reduce: { input: \"$Catalogs.priceBook.offerPrices.offerAssociation\", initialValue: \"\",\r\n"
			+ "                in: {$concat: [\"$$value\", {$cond: {if: {$eq: [\"$$value\", \"\"]}, then: \"\", else: \",\"}}, \"$$this.offerCode\"]}\r\n"
			+ "                }},\r\n" + "            else: null }},\r\n" + "        \"offerList\": {\r\n"
			+ "                \"offerName\": \"$Catalogs.priceBook.offerPrices.offerName\",\r\n"
			+ "                \"offerDescription\": { $slice: [ { $split: [ \"$Catalogs.priceBook.offerPrices.offerDescription\", \"|\" ] }, 0, 5 ] } ,\r\n"
			+ "                \"offerPricingStatus\": \"$Catalogs.priceBook.offerPrices.offerPricingStatus\",\r\n"
			+ "                \"offerCode\": \"$Catalogs.priceBook.offerPrices.offerCode\",\r\n"
			+ "                \"offerType\": \"$Catalogs.priceBook.offerPrices.offerType\",\r\n"
			+ "                \"productName\": \"$Catalogs.priceBook.offerPrices.products.productName\",\r\n"
			+ "                \"productCode\": \"$Catalogs.priceBook.offerPrices.products.productCode\",\r\n"
			+ "                \"productType\": \"$Catalogs.priceBook.offerPrices.products.productType\",\r\n"
			+ "                \"price\": { $concat: [ \"$Catalogs.priceBook.offerPrices.products.prices.recurringCharge.price\", \" €\"] },\r\n"
			+ "                \"onetimePrice\": { $concat: [ \"$Catalogs.priceBook.offerPrices.products.prices.onetimeCharge.price\", \" €\"] },\r\n"
			+ "                \"addon\": {\r\n"
			+ "                    \"offerCode\": \"$Catalogs.priceBook.offerPrices.offerCode\",\r\n"
			+ "                    \"offerName\": \"$Catalogs.priceBook.offerPrices.offerName\",\r\n"
			+ "                    \"price\": { $concat: [ \"$Catalogs.priceBook.offerPrices.products.prices.onetimeCharge.price\", \" €/ one time\"] }\r\n"
			+ "                    },\r\n" + "            }},\r\n" + "    \"content_url\": {$cond: { \r\n"
			+ "        if: {$eq: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\", \"content_url\"]}, \r\n"
			+ "        then: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\", \r\n"
			+ "        else: null}}}},\r\n"
			+ "{$group: { \"_id\": \"$data\", \"content_url\": {$push: \"$content_url\"}}},\r\n" + "{$addFields: {\r\n"
			+ "    \"_id.offerList.content_url\": {$cond: {\r\n"
			+ "        if: {$eq: [{$size: {$filter: {input: \"$content_url\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]},\r\n"
			+ "        then: {$concat: [\"$_id.baseUrl\", \"T_Logo_bg.png\"]},\r\n"
			+ "        else:  {$concat: [\"$_id.baseUrl\", {$arrayElemAt: [{$filter: {input: \"$content_url\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}, 0]}]}   \r\n"
			+ "        }},\r\n"
			+ "    \"_id.offerList.isActualUrl\": {$cond: { if: {$eq: [{$size: {$filter: {input: \"$content_url\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]}, then: 'No', else: 'Yes' }},\r\n"
			+ "    \"_id.offerList.oas\": {$split: [\"$_id.oa\", \",\"]}\r\n" + "        }},\r\n"
			+ "{$group: { \"_id\": { \"c\": \"$_id.currency\", \"oc\": \"$_id.offerList.offerCode\" }, \"offer\": {$push: \"$_id.offerList\"}}},\r\n"
			+ "{$addFields: {\r\n" + "    \"data\": {$cond: {\r\n"
			+ "        if: {$gt: [{$size: {$filter: {input: \"$offer\", as: \"d\", cond: {$ne: [ \"$$d.isActualUrl\", 'Yes']}}}}, 0]},\r\n"
			+ "        then: {$arrayElemAt: [{$filter: {input: \"$offer\", as: \"d\", cond: {$ne: [ \"$$d.isActualUrl\", 'Yes']}}}, 0]},\r\n"
			+ "        else: {$arrayElemAt: [\"$offer\", 0]}}}}},\r\n"
			+ "{$group: { \"_id\": { \"currency\": \"$_id.c\" }, \"offer\": {$push: \"$data\"}}},\r\n"
			+ "{$project: { \"_id\": 0, \"currency\": \"$_id.currency\", \"offerList\": \"$offer\" }}\r\n" + "])";

	public static String offerPrice = "db.getCollection('catalogs').aggregate([\r\n"
			+ "{$match: {\"_id\": \"{emailId}\"}}, \r\n" + "{$unwind: \"$Catalogs\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook\"},\r\n" + "{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.prices\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\"},\r\n"
			+
			// "{$match: {\"Catalogs.priceBook.offerPrices.offerType\": \"Simple\"}},\r\n" +
			"{$project: {\r\n" + "    \"data\": {\r\n" + "        \"currency\": \"$Catalogs.priceBook.currency\",\r\n"
			+ "        \"baseUrl\": \"$baseUrl\",\r\n" + "        \"offerList\": {\r\n"
			+ "                \"offerName\": \"$Catalogs.priceBook.offerPrices.offerName\",\r\n"
			+ "                \"offerDescription\": { $slice: [ { $split: [ \"$Catalogs.priceBook.offerPrices.offerDescription\", \"|\" ] }, 0, 5 ] } ,\r\n"
			+ "                \"offerPricingStatus\": \"$Catalogs.priceBook.offerPrices.offerPricingStatus\",\r\n"
			+ "                \"offerCode\": \"$Catalogs.priceBook.offerPrices.offerCode\",\r\n"
			+ "                \"productName\": \"$Catalogs.priceBook.offerPrices.products.productName\",\r\n"
			+ "                \"productCode\": \"$Catalogs.priceBook.offerPrices.products.productCode\",\r\n"
			+ "                \"productType\": \"$Catalogs.priceBook.offerPrices.products.productType\",\r\n"
			+ "                \"price\": \"$Catalogs.priceBook.offerPrices.products.prices.recurringCharge.price\"\r\n"
			+ "            }},\r\n" + "    \"content_url\": {$cond: { \r\n"
			+ "        if: {$eq: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\", \"content_url\"]}, \r\n"
			+ "        then: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\", \r\n"
			+ "        else: null}}}},\r\n"
			+ "{$group: { \"_id\": \"$data\", \"content_url\": {$push: \"$content_url\"}}},\r\n" + "{$addFields: {\r\n"
			+ "    \"_id.offerList.content_url\": {$cond: {\r\n"
			+ "        if: {$eq: [{$size: {$filter: {input: \"$content_url\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]},\r\n"
			+ "        then: {$concat: [\"$_id.baseUrl\", \"T_Logo_bg.png\"]},\r\n"
			+ "        else:  {$concat: [\"$_id.baseUrl\", {$arrayElemAt: [{$filter: {input: \"$content_url\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}, 0]}]}   \r\n"
			+ "        }}}},\r\n"
			+ "{$group: { \"_id\": { \"currency\": \"$_id.currency\" }, \"offer\": {$push: \"$_id.offerList\"}}},\r\n"
			+ "{$project: { \"_id\": 0, \"result\": { \"offerDetails\": { \"currency\": \"$_id.currency\", \"offerList\": \"$offer\" } }}}\r\n"
			+ "])";

	public static String selectedOffers = "db.getCollection('catalogs').aggregate([\r\n"
			+ "{$match: {\"_id\": \"{emailId}\"}},\r\n" + "{$unwind: \"$Catalogs\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook\"},\r\n" + "{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products\"},\r\n"
			+ "{$match: {\"Catalogs.priceBook.offerPrices.offerCode\": {$in: {selectedOfferCodes}}}},\r\n"
			+ "{$addFields: {\r\n"  
			+ "    \"isConf\": {\r\n"  
			+ "        \"isCongurable1\":  {$size: {$filter: {input: {$arrayElemAt: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\", 0]}, \"as\": \"char\", cond: { $and: [{$eq: [\"$$char.configurable\", true]}, {$eq: [\"$$char.isVisible\", true]}]}}}},\r\n"  
			+ "        \"isCongurable2\":  {$size: {$filter: {input: {$arrayElemAt: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\", 0]}, \"as\": \"char\", cond: { $and: [{$or: [{$eq: [\"$$char.configurable\", true]}, {$in: [\"$$char.productSpecAttributeValueType\", [\"String\", \"List\"]]}]}, {$eq: [\"$$char.isVisible\", true]}]}}}}}}},"
			+ "{$project: {\r\n" + "    \"data\": {\r\n"
			+ "        \"offerDesc\": { $slice: [ {$split: [\"$Catalogs.priceBook.offerPrices.offerDescription\", \"|\"]}, 0, 5 ] } ,\r\n"
			+ "        \"configDetails\": \"dsfsdf\",\r\n"
			+ "        \"offerCode\": \"$Catalogs.priceBook.offerPrices.offerCode\",\r\n"
			+ "        \"offerName\": \"$Catalogs.priceBook.offerPrices.offerName\",\r\n"
			+ "        \"viewColorCodeMap\": {\"editConfig\": {$cond: {if: {$or: [{$gt: [\"$isConf.isCongurable1\", 0]}, {$gt: [\"$isConf.isCongurable2\", 0]}]}, then: \"\", else: \"disabled\"}}},"
			+ "		   \"editConfig\": {$cond: {if: {$gt: [\"$isConf.isCongurable1\", 0]}, then: \"Configure\", else: \"Check Configure\"}}},"
			//+ "        \"editConfig\": {$cond: {if: {$gt: [{ $size: {$filter: {input: {$arrayElemAt: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\", 0]}, \"as\": \"char\", cond: { $and: [{$eq: [\"$$char.configurable\", true]}, {$eq: [\"$$char.isVisible\", true]}]}}} }, 0]}, then: \"Configure\", else: \"Check Configure\"}}},"
			// + "        \"editConfig\": \"Configure\"},\r\n"
			+ "    \"productOffer\": \"$Catalogs.priceBook.offerPrices.products.productName\",\r\n"
			+ "    \"productType\": \"$Catalogs.priceBook.offerPrices.products.productType\" }},\r\n"
			+ "{$group: {_id: {\"emailId\": \"$_id\", \"data\": \"$data\"}, \"productOffer\": {$push: \"$productOffer\"}, \"productType\": {$push: \"$productType\"}}},\r\n"
			+ "{$addFields: {\r\n"
			+ "    \"_id.data.productOffer\": { $reduce: { \"input\": \"$productOffer\", \"initialValue\": \"\", \"in\": {$concat: [\"$$value\", {$cond: {if: {$eq: [\"$$value\", \"\"]}, then: \"\", else: \",\"}},\"$$this\"]}}},\r\n"
			+ "    \"_id.data.productType\": { $reduce: { \"input\": \"$productType\", \"initialValue\": \"\", \"in\": {$concat: [\"$$value\", {$cond: {if: {$eq: [\"$$value\", \"\"]}, then: \"\", else: \",\"}},\"$$this\"]}}},\r\n"
			+ "    }},\r\n" + "{$group: {_id: \"$_id.emailId\", \"data\": {$push: \"$_id.data\"}}},\r\n"
			+ "{$unwind: {\"path\": \"$data\", \"includeArrayIndex\": \"rowNumber\"}},\r\n"
			+ "{$addFields: {\"data.serialNo\": { $add: [ \"$rowNumber\", 1 ] }}},\r\n"
			+ "{$group: {_id: null, \"data\": {$push: \"$data\"}}},\r\n" + "{$project: {\r\n" + "    \"_id\": 0,\r\n"
			+ "    \"result\": { \"offerDetails\": { \"dataList\": \"$data\", \"displayfields\": [\"Product/Offer\", \"Offer Desc\", \"Config details\", \"Edit Config\"] } }\r\n"
			+ "    }}\r\n" + "])";

	public static String selectedOffersOld = "db.getCollection('catalogs').aggregate([\r\n"
			+ "{$match: {\"_id\": \"{emailId}\"}},\r\n" + "{$unwind: \"$Catalogs\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook\"},\r\n" + "{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products\"},\r\n"
			+ "{$match: {\"Catalogs.priceBook.offerPrices.offerCode\": {$in: {selectedOfferCodes}}}},\r\n"
			+ "{$project: {\r\n" + "    \"data\": {\r\n"
			+ "        \"offerDesc\": { $slice: [ {$split: [\"$Catalogs.priceBook.offerPrices.offerDescription\", \"|\"]}, 0, 5 ] } ,\r\n"
			+ "        \"configDetails\": \"dsfsdf\",\r\n"
			+ "        \"offerCode\": \"$Catalogs.priceBook.offerPrices.offerCode\",\r\n"
			+ "        \"productOffer\": \"$Catalogs.priceBook.offerPrices.products.productName\",\r\n"
			+ "        \"editConfig\": \"Configure\"\r\n" + "        }\r\n" + "    }},\r\n"
			+ "{$group: {_id: \"$_id\", \"data\": {$push: \"$data\"}}},\r\n"
			+ "{$unwind: {\"path\": \"$data\", \"includeArrayIndex\": \"rowNumber\"}},\r\n"
			+ "{$addFields: {\"data.serialNo\": { $add: [ \"$rowNumber\", 1 ] }}},\r\n"
			+ "{$group: {_id: null, \"data\": {$push: \"$data\"}}},\r\n" + "{$project: {\r\n" + "    \"_id\": 0,\r\n"
			+ "    \"result\": {\r\n" + "        \"offerDetails\": {\r\n" + "            \"dataList\": \"$data\",\r\n"
			+ "            \"displayfields\": [\"Product/Offer\", \"Offer Desc\", \"Config details\", \"Edit Config\"]\r\n"
			+ "            }\r\n" + "        }\r\n" + "    }}\r\n" + "])";

	public static String offerConfigNew = "db.getCollection('catalogs').aggregate([\r\n"
			+ "{$match: {\"_id\": \"{emailId}\"}},\r\n" + "{$unwind: \"$Catalogs\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook\"},\r\n" + "{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products\"},\r\n"
			+ "{$match: {\"Catalogs.priceBook.offerPrices.offerCode\": \"{selectedOfferCodes}\"}},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\"},\r\n"
			+ "{$match: {\"Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.isVisible\": true}},\r\n"
			+ "{$sort: {\"Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.sequence\": 1}},\r\n"
			+ "{$addFields: {\r\n"
			+ "    \"dataKey\": {$concat: [\"$Catalogs.priceBook.offerPrices.offerCode\", \"_\", \"$_id\"]},\r\n"
			+ "    \"elementKeyPanel\": \"div_panel\",\r\n" + "    \"elementKey\": {$concat: [\r\n"
			+ "        {$toLower: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValueType\"},\r\n"
			+ "        \"_\", { $convert: { input: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.configurable\", to: \"string\" } }]},\r\n"
			+ "    \"isRequired\": {\r\n" + "        $cond: {\r\n"
			+ "            if: { $gt: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.minCardinality\", 0] },\r\n"
			+ "            then: true,\r\n" + "            else: null}},\r\n"
			+ "    \"attr\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\" }},\r\n"
			+ "{$lookup: { \"from\": \"dynamicElements\", \"localField\": \"elementKey\", \"foreignField\": \"_id\", \"as\": \"elems\" }},\r\n"
			+ "{$lookup: { \"from\": \"dynamicElements\", \"localField\": \"elementKeyPanel\", \"foreignField\": \"_id\", \"as\": \"divElems\" }},\r\n"
			+ "{$unwind: \"$elems\"},\r\n" + "{$unwind: \"$divElems\"},\r\n" + "{$addFields: {\r\n"
			+ "    \"elems.prop.dataBinding.beanClass\": \"productspecattributevalue\",\r\n"
			+ "    \"elems.prop.dataBinding.beanId\": {"
			+ "        $concat: [\"$attr.productAttributeProductAttributeCode\",\"_\", \"$Catalogs.priceBook.offerPrices.products.productCode\",\"_\", "
			+ "        {$cond: { if: {$gt: [\"$Catalogs.priceBook.offerPrices.products.unique\", null]}, then: \"$Catalogs.priceBook.offerPrices.products.unique\", else: \"\"}}]},"
			+ "    \"elems.prop.dataBinding.rdKey\": {$cond: {if: {$in: [{$toLower:\"$attr.productSpecAttributeValueType\"}, [\"list\", \"singleselect\",\"multiselect\"]]}, then: \"$attr.productSpecAttributeValue\", else: null}},\r\n"
			+ "    \"elems.prop.dataBinding.rdVal\": {$cond: {if: {$in: [{$toLower:\"$attr.productSpecAttributeValueType\"}, [\"list\", \"singleselect\",\"multiselect\"]]}, then: \"$attr.productSpecAttributeValue\", else: null}},\r\n"
			// + "    \"elems.prop.dataBinding.defaultValue\": {$cond: {if: {$in: [\"$attr.productSpecAttributeValueType\", [\"String\"]]}, then: \"$attr.productSpecAttributeValue\", else: null}},\r\n"
			+ "    \"elems.prop.dataBinding.defaultValue\": {$cond: {if: {$in: [\"$attr.productSpecAttributeValueType\", [\"String\"]]}, then: \"$attr.productSpecAttributeValue\", else: {$cond: {if: {$and: [{$eq: [{$toLower: \"$attr.productSpecAttributeValueType\"}, \"list\"]}, {$eq: [{$size: {$split: [\"$attr.productSpecAttributeValue\", \",\"]}}, 1]}]}, then: \"$attr.productSpecAttributeValue\", else: null}}}},"
			+ "    \"elems.prop.sequence\": \"$attr.sequence\",\r\n"
			+ "    \"elems.prop.properties.dummyOption\": {$cond: {if: {$and: [{$eq: [{$toLower: \"$attr.productSpecAttributeValueType\"}, \"list\"]}, {$eq: [{$size: {$split: [\"$attr.productSpecAttributeValue\", \",\"]}}, 1]}]}, then: false, else: true}},"
			+ "    \"elems.prop.properties.title\": {$ifNull: [\"$attr.displayAttributeName\", \"$attr.productSpecAttributeName\"]},"
			+ "    \"elems.prop.properties.label\": {$ifNull: [\"$attr.displayAttributeName\", \"$attr.productSpecAttributeName\"]},"
			// + "    \"elems.prop.properties.title\": {$ifNull: [\"$attr.displayAttributeName\", \"$attr.productSpecAttributeName\"]},"
			//+ "    \"elems.prop.properties.title\": {$concat: [{$ifNull: [\"$attr.displayAttributeName\", \"$attr.productSpecAttributeName\"]}, {$cond: {if: {$in: [{$toLower: {$ifNull: [{$toLower: \"$attr.unitOfMeasurement\"}, \"\"]}}, [\"\", \"na\"]]}, then: \"\", else: {$ifNull: [{$concat: [\" (\", \"$attr.unitOfMeasurement\", \")\"]}, \"\"]}}}]},"
			// + "    \"elems.prop.properties.label\": {$concat: [{$ifNull: [\"$attr.displayAttributeName\", \"$attr.productSpecAttributeName\"]}, {$cond: {if: {$in: [{$toLower: {$ifNull: [{$toLower: \"$attr.unitOfMeasurement\"}, \"\"]}}, [\"\", \"na\"]]}, then: \"\", else: {$ifNull: [{$concat: [\" (\", \"$attr.unitOfMeasurement\", \")\"]}, \"\"]}}}]},"
			// + "    \"elems.prop.properties.umo\": \"$attr.unitOfMeasurement\","
			+ "    \"elems.prop.properties.umo\": {$cond: {if: {$in: [{$toLower: {$ifNull: [{$toLower: \"$attr.unitOfMeasurement\"}, \"\"]}}, [\"\", \"na\"]]}, then: null, else: \"$attr.unitOfMeasurement\"}},"
			// + "    \"elems.prop.properties.title\": {$concat: [{$ifNull: [\"$attr.displayAttributeName\", \"$attr.productSpecAttributeName\"]}, {$ifNull: [{$concat: [\" (\", \"$attr.unitOfMeasurement\", \")\"]}, \"\"]}]},"
			// + "    \"elems.prop.properties.label\": {$concat: [{$ifNull: [\"$attr.displayAttributeName\", \"$attr.productSpecAttributeName\"]}, {$ifNull: [{$concat: [\" (\", \"$attr.unitOfMeasurement\", \")\"]}, \"\"]}]},"
			+ "    \"elems.prop.properties.id\": {"
			+ "        $concat: [\"$attr.productAttributeProductAttributeCode\",\"_\", \"$Catalogs.priceBook.offerPrices.products.productCode\",\"_\", "
			+ "        {$cond: { if: {$gt: [\"$Catalogs.priceBook.offerPrices.products.unique\", null]}, then: \"$Catalogs.priceBook.offerPrices.products.unique\", else: \"\"}}]},"
			+ "    \"elems.prop.properties.required\": \"$isRequired\",\r\n" + "    \"column\": \"$divElems.prop\"}},\r\n"
			+ "{$addFields: { \r\n" + "	\"column.dataBinding.beanClass\": \"productspecattributevalue\",\r\n"
			+ "\"column.dataBinding.unique\": \"$Catalogs.priceBook.offerPrices.products.unique\","
			+ "	\"column.properties.displayName\": \"$Catalogs.priceBook.offerPrices.products.productName\" }},\r\n"
			+ "{$lookup: { \"from\": \"customerCatalogs\", \"localField\": \"dataKey\", \"foreignField\": \"_id\", \"as\": \"ans\" }},\r\n"
			+ "{$addFields: { \"productspecattributevalue\": {\r\n"
			+ "    $cond: { if: {$gt: [{$size: \"$ans\"}, 0]}, then: {$arrayElemAt: [\"$ans.responseMap\", 0]}, else: {}}}}},\r\n"
			+ "{$group: {\r\n"
			+ "    \"_id\": { \"dzcolumn\": \"$column\", \"ans\": \"$productspecattributevalue\"},\r\n"
			+ "    \"columns\": {$push: \"$elems.prop\"}}},\r\n"
			+ "{$addFields: { \"_id.dzcolumn.columns\": \"$columns\" }},\r\n"
			+ "{$group: { \"_id\": \"$_id.ans\", \"columns\": {$push: \"$_id.dzcolumn\"}}}," + "{$project: {\r\n"
			+ "    \"_id\": 0,\r\n" + "    \"result\": {\r\n"
			+ "        \"ValueJson\": { \"productspecattributevalue\": \"$_id\" },\r\n"
			+ "        \"ScreenJson\": { \r\n" + "            \"dropZone\": {\r\n"
			+ "                \"_elementid\" : \"3\",\r\n" + "                \"name\" : \"Section1\",\r\n"
			+ "                \"title\" : \"Panel\",\r\n" + "                \"icon\" : \"fa fa-square-o\",\r\n"
			+ "                \"type\" : \"div\",\r\n" + "                \"_id\" : \"Div\",\r\n"
			+ "                \"dataBinding\": { \"beanClass\": \"productspecattributevalue\" },\r\n"
			+ "                \"properties\" : {\r\n" + "                    \"id\" : \"Div\",\r\n"
			+ "                    \"name\" : \"Div\",\r\n" + "                    \"elementType\" : \"complex\",\r\n"
			+ "                    \"class\" : \"col-lg-12 col-md-12 col-sm-12 col-xs-12\"\r\n"
			+ "                },\r\n" + "                \"columns\": \"$columns\" } }}}}])";

	public static String offerConfig = "db.getCollection('catalogs').aggregate([\r\n"
			+ "{$match: {\"_id\": \"{emailId}\"}},\r\n" + "{$unwind: \"$Catalogs\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook\"},\r\n" + "{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products\"},\r\n"
			+ "{$match: {\"Catalogs.priceBook.offerPrices.offerCode\": \"{selectedOfferCodes}\"}},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\"},\r\n"
			+ "{$match: {\"Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.isVisible\": true}},\r\n"
			+ "{$sort: {\"Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.sequence\": 1}},\r\n"
			+ "{$project: {\r\n" + "    \"_id\": 1,\r\n"
			+ "    \"id\": \"$Catalogs.priceBook.offerPrices.offerCode\",\r\n" + "    \"dropZone\": {\r\n"
			+ "        \"_elementid\": \"3\",\r\n" + "        \"name\": \"Section1\",\r\n"
			+ "        \"icon\": \"fa fa-square-o\",\r\n" + "        \"_id\": \"Div\",\r\n"
			+ "        \"title\": \"Panel\",\r\n" + "        \"type\": \"div\",\r\n" + "        \"dataBinding\": {\r\n"
			+ "            \"beanClass\": \"productspecattributevalue\"\r\n" + "            },\r\n"
			+ "        \"properties\": {\r\n" + "            \"name\": \"Div\",\r\n"
			+ "            \"id\": \"Div\",\r\n" + "            \"elementType\": \"complex\",\r\n"
			+ "            \"class\": \"col-lg-12 col-md-12 col-sm-12 col-xs-12\"\r\n" + "            },\r\n"
			+ "        \"columns\": {\r\n" + "            \"_elementid\": \"3\",\r\n"
			+ "            \"name\": \"Panel\",\r\n" + "            \"title\": \"Panel\",\r\n"
			+ "            \"icon\": \"fa fa-square-o\",\r\n" + "            \"type\": \"div\",\r\n"
			+ "            \"_id\": \"Div\",\r\n" + "            \"properties\": {\r\n"
			+ "                \"id\": \"Div\",\r\n" + "                \"name\": \"Div\",\r\n"
			+ "                \"elementType\": \"complex\",\r\n" + "                \"orientation\": \"Top\",\r\n"
			+ "                \"panelView\": \"true\",\r\n" + "                \"displayPanel\": \"true\",\r\n"
			+ "                \"displayName\": \"$Catalogs.priceBook.offerPrices.products.productName\"\r\n"
			+ "                },\r\n" + "            \"dataBinding\": {\r\n"
			+ "                \"beanClass\": \"productspecattributevalue\"\r\n" + "                }\r\n"
			+ "            }\r\n" + "        },\r\n" + "		\"columns\": {\r\n" + "			$cond: {\r\n"
			+ "				if: { $eq: [{$toLower: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValueType\"}, \"list\"] },\r\n"
			+ "				then: {\r\n" + "					\"dataBinding\": {\r\n"
			+ "						\"beanClass\": \"productspecattributevalue\",\r\n"
			+ "						\"rdKey\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n"
			+ "						\"rdVal\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n"
			+ "						\"beanId\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecBindAttributeName\"\r\n"
			+ "					},\r\n" + "					\"properties\": {\r\n"
			+ "						\"title\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n"
			+ "						\"id\": \"dropdown-1\",\r\n"
			+ "						\"name\": \"dropdown-1\",\r\n"
			+ "						\"type\": \"HtmlSelectOneMenu\",\r\n"
			+ "						\"elementContainerStyleClass\": \"col-lg-12 col-md-12 col-sm-12 col-xs-12 truncatedStringDyanamic pb-10\",\r\n"
			+ "						\"orientation\": \"Left\",\r\n"
			+ "						\"elementType\": \"simple\",\r\n"
			+ "						\"dummyOption\": \"true\",\r\n"
			+ "						\"label\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n"
			+ "						\"required\": {\r\n" + "							$cond: {\r\n"
			+ "								if: { $gt: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.minCardinality\", 0] },\r\n"
			+ "								then: true,\r\n" + "								else: null\r\n"
			+ "								}\r\n" + "							}\r\n" + "						},\r\n"
			+ "					 \"_elementid\": \"15\",\r\n" + "					\"name\": \"Dropdown\",\r\n"
			+ "					\"type\": \"dropdown\",\r\n" + "					\"desc\": \" \",\r\n"
			+ "					\"_id\": \"dropdown-1\",\r\n"
			+ "					\"sequence\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.sequence\"\r\n"
			+ "					\r\n" + "					},\r\n" + "				else: {\r\n"
			+ "					$cond: {\r\n" + "						if: {\r\n"
			+ "                                                    $and: [\r\n"
			+ "                                                    {$eq: [{$toLower: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValueType\"}, \"string\"]},\r\n"
			+ "                                                    {$eq: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.configurable\", true]}\r\n"
			+ "                                                    ]\r\n"
			+ "                                                    },\r\n" + "						then: {\r\n"
			+ "							\"dataBinding\": {\r\n"
			+ "                                                            \"beanClass\": \"productspecattributevalue\",\r\n"
			+ "                                                            \"defaultValue\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n"
			+ "                                                            \"beanId\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecBindAttributeName\"\r\n"
			+ "							},\r\n" + "							\"properties\": {\r\n"
			+ "                                                            \"title\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n"
			+ "                                                            \"id\": \"genericbox-1\",\r\n"
			+ "                                                            \"name\": \"genericbox-1\",\r\n"
			+ "                                                            \"type\": \"HtmlInputText\",\r\n"
			+ "                                                            \"elementContainerStyleClass\": \"col-lg-12 truncatedStringDyanamic col-md-12 col-sm-12 col-xs-12 pb-10\",\r\n"
			+ "                                                            \"orientation\": \"Left\",\r\n"
			+ "                                                            \"elementType\": \"simple\",\r\n"
			+ "                                                            \"label\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n"
			+ "                                                            \"required\": {\r\n"
			+ "                                                                $cond: {\r\n"
			+ "                                                                    if: { $gt: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.minCardinality\", 0] },\r\n"
			+ "                                                                    then: true,\r\n"
			+ "                                                                    else: null\r\n"
			+ "                                                                    }\r\n"
			+ "                                                                }\r\n"
			+ "                                                            },\r\n"
			+ "							\"_elementid\": \"1\",\r\n" + "							\"name\": \"Text\",\r\n"
			+ "							\"type\": \"genericbox\",\r\n"
			+ "							\"desc\": \"An element which enables to enter text\",\r\n"
			+ "							\"_id\": \"genericbox-1\",\r\n"
			+ "							\"sequence\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.sequence\"\r\n"
			+ "							},\r\n" + "						else: {\r\n"
			+ "							$cond: { \r\n" + "								if: {\r\n"
			+ "                                                                    $and: [\r\n"
			+ "                                                                    {$eq: [{$toLower: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValueType\"}, \"string\"]},\r\n"
			+ "                                                                    {$eq: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.configurable\", false]}\r\n"
			+ "                                                                    ]\r\n"
			+ "								},\r\n" + "								then: {\r\n"
			+ "									\"dataBinding\": {\r\n"
			+ "                                                                            \"beanClass\": \"productspecattributevalue\",\r\n"
			+ "                                                                            \"defaultValue\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n"
			+ "                                                                            \"beanId\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecBindAttributeName\"\r\n"
			+ "									},\r\n" + "									\"properties\": {\r\n"
			+ "                                                                            \"title\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n"
			+ "                                                                            \"id\": \"label-1\",\r\n"
			+ "                                                                            \"name\": \"label-1\",\r\n"
			+ "                                                                            \"type\": \"HtmlOutputLabel\",\r\n"
			+ "                                                                            \"elementContainerStyleClass\": \"col-lg-12 truncatedStringDyanamic col-md-12 col-sm-12 col-xs-12 pb-10\",\r\n"
			+ "                                                                            \"orientation\": \"Left\",\r\n"
			+ "                                                                            \"elementType\": \"simple\",\r\n"
			+ "                                                                            \"label\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\"\r\n"
			+ "                                                                        },\r\n"
			+ "									\"_elementid\": \"14\",\r\n"
			+ "									\"name\": \"Label\",\r\n"
			+ "									\"type\": \"label\",\r\n"
			+ "									\"desc\": \"An element which enables to labelize Headers\",\r\n"
			+ "									\"_id\": \"label-1\",\r\n"
			+ "									\"sequence\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.sequence\"\r\n"
			+ "									},\r\n" + "								else: {\r\n"
			+ "									$cond: { \r\n"
			+ "                                                                            if: { $eq: [{$toLower: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValueType\"}, \"singleselect\"] },\r\n"
			+ "                                                                            then: {\r\n"
			+ "                                                                                    \"dataBinding\": {\r\n"
			+ "                                                                                        \"beanClass\": \"productspecattributevalue\",\r\n"
			+ "                                                                                        \"rdKey\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n"
			+ "                                                                                        \"rdVal\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n"
			+ "                                                                                        \"beanId\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecBindAttributeName\"\r\n"
			+ "                                                                                    },\r\n"
			+ "                                                                                    \"properties\": {\r\n"
			+ "                                                                                        \"title\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n"
			+ "                                                                                        \"id\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecBindAttributeName\",\r\n"
			+ "                                                                                        \"name\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecBindAttributeName\",\r\n"
			+ "                                                                                        \"type\": \"HtmlSelectOneRadio\",\r\n"
			+ "                                                                                        \"elementContainerStyleClass\": \"col-lg-12 truncatedStringDyanamic col-md-12 col-sm-12 col-xs-12 pb-10\",\r\n"
			+ "                                                                                        \"orientation\": \"Left\",\r\n"
			+ "                                                                                        \"elementType\": \"simple\",\r\n"
			+ "                                                                                        \"label\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n"
			+ "                                                                                        \"required\": {\r\n"
			+ "                                                                                            $cond: {\r\n"
			+ "                                                                                                    if: { $gt: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.minCardinality\", 0] },\r\n"
			+ "                                                                                                    then: true,\r\n"
			+ "                                                                                                    else: null\r\n"
			+ "                                                                                                    }\r\n"
			+ "                                                                                            }\r\n"
			+ "                                                                                        },\r\n"
			+ "                                                                                    \"_elementid\": \"12\",\r\n"
			+ "                                                                                    \"name\": \"Radio Group\",\r\n"
			+ "                                                                                    \"type\": \"radiolist\",\r\n"
			+ "                                                                                    \"desc\": \"Enables user to select between different alternatives\",\r\n"
			+ "                                                                                    \"_id\": \"radiolist-1\",\r\n"
			+ "                                                                                    \"sequence\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.sequence\"\r\n"
			+ "                                                                                    },\r\n"
			+ "										else: {\r\n"
			+ "											$cond: {\r\n"
			+ "                                                                                            if: { $eq: [{$toLower: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValueType\"}, \"multiselect\"] },\r\n"
			+ "												then: {\r\n"
			+ "                                                                                                    \"dataBinding\": {\r\n"
			+ "                                                                                                            \"beanClass\": \"productspecattributevalue\",\r\n"
			+ "                                                                                                            \"rdKey\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n"
			+ "                                                                                                            \"rdVal\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n"
			+ "                                                                                                            \"beanId\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecBindAttributeName\"\r\n"
			+ "                                                                                                    },\r\n"
			+ "                                                                                                    \"properties\": {\r\n"
			+ "                                                                                                            \"title\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n"
			+ "                                                                                                            \"id\": \"checkbox-1\",\r\n"
			+ "                                                                                                            \"name\": \"checkbox-1\",\r\n"
			+ "                                                                                                            \"type\": \"HtmlSelectBooleanCheckbox\",\r\n"
			+ "                                                                                                            \"elementContainerStyleClass\": \"col-lg-12 truncatedStringDyanamic col-md-12 col-sm-12 col-xs-12 pb-10\",\r\n"
			+ "                                                                                                            \"orientation\": \"Left\",\r\n"
			+ "                                                                                                            \"elementType\": \"simple\",\r\n"
			+ "                                                                                                            \"isDynamicCheck\": \"true\",\r\n"
			+ "                                                                                                            \"required\": {\r\n"
			+ "                                                                                                                $cond: {\r\n"
			+ "                                                                                                                        if: { $gt: [\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.minCardinality\", 0] },\r\n"
			+ "                                                                                                                        then: true,\r\n"
			+ "                                                                                                                        else: null\r\n"
			+ "                                                                                                                        }\r\n"
			+ "                                                                                                                }\r\n"
			+ "                                                                                                            },\r\n"
			+ "													\"_elementid\": \"6\",\r\n"
			+ "													\"name\": \"Checkbox\",\r\n"
			+ "													\"type\": \"checkbox\",\r\n"
			+ "													\"_id\": \"checkbox-1\",\r\n"
			+ "													\"sequence\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.sequence\"\r\n"
			+ "													},\r\n"
			+ "												else: {\r\n"
			+ "                                                                                                    \"dataBinding\": {\r\n"
			+ "                                                                                                        \"beanClass\": \"productspecattributevalue\",\r\n"
			+ "                                                                                                        \"beanId\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecBindAttributeName\"\r\n"
			+ "                                                                                                        },\r\n"
			+ "                                                                                                    \"properties\": {\r\n"
			+ "                                                                                                        \"label\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\"\r\n"
			+ "                                                                                                        },\r\n"
			+ "                                                                                                    \"sequence\": \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.sequence\"\r\n"
			+ "                                                                                                    }\r\n"
			+ "												}\r\n" + "											}\r\n"
			+ "										}\r\n" + "									}\r\n"
			+ "								}\r\n" + "							}\r\n" + "						}\r\n"
			+ "					}\r\n" + "				}\r\n" + "			}\r\n" + "    }},\r\n" + "{$group: {\r\n"
			+ "        \"_id\": {\"emailId\": \"$_id\", \"dropZone\": \"$dropZone\", \"id\": \"$id\"},\r\n"
			+ "        \"columns\": {$push: \"$columns\"},\r\n" + "        \"data\": {$push: \"$data\"}\r\n"
			+ "    }},\r\n" + "{$lookup: {\r\n" + "        \"from\": \"customerCatalogs\",\r\n"
			+ "        \"let\": {\"emailId\": \"$_id.emailId\", \"offerId\": \"$_id.id\"},\r\n"
			+ "        \"pipeline\": [{\r\n" + "            $match: {\r\n" + "                \"$expr\": {\r\n"
			+ "                    $and: [{\r\n" + "                        $eq: [\"$emailId\", \"$$emailId\"]\r\n"
			+ "                        }, {\r\n"
			+ "                        $eq: [\"$offerPrice.offerCode\", \"$$offerId\"]\r\n"
			+ "                            }]\r\n" + "                    }\r\n" + "                }\r\n"
			+ "            }],\r\n" + "        \r\n" + "        \"as\": \"responseMap\"\r\n" + "        }\r\n"
			+ "    },\r\n" + "{$project: {\r\n" + "    \"_id\": 0,\r\n" + "    \"result\": {\r\n"
			+ "        \"ValueJson\": {\r\n" + "            \"productspecattributevalue\": {$cond: {\r\n"
			+ "                if: {$gt: [{$size: \"$responseMap.responseMap\"}, 0]}, \r\n"
			+ "                then: {$arrayElemAt: [\"$responseMap.responseMap\", 0]}, \r\n"
			+ "                else: {}}},            \r\n" + "            },\r\n" + "        \"ScreenJson\": {\r\n"
			+ "            \"dropZone\": {\r\n" + "                \"_elementid\": \"$_id.dropZone._elementid\",\r\n"
			+ "                \"name\": \"$_id.dropZone.name\",\r\n"
			+ "                \"icon\": \"$_id.dropZone.icon\",\r\n"
			+ "                \"_id\": \"$_id.dropZone._id\",\r\n"
			+ "                \"title\": \"$_id.dropZone.title\",\r\n"
			+ "                \"type\": \"$_id.dropZone.type\",\r\n"
			+ "                \"dataBinding\": \"$_id.dropZone.dataBinding\",\r\n"
			+ "                \"properties\": \"$_id.dropZone.properties\",\r\n"
			+ "                \"columns\": [{\r\n"
			+ "                    \"_elementid\": \"$_id.dropZone.columns._elementid\",\r\n"
			+ "                    \"name\": \"$_id.dropZone.columns.name\",\r\n"
			+ "                    \"title\": \"$_id.dropZone.columns.title\",\r\n"
			+ "                    \"icon\": \"$_id.dropZone.columns.icon\",\r\n"
			+ "                    \"type\": \"$_id.dropZone.columns.type\",\r\n"
			+ "                    \"_id\": \"$_id.dropZone.columns._id\",\r\n"
			+ "                    \"properties\": \"$_id.dropZone.columns.properties\",\r\n"
			+ "                    \"dataBinding\": \"$_id.dropZone.columns.dataBinding\",\r\n"
			+ "                    \"columns\": \"$columns\",\r\n" + "                    }]\r\n"
			+ "                }\r\n" + "            }\r\n" + "        }\r\n" + "    }}\r\n" + "])";

	public static String saveOffer1 = "db.getCollection('catalogs').aggregate([\r\n"
			+ "{$match: {\"_id\": \"{emailId}\"}},\r\n" + "{$unwind: \"$Catalogs\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook\"},\r\n" + "{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "{$match: {\"Catalogs.priceBook.offerPrices.offerCode\": \"{offerCode}\"}},\r\n" + "{$project: {\r\n"
			+ "    \"_id\": 0,\r\n" + "    \"emailId\": \"$_id\",\r\n"
			+ "    \"offerPrice\": \"$Catalogs.priceBook.offerPrices\"\r\n" + "    }}\r\n" + "])";

	public static String saveOffer = "db.getCollection('catalogs').aggregate([\r\n"
			+ "{$match: {\"_id\": \"{emailId}\"}}, \r\n" + "{$unwind: \"$Catalogs\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook\"},\r\n" + "{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "{$match: {\"Catalogs.priceBook.offerPrices.offerCode\": \"{offerCode}\"}},\r\n" + "{$project: {\r\n"
			+ "            \"_id\": 0,\r\n"
			+ "            \"_id\": {$concat: [\"$Catalogs.priceBook.offerPrices.offerCode\", \"_\", \"$_id\"]},\r\n"
			+ "            \"emailId\": \"$_id\",\r\n" + "            \"customerDetails\": \"$customerDetails\",\r\n"
			+ "            \"offerPrice\": \"$Catalogs.priceBook.offerPrices\",\r\n"
			+ "            \"error\": \"$error\",\r\n" + "            \"relatedParty\": [\"$relatedParty\"]\r\n"
			+ "            }}\r\n" + "	])";
	
	public static String saveOffer2="db.getCollection('catalogs1').aggregate([\r\n" + 
			"{$match: {\"_id\": \"{emailId}\"}}, \r\n" + 
			"{$unwind: \"$Catalogs\"},\r\n" + 
			"{$unwind: \"$Catalogs.ProductOffers\"},\r\n" + 
			"{$match: {\"Catalogs.ProductOffers.offerCode\": \"{offerCode}\"}},\r\n" + 
			"{$project: {\r\n" + 
			"            \"_id\": 0,\r\n" + 
			"            \"_id\": {$concat: [\"$Catalogs.ProductOffers.offerCode\", \"_\", \"$_id\"]},\r\n" + 
			"            \"emailId\": \"$_id\",\r\n" + 
			"            \"customerDetails\": \"$customerDetails\",\r\n" + 
			"            \"offerPrice\": \"$Catalogs.ProductOffers\",\r\n" + 
			"            \"error\": \"$error\",\r\n" + 
			"            \"relatedParty\": [\"$relatedParty\"]\r\n" + 
			"            }}\r\n" + 
			"	])\r\n" + 
			"";

	public static String saveOfferOld = "db.getCollection('catalogs').aggregate([\r\n"
			+ "			{$match: {\"_id\": \"{emailId}\"}},\r\n" + "			{$unwind: \"$Catalogs\"},\r\n"
			+ "			{$unwind: \"$Catalogs.priceBook\"},\r\n"
			+ "			{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "			{$match: {\"Catalogs.priceBook.offerPrices.offerCode\": \"{offerCode}\"}},\r\n"
			+ "			{$project: {\r\n" + "			    \"_id\": 0,\r\n"
			+ "                            \"_id\": {$concat: [\"$Catalogs.priceBook.offerPrices.offerCode\", \"_\", \"$_id\"]},\r\n"
			+ "			    \"emailId\": \"$_id\",\r\n"
			+ "			    \"offerPrice\": \"$Catalogs.priceBook.offerPrices\"\r\n" + "			    }}\r\n"
			+ "			])";

	public static String getProductDynamicScreen = "db.getCollection('customerCatalogs').aggregate([\r\n"
			+ "{$match: {\"emailId\" : \"{emailId}\", \"offerPrice.offerCode\": {$in: {selectedOfferCodes}}}},\r\n"
			+ "{$unwind: \"$offerPrice.products\"},\r\n"
			+ "{$match: {\"offerPrice.products.isActiveProduct\": true}},\r\n"
			+ "{$unwind: \"$offerPrice.products.productspecifications\"},\r\n"
			+ "{$unwind: \"$offerPrice.products.productspecifications.productspecattributevalue\"},\r\n"
			+ "{$match: {\"offerPrice.products.productspecifications.productspecattributevalue.isVisible\": true}},\r\n"
			+ "{$sort: {\"offerPrice.products.productspecifications.productspecattributevalue.sequence\": 1}},\r\n"
			+ "{$project: {\r\n" + "    \"_id\": 0,\r\n"
			+ "    \"allName\": { \"offerName\": \"$offerPrice.offerName\", \"productName\": \"$offerPrice.products.productName\", \"unique\": \"$offerPrice.products.unique\" },    \"labelValue\": \"$offerPrice.products.productspecifications.productspecattributevalue\",\r\n"
			+ "    \"error\": \"$error\",\r\n" + "    \"labelId\": \"string_false\",\r\n"
			+ "    \"divPanel\": \"div_panel\" }},\r\n" + "{$lookup: {\r\n" + "    \"from\": \"dynamicElements\",\r\n"
			+ "    \"localField\": \"labelId\",\r\n" + "    \"foreignField\": \"_id\",\r\n"
			+ "    \"as\": \"labelElement\" }},\r\n" + "{$unwind: \"$labelElement\"},\r\n" + "{$lookup: {\r\n"
			+ "    \"from\": \"dynamicElements\",\r\n" + "    \"localField\": \"divPanel\",\r\n"
			+ "    \"foreignField\": \"_id\",\r\n" + "    \"as\": \"divElement\" }},\r\n"
			+ "{$unwind: \"$divElement\"},\r\n" + "{$addFields: {\r\n"
			+ "    \"labelElement.prop.sequence\": \"$labelValue.sequence\",\r\n"
			+ "    \"labelElement.prop.dataBinding.beanClass\": \"productspecattributevalue\",\r\n"
			+ "    \"labelElement.prop.dataBinding.defaultValue\": \"$labelValue.productSpecAttributeValue\",\r\n"
			+ "    \"labelElement.prop.dataBinding.beanId\": \"$labelValue.productSpecBindAttributeName\",\r\n"
			+ "    \"labelElement.prop.properties.label\": \"$labelValue.productSpecAttributeName\",\r\n"
			+ "    \"divElement.prop.dataBinding.beanClass\": \"productspecattributevalue\",\r\n"
			+ "    \"divElement.prop.properties.displayName\": \"$allName.productName\",\r\n"
			+ "    \"divElement.prop.properties.unique\": \"$allName.unique\",    \"divElementRoot\": \"$divElement.prop\" }},\r\n"
			+ "{$group: {\r\n"
			+ "    \"_id\": {\"error\": \"$error\", \"offerName\": \"$allName.offerName\", \"productPanel\": \"$divElement.prop\", \"offerPanel\": \"$divElementRoot\", \"topPanel\": \"$divElementRoot\"},\r\n"
			+ "    \"columnsLabel\": {$push: \"$labelElement.prop\" }}},\r\n"
			+ "{$addFields: { \"_id.productPanel.columns\": \"$columnsLabel\", \"_id.offerPanel.properties.displayName\": \"$_id.offerName\" }},\r\n"
			+ "{$group: {\r\n"
			+ "    \"_id\": {\"error\": \"$_id.error\", \"offerName\": \"$_id.offerName\", \"offerPanel\": \"$_id.offerPanel\", \"topPanel\": \"$_id.topPanel\"},\r\n"
			+ "    \"productColumns\": {$push: \"$_id.productPanel\" }}},\r\n" + "{$addFields: { \r\n"
			+ "    \"_id.offerPanel.properties.elementContainerStyleClass\": \"\",\r\n"
			+ "    \"_id.offerPanel.properties.class\": \"col-lg-12 col-md-12 col-sm-12 col-xs-12\",\r\n"
			+ "    \"_id.offerPanel.columns\": \"$productColumns\",\r\n"
			+ "    \"_id.topPanel.properties.elementContainerStyleClass\": \"\",\r\n"
			+ "    \"_id.topPanel.properties.class\": \"col-lg-12 col-md-12 col-sm-12 col-xs-12\" }},\r\n"
			+ "{$group: { \"_id\": {\"error\": \"$_id.error\", \"topPanel\": \"$_id.topPanel\"}, \"offerColumns\": {$push: \"$_id.offerPanel\"}}},\r\n"
			+ "{$addFields: { \"_id.topPanel.columns\": \"$offerColumns\" }},\r\n"
			+ "{$project: { \"_id\": \"$_id.error\", \"result\": { \"ScreenJson\": { \"dropZone\": \"$_id.topPanel\" }}}}])";

	public static String getProductDynamicScreenOld = "db.getCollection('customerCatalogs').aggregate([\r\n"
			+ "{$match: {\"emailId\" : \"{emailId}\", \"offerPrice.offerCode\": {$in: {selectedOfferCodes}}}},\r\n"
			+ "{$unwind: \"$offerPrice.products\"},\r\n"
			+ "{$unwind: \"$offerPrice.products.productspecifications\"},\r\n"
			+ "{$unwind: \"$offerPrice.products.productspecifications.productspecattributevalue\"},\r\n"
			+ "{$match: {\"offerPrice.products.isActiveProduct\": true}},\r\n"
			+ "{$match: {\"offerPrice.products.productspecifications.productspecattributevalue.isVisible\": true}},\r\n"
			+ "{$sort: {\"offerPrice.products.productspecifications.productspecattributevalue.sequence\": 1}},\r\n"
			+ "{$project: {\r\n" + "    \"_id\": 0,\r\n" +
			// " \"allName\": { \"offerName\": \"$offerPrice.offerName\", \"productName\":
			// \"$offerPrice.products.productName\" },\r\n" +
			"    \"allName\": { \"offerName\": \"$offerPrice.offerName\", \"productName\": \"$offerPrice.products.productName\", \"unique\": \"$offerPrice.products.unique\" },"
			+ "    \"labelValue\": \"$offerPrice.products.productspecifications.productspecattributevalue\",\r\n"
			+ "    \"labelId\": \"string_false\",\r\n" + "    \"divPanel\": \"div_panel\" }},\r\n" + "{$lookup: {\r\n"
			+ "    \"from\": \"dynamicElements\",\r\n" + "    \"localField\": \"labelId\",\r\n"
			+ "    \"foreignField\": \"_id\",\r\n" + "    \"as\": \"labelElement\" }},\r\n"
			+ "{$unwind: \"$labelElement\"},\r\n" + "{$lookup: {\r\n" + "    \"from\": \"dynamicElements\",\r\n"
			+ "    \"localField\": \"divPanel\",\r\n" + "    \"foreignField\": \"_id\",\r\n"
			+ "    \"as\": \"divElement\" }},\r\n" + "{$unwind: \"$divElement\"},\r\n" + "{$addFields: {\r\n"
			+ "    \"labelElement.prop.sequence\": \"$labelValue.sequence\",\r\n"
			+ "    \"labelElement.prop.dataBinding.beanClass\": \"productspecattributevalue\",\r\n"
			+ "    \"labelElement.prop.dataBinding.defaultValue\": \"$labelValue.productSpecAttributeValue\",\r\n"
			+ "    \"labelElement.prop.dataBinding.beanId\": \"$labelValue.productSpecBindAttributeName\",\r\n"
			+ "    \"labelElement.prop.properties.label\": \"$labelValue.productSpecAttributeName\",\r\n"
			+ "    \"divElement.prop.dataBinding.beanClass\": \"productspecattributevalue\",\r\n"
			+ "    \"divElement.prop.properties.displayName\": \"$allName.productName\",\r\n"
			+ "    \"divElement.prop.properties.unique\": \"$allName.unique\","
			+ "    \"divElementRoot\": \"$divElement.prop\" }},\r\n" + "{$group: {\r\n"
			+ "    \"_id\": {\"offerName\": \"$allName.offerName\", \"productPanel\": \"$divElement.prop\", \"offerPanel\": \"$divElementRoot\", \"topPanel\": \"$divElementRoot\"},\r\n"
			+ "    \"columnsLabel\": {$push: \"$labelElement.prop\" }}},\r\n"
			+ "{$addFields: { \"_id.productPanel.columns\": \"$columnsLabel\", \"_id.offerPanel.properties.displayName\": \"$_id.offerName\" }},\r\n"
			+ "{$group: {\r\n"
			+ "    \"_id\": {\"offerName\": \"$_id.offerName\", \"offerPanel\": \"$_id.offerPanel\", \"topPanel\": \"$_id.topPanel\"},\r\n"
			+ "    \"productColumns\": {$push: \"$_id.productPanel\" }}},\r\n" + "{$addFields: { \r\n"
			+ "    \"_id.offerPanel.properties.elementContainerStyleClass\": \"\",\r\n"
			+ "    \"_id.offerPanel.properties.class\": \"col-lg-12 col-md-12 col-sm-12 col-xs-12\",\r\n"
			+ "    \"_id.offerPanel.columns\": \"$productColumns\",\r\n"
			+ "    \"_id.topPanel.properties.elementContainerStyleClass\": \"\",\r\n"
			+ "    \"_id.topPanel.properties.class\": \"col-lg-12 col-md-12 col-sm-12 col-xs-12\" }},\r\n"
			+ "{$group: { \"_id\": {\"topPanel\": \"$_id.topPanel\"}, \"offerColumns\": {$push: \"$_id.offerPanel\"}}},\r\n"
			+ "{$addFields: { \"_id.topPanel.columns\": \"$offerColumns\" }},\r\n"
			+ "{$project: { \"_id\": 0, \"result\": { \"ScreenJson\": { \"dropZone\": \"$_id.topPanel\" }}}}])";

	public static String customerDetails = "db.getCollection('customerCatalogs').aggregate([\r\n"
			+ "{$match: { \"emailId\": \"{emailId}\", \"offerPrice.offerCode\": {$in: {offerCode}}}},\r\n"
			+ "{$unwind: \"$offerPrice.products\" },\r\n" + "{$unwind: \"$offerPrice.products.prices\" },\r\n"
			+ "{$project: {\r\n"
			+ "    \"offer\": {\"offerName\": \"$offerPrice.offerName\", \"term\": \"$offerPrice.productOfferingTerm.name\", \"cd\": \"$customerDetails\"},\r\n"
			+ "    \"oneTime\": {$convert: {input: \"$offerPrice.products.prices.onetimeCharge.price\", \"to\": \"int\"}},\r\n"
			+ "    \"recurring\": {$convert: {input: \"$offerPrice.products.prices.recurringCharge.price\", \"to\": \"int\"}},\r\n"
			+ "    \"usage\": {$ifNull: [{$convert: {input: \"$offerPrice.products.prices.usage\", \"to\": \"int\"}}, {$convert: {input: \"0\", \"to\": \"int\"}}]} \r\n"
			+ "    }},\r\n" + "{ $group: {\r\n" + "    \"_id\": \"$offer\",\r\n"
			+ "    \"totalOneTime\": {$sum: \"$oneTime\"},\r\n" + "    \"totalRecurring\": {$sum: \"$recurring\"},\r\n"
			+ "    \"totalUsage\": {$sum: \"$usage\"}\r\n" + "    }},\r\n" + "{$project: {\r\n" + "    \"data\": {\r\n"
			+ "        \"offerName\": \"$_id.offerName\",\r\n" + "        \"term\": \"$_id.term\",\r\n"
			+ "        \"oneTimePrice\": \"$totalOneTime\",\r\n"
			+ "        \"recurringPrice\": \"$totalRecurring\",\r\n" + "        \"usagePrice\": \"$totalUsage\",\r\n"
			+ "        \"payNow\": \"$totalOneTime\",\r\n"
			+ "        \"totalMonthly\": {$add: [\"$totalUsage\", \"$totalRecurring\"]}        \r\n" + "        },\r\n"
			+ "    \"data1\": {\r\n" + "        \"offerName\": \"$_id.offerName\",\r\n"
			+ "        \"term\": \"$_id.term\",\r\n"
			+ "        \"oneTimePrice\": {$concat: [{$toString: \"$totalOneTime\"}, \"€\"]},\r\n"
			+ "        \"recurringPrice\": {$concat: [{$toString: \"$totalRecurring\"}, \"€\"]},\r\n"
			+ "        \"usagePrice\": {$concat: [{$toString: \"$totalUsage\"}, \"€\"]},\r\n"
			+ "        \"payNow\": {$concat: [{$toString: \"$totalOneTime\"}, \"€\"]},\r\n"
			+ "        \"totalMonthly\": {$concat: [{$toString: {$add: [\"$totalUsage\", \"$totalRecurring\"]}}, \"€\"]}        \r\n"
			+ "        }}},\r\n"
			+ "{$group: {\"_id\": \"$_id.cd\",\"dataList\": {$push: \"$data1\"},\"total\": {$sum: \"$data.totalMonthly\"},\"totalPayNow\": {$sum: \"$data.payNow\"}}},\r\n"
			+ "{$unwind: {\"path\": \"$dataList\", \"includeArrayIndex\": \"i\"}},\r\n"
			+ "{$addFields: { \"dataList.srNo\": {$add: [\"$i\", 1]} }},\r\n"
			+ "{$group: { \"_id\": {\"cd\": \"$_id\", \"total\": \"$total\", \"totalPayNow\": \"$totalPayNow\"}, \"dataList\": {$push: \"$dataList\"}}},\r\n"
			+ "{$project: {\r\n" + "    \"_id\": 0,\r\n" + "    \"result\": {\r\n" + "        \"billingDetails\": {\r\n"
			+ "            \"dataList\": \"$dataList\",\r\n"
			+ "            \"displayfields\": [\"Product/Offer\", \"Term\", \"Recurring Charges\", \"One Time Charges\", \"Usage Charges\", \"Total Monthly\", \"Pay Now\", \"Sr No.\"]},\r\n"
			+ "        \"TotalDetails\": {\"total\": {$concat: [{$toString: \"$_id.total\"}, \"€\"]}, \"totalPayNow\": {$concat: [{$toString: \"$_id.totalPayNow\"}, \"€\"]}},\r\n"
			+ "        \"customerDetails\": {\"dataList\": [\"$_id.cd\"]}\r\n" + "        }\r\n" + "    }}\r\n" + "])";

	public static String customerDetailsOldd = "db.getCollection('customerCatalogs').aggregate([\r\n"
			+ "{ $match: { \"emailId\": \"{emailId}\", \"offerPrice.offerCode\": {$in: {offerCode}} }},\r\n"
			+ "{ $unwind: \"$offerPrice.products\" },\r\n" + "{ $unwind: \"$offerPrice.products.prices\" },\r\n"
			+ "{ $project: {\r\n"
			+ "    \"offer\": {\"offerName\": \"$offerPrice.offerName\", \"term\": \"$offerPrice.productOfferingTerm.name\", \"cd\": \"$customerDetails\"},\r\n"
			+ "    \"oneTime\": {$convert: {input: \"$offerPrice.products.prices.onetimeCharge.price\", \"to\": \"int\"}},\r\n"
			+ "    \"recurring\": {$convert: {input: \"$offerPrice.products.prices.recurringCharge.price\", \"to\": \"int\"}},\r\n"
			+ "    \"usage\": {$ifNull: [{$convert: {input: \"$offerPrice.products.prices.usage\", \"to\": \"int\"}}, {$convert: {input: \"0\", \"to\": \"int\"}}]} \r\n"
			+ "    }},\r\n" + "{ $group: {\r\n" + "    \"_id\": \"$offer\",\r\n"
			+ "    \"totalOneTime\": {$sum: \"$oneTime\"},\r\n" + "    \"totalRecurring\": {$sum: \"$recurring\"},\r\n"
			+ "    \"totalUsage\": {$sum: \"$usage\"}\r\n" + "    }},\r\n" + "{$project: {\r\n" + "    \"data\": {\r\n"
			+ "        \"offerName\": \"$_id.offerName\",\r\n" + "        \"term\": \"$_id.term\",\r\n"
			+ "        \"oneTimePrice\": \"$totalOneTime\",\r\n"
			+ "        \"recurringPrice\": \"$totalRecurring\",\r\n" + "        \"usagePrice\": \"$totalUsage\",\r\n"
			+ "        \"payNow\": \"$totalOneTime\",\r\n"
			+ "        \"totalMonthly\": {$add: [\"$totalUsage\", \"$totalRecurring\"]}        \r\n" + "        }\r\n"
			+ "    }},\r\n" + "{$group: {\r\n" + "    \"_id\": \"$_id.cd\",\r\n"
			+ "    \"dataList\": {$push: \"$data\"},\r\n" + "    \"total\": {$sum: \"$data.totalMonthly\"},\r\n"
			+ "    \"totalPayNow\": {$sum: \"$data.payNow\"}\r\n" + "    }},\r\n"
			+ "{ $unwind: {\"path\": \"$dataList\", \"includeArrayIndex\": \"i\"}},\r\n" + "{$addFields: {\r\n"
			+ "    \"dataList.srNo\": {$add: [\"$i\", 1]}\r\n" + "    }},\r\n" + "{$group: {\r\n"
			+ "    \"_id\": {\"cd\": \"$_id\", \"total\": \"$total\", \"totalPayNow\": \"$totalPayNow\"},\r\n"
			+ "    \"dataList\": {$push: \"$dataList\"}\r\n" + "    }},\r\n" + "{$project: {\r\n"
			+ "    \"_id\": 0,\r\n" + "    \"result\": {\r\n" + "        \"billingDetails\": {\r\n"
			+ "            \"dataList\": \"$dataList\",\r\n"
			+ "            \"displayfields\": [\"Product/Offer\", \"Term\", \"Recurring Charges\", \"One Time Charges\", \"Usage Charges\", \"Total Monthly\", \"Pay Now\", \"Sr No.\"]\r\n"
			+ "            },\r\n" + "        \"TotalDetails\": {\r\n" + "            \"total\": \"$_id.total\",\r\n"
			+ "            \"totalPayNow\": \"$_id.totalPayNow\"\r\n" + "            },\r\n"
			+ "        \"customerDetails\": {\"dataList\": [\"$_id.cd\"]}\r\n" + "        }\r\n" + "    }}\r\n" + "])";

	public static String customerDetailsOld = "db.getCollection('customerCatalogs').aggregate([\r\n"
			+ "{ $match: { \"emailId\": \"{emailId}\", \"offerPrice.offerCode\": {$in: {offerCode}} }},\r\n"
			+ "{ $unwind: \"$offerPrice.products\" },\r\n" + "{ $unwind: \"$offerPrice.products.prices\" },\r\n"
			+ "{ $project: {\r\n"
			+ "    \"offer\": {\"offerName\": \"$offerPrice.offerName\", \"term\": \"$offerPrice.productOfferingTerm.name\", \"cd\": \"$customerDetails\"},\r\n"
			+ "    \"oneTime\": {$convert: {input: \"$offerPrice.products.prices.onetimeCharge.price\", \"to\": \"int\"}},\r\n"
			+ "    \"recurring\": {$convert: {input: \"$offerPrice.products.prices.recurringCharge.price\", \"to\": \"int\"}},\r\n"
			+ "    \"usage\": {$ifNull: [{$convert: {input: \"$offerPrice.products.prices.usage\", \"to\": \"int\"}}, {$convert: {input: \"0\", \"to\": \"int\"}}]} \r\n"
			+ "    }},\r\n" + "{ $group: {\r\n" + "    \"_id\": \"$offer\",\r\n"
			+ "    \"totalOneTime\": {$sum: \"$oneTime\"},\r\n" + "    \"totalRecurring\": {$sum: \"$recurring\"},\r\n"
			+ "    \"totalUsage\": {$sum: \"$usage\"}\r\n" + "    }},\r\n" + "{$project: {\r\n" + "    \"data\": {\r\n"
			+ "        \"offerName\": \"$_id.offerName\",\r\n" + "        \"term\": \"$_id.term\",\r\n"
			+ "        \"oneTimePrice\": \"$totalOneTime\",\r\n"
			+ "        \"recurringPrice\": \"$totalRecurring\",\r\n" + "        \"usagePrice\": \"$totalUsage\",\r\n"
			+ "        \"payNow\": \"$totalOneTime\",\r\n"
			+ "        \"totalMonthly\": {$add: [\"$totalUsage\", \"$totalRecurring\"]}        \r\n" + "        }\r\n"
			+ "    }},\r\n" + "{$group: {\r\n" + "    \"_id\": \"$_id.cd\",\r\n"
			+ "    \"dataList\": {$push: \"$data\"},\r\n" + "    \"total\": {$sum: \"$data.totalMonthly\"},\r\n"
			+ "    \"totalPayNow\": {$sum: \"$data.payNow\"}\r\n" + "    }},\r\n" + "{$project: {\r\n"
			+ "    \"_id\": 0,\r\n" + "    \"result\": {\r\n" + "        \"billingDetails\": {\r\n"
			+ "            \"dataList\": \"$dataList\",\r\n"
			+ "            \"displayfields\": [\"Product/Offer\", \"Term\", \"Recurring Charges\", \"One Time Charges\", \"Usage Charges\", \"Total Monthly\", \"Pay Now\", \"Sr No.\"]\r\n"
			+ "            },\r\n" + "        \"TotalDetails\": {\r\n" + "            \"total\": \"$total\",\r\n"
			+ "            \"totalPayNow\": \"$totalPayNow\"\r\n" + "            },\r\n"
			+ "        \"customerDetails\": {\"dataList\": [\"$_id\"]}\r\n" + "        }\r\n" + "    }}\r\n" + "])";

	public static String placeOrderNew = "db.getCollection('customerCatalogs').aggregate([\r\n" + 
			"{ $match: { \"emailId\": \"{emailId}\", \"offerPrice.offerCode\": {$in: {offerCode}}}},\r\n" + 
			"{ $unwind: \"$offerPrice.products\" },\r\n" + 
			"{ $match: { \"offerPrice.products.isActiveProduct\": true}},\r\n" + 
			"{ $unwind: \"$offerPrice.products.productspecifications\" },\r\n" + 
			"{ $unwind: \"$offerPrice.products.prices\" },\r\n" + 
			"{ $unwind: \"$offerPrice.products.productspecifications.productspecattributevalue\" },\r\n" + 
			"{ $sort: { \"offerPrice.offerSort\": 1, \"offerPrice.products.productspecifications.productspecattributevalue.sequence\": 1}},\r\n" + 
			"{ $project: {\r\n" + 
			"    \"_id\": 0,\r\n" + 
			"    \"emailId\": \"$emailId\",\r\n" + 
			"    \"productType\": \"$offerPrice.products.productType\",\r\n" + 
			"    \"priceIdDetails\": { \"priceId\": {$ifNull: [\"$offerPrice.priceId\", \"\"]},\r\n" + 
			"        \"note\": { \"text\": \"$offerPrice.offerCode\", \"date\": new Date(), \"author\": { $arrayElemAt: [ {$map: {input: \"$relatedParty\", as: \"user\", in: \"$$user.name\"}}, 0 ] } }\r\n" + 
			"        },\r\n" + 
			"    \"orderAction\": {$ifNull: [\"$offerPrice.products.orderAction\", \"add\"]},    \"relatedParty\": \"$relatedParty\",\r\n" + 
			"    \"channel\": [{\r\n" + 
			"        \"id\": \"CSC\",\r\n" + 
			"        \"name\": \"CSC\",\r\n" + 
			"        \"role\": \"CSC\",\r\n" + 
			"        \"@type\": \"CSC\" }],\r\n" + 
			"    \"productOfferingRoot\": {\r\n" + 
			"        \"id\": \"$offerPrice.offerCode\",\r\n" + 
			"        \"@referredType\": \"$offerPrice.offerType\",\r\n" + 
			"        \"name\": \"$offerPrice.offerName\"},\r\n" + 
			"    \"prod\": {\r\n" + 
			"        \"id\": \"$offerPrice.products.productCode\",\r\n" + 
			"        \"name\": \"$offerPrice.products.productName\",\r\n" + 
			"        \"relation\": \"$offerPrice.products.productRelationship\",\r\n" + 
			"        \"unique\": \"$offerPrice.products.unique\"},\r\n" + 
			"    \"productSpecification\": {\r\n" + 
			"        \"href\": \"\",\r\n" + 
			"        \"version\": \"\"},\r\n" + 
			"    \"characteristic\": {\r\n" + 
			"        \"name\": \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n" + 
			"        \"value\": \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n" + 
			"        \"isVisible\": \"$offerPrice.products.productspecifications.productspecattributevalue.isVisible\",\r\n" + 
			"        \"_id\": \"$offerPrice.products.productspecifications.productspecattributevalue.productAttributeProductAttributeCode\",\r\n" + 
			"        \"displayAttributeName\": \"$offerPrice.products.productspecifications.productspecattributevalue.displayAttributeName\",\r\n" + 
			"        \"unitOfMeasurement\": \"$offerPrice.products.productspecifications.productspecattributevalue.unitOfMeasurement\",\r\n" + 
			"        \"@type\": \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValueType\"},\r\n" + 
			"    \"productSpecification_id\": {$cond: { if: { $eq: [ { $toLower: \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeName\" }, \"cfs_specification_id\" ] }, then: \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValue\", else: null }},\r\n" + 
			"    \"productSpecification_name\": {$cond: { if: { $eq: [ { $toLower: \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeName\" }, \"cfs_name\" ] }, then: \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValue\", else: null }},\r\n" + 
			"    \"itemTerm\": [\"$offerPrice.productOfferingTerm\"],\r\n" + 
			"    \"itemPrice\": [{\r\n" + 
			"        \"name\": \"oneTime\",\r\n" + 
			"        \"description\": \"oneTime\",\r\n" + 
			"        \"priceType\": \"oneTime\",\r\n" + 
			"        \"price\": {\r\n" + 
			"                \"taxIncludedAmount\": {\r\n" + 
			"                    \"value\": { $toDouble: \"$offerPrice.products.prices.onetimeCharge.price\" },\r\n" + 
			"                    \"unit\": \"Euro\"\r\n" + 
			"                    },\r\n" + 
			"                \"taxRate\": 0\r\n" + 
			"        }},\r\n" + 
			"    {\r\n" + 
			"        \"priceType\": \"recuring\",\r\n" + 
			"        \"description\": \"recuring\",\r\n" + 
			"        \"recurringChargePeriod\": \"$offerPrice.products.prices.recurringCharge.frequency\",\r\n" + 
			"        \"price\": {\r\n" + 
			"            \"taxIncludedAmount\": {\r\n" + 
			"                    \"value\": { $toDouble: \"$offerPrice.products.prices.recurringCharge.price\" },\r\n" + 
			"                    \"unit\": \"Euro\"\r\n" + 
			"                },\r\n" + 
			"            \"taxRate\" : 0.0\r\n" + 
			"            }\r\n" + 
			"    }]}},\r\n" + 
			"{ $group: {\r\n" + 
			"    \"_id\": {\r\n" + 
			"        \"emailId\": \"$emailId\",\r\n" + 
			"        \"productType\": \"$productType\",\r\n" + 
			"        \"productOfferingRoot\": \"$productOfferingRoot\",\r\n" + 
			"        \"productOffering\": \"$productOfferingRoot\",\r\n" + 
			"        \"prod\": \"$prod\",\r\n" + 
			"        \"productSpecification\": \"$productSpecification\",\r\n" + 
			"        \"itemPrice\": \"$itemPrice\",\r\n" + 
			"        \"relatedParty\": \"$relatedParty\",\r\n" + 
			"        \"orderAction\": \"$orderAction\",        \"channel\": \"$channel\",\r\n" + 
			"        \"itemTerm\": \"$itemTerm\",\r\n" + 
			"        \"priceIdDetails\": \"$priceIdDetails\"\r\n" + 
			"        },\r\n" + 
			"    \"characteristic\": {$push: \"$characteristic\"},\r\n" + 
			"    \"productSpecification_ids\": {$push: \"$productSpecification_id\"},\r\n" + 
			"    \"productSpecification_names\": {$push: \"$productSpecification_name\"}\r\n" + 
			"    }},\r\n" + 
			"{$addFields: {\r\n" + 
			"    \"_id.productOfferingRoot.priceIdDetails\": \"$_id.priceIdDetails\",\r\n" + 
			"    \"_id.productSpecification.id\": {$cond: {\r\n" + 
			"        if: {$eq: [{$size: {$filter: {input: \"$productSpecification_ids\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]},\r\n" + 
			"        then: \"\",\r\n" + 
			"        else:  {$arrayElemAt: [{$filter: {input: \"$productSpecification_ids\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}, 0]}   \r\n" + 
			"        }},\r\n" + 
			"    \"_id.productSpecification.name\": {$cond: {\r\n" + 
			"        if: {$eq: [{$size: {$filter: {input: \"$productSpecification_names\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]},\r\n" + 
			"        then: \"\",\r\n" + 
			"        else:  {$arrayElemAt: [{$filter: {input: \"$productSpecification_names\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}, 0]}   \r\n" + 
			"        }}\r\n" + 
			"    }},\r\n" + 
			"{ $project: {\r\n" + 
			"    \"orderItem\": {\r\n" + 
			"        \"action\": \"$_id.orderAction\",\r\n" + 
			"        \"quantity\": { $convert: { input: \"1\", to: \"int\" } },\r\n" + 
			"        \"state\": \"Acknowledged\",\r\n" + 
			"        \"itemPrice\": \"$_id.itemPrice\",\r\n" + 
			"        \"productOffering\": \"$_id.productOffering\",\r\n" + 
			"        \"product\": {\r\n" + 
			"            \"id\": \"$_id.prod.id\",\r\n" + 
			"            \"name\": \"$_id.prod.name\",\r\n" + 
			"            \"productRelationship\": \"$_id.prod.relation\",\r\n" + 
			"            \"productType\": \"$_id.productType\",\r\n" + 
			"            \"characteristic\": \"$characteristic\",\r\n" + 
			"            \"relatedParty\": \"$_id.relatedParty\",\r\n" + 
			"            \"productSpecification\": \"$_id.productSpecification\"\r\n" + 
			"            },\r\n" + 
			"        \"itemTerm\": \"$_id.itemTerm\",\r\n" + 
			"            \"payment\": \"$_id.payment\"\r\n" + 
			"        },\r\n" + 
			"    \"relatedParty\": \"$_id.relatedParty\",\r\n" + 
			"    \"channel\": \"$_id.channel\"\r\n" + 
			"    }},    \r\n" + 
			"{$group: {\r\n" + 
			"    \"_id\": {\r\n" + 
			"        \"emailId\": \"$_id.emailId\",\r\n" + 
			"        \"relatedParty\": \"$relatedParty\",\r\n" + 
			"        \"channel\": \"$channel\",\r\n" + 
			"        \"productOfferingRoot\": \"$_id.productOfferingRoot\"\r\n" + 
			"        },\r\n" + 
			"    \"orderItemNest\": {$push: \"$orderItem\"}\r\n" + 
			"    }},\r\n" + 
			"{$project: {\r\n" + 
			"    \"_id\": 0,\r\n" + 
			"    \"emailId\": \"$_id.emailId\",\r\n" + 
			"    \"priceIdDetails\": \"$_id.productOfferingRoot.priceIdDetails\",\r\n" + 
			"    \"orderItemRoot\": {\r\n" + 
			"        \"action\": \"add\",\r\n" + 
			"        \"quantity\": { $convert: { input: \"1\", to: \"int\" } },\r\n" + 
			"        \"state\": \"Acknowledged\",\r\n" + 
			"        \"orderItem\": \"$orderItemNest\",\r\n" + 
			"        \"productOffering\": \"$_id.productOfferingRoot\"\r\n" + 
			"        },\r\n" + 
			"    \"relatedParty\": \"$_id.relatedParty\",\r\n" + 
			"    \"channel\": \"$_id.channel\",\r\n" + 
			"    }},\r\n" + 
			"{$addFields: { \"orderItemRoot.productOffering.priceIdDetails\": null }},\r\n" + 
			"{$group: {\r\n" + 
			"    \"_id\": {\r\n" + 
			"        \"emailId\": \"$emailId\",\r\n" + 
			"        \"relatedParty\": \"$relatedParty\",\r\n" + 
			"        \"channel\": \"$channel\"\r\n" + 
			"        },\r\n" + 
			"    \"orderItem\": {$push: \"$orderItemRoot\"},\r\n" + 
			"    \"priceIdDetails\": {$push: \"$priceIdDetails\"}\r\n" + 
			"    }},\r\n" + 
			"{$project: {\r\n" + 
			"    \"_id\": 0,\r\n" + 
			"    \"priority\": \"1\",\r\n" + 
			"    \"category\": \"\",\r\n" + 
			"    \"state\": \"Acknowledged\",\r\n" + 
			"    \"notificationContact\": \"$_id.emailId\",\r\n" + 
			"    \"@baseType\": \"productOrder\",\r\n" + 
			"    \"@type\": \"New Order\",\r\n" + 
			"    \"orderItem\": \"$orderItem\",\r\n" + 
			"    \"relatedParty\": \"$_id.relatedParty\",\r\n" + 
			"    \"channel\": \"$_id.channel\",\r\n" + 
			"    \"description\": \"string\",\r\n" + 
			"    \"note\": {$filter: {input: {$map: {input: \"$priceIdDetails\", as: \"n\", in: {$cond: {if: {$eq: [{$size: {$split: [\"$$n.priceId\", \"|\"]}}, 2]}, then: \"$$n.note\", else: null}}}}, as: \"n\", cond: {$ne: [\"$$n\", null]}}}\r\n" + 
			"    }}])";
	
	public static String placeOrder = "db.getCollection('customerCatalogs').aggregate([\r\n"
			+ "{ $match: { \"emailId\": \"{emailId}\", \"offerPrice.offerCode\": {$in: {offerCode}}}},\r\n"
			+ "{ $unwind: \"$offerPrice.products\" },\r\n"
			+ "{ $match: { \"offerPrice.products.isActiveProduct\": true}},\r\n"
			+ "{ $unwind: \"$offerPrice.products.productspecifications\" },\r\n"
			+ "{ $unwind: \"$offerPrice.products.prices\" },\r\n"
			+ "{ $unwind: \"$offerPrice.products.productspecifications.productspecattributevalue\" },\r\n"
			+ "{ $sort: { \"offerPrice.offerSort\": 1, \"offerPrice.products.productspecifications.productspecattributevalue.sequence\": 1}},\r\n"
			+ "{ $project: {\r\n" + "    \"_id\": 0,\r\n" + "    \"emailId\": \"$emailId\",\r\n"
			+ "    \"productType\": \"$offerPrice.products.productType\",\r\n"
			+ "    \"orderAction\": {$ifNull: [\"$offerPrice.products.orderAction\", \"add\"]},"
			+ "    \"relatedParty\": \"$relatedParty\",\r\n" + "    \"channel\": [{\r\n"
			+ "        \"id\": \"CSC\",\r\n" + "        \"name\": \"CSC\",\r\n" + "        \"role\": \"CSC\",\r\n"
			+ "        \"@type\": \"CSC\" }],\r\n" 
			+ "    \"productOfferingRoot\": {\r\n"
			+ "        \"id\": \"$offerPrice.offerCode\",\r\n"
			+ "        \"@referredType\": \"$offerPrice.offerType\",\r\n"
			+ "        \"name\": \"$offerPrice.offerName\"},\r\n"
			+ "    \"prod\": {\r\n" + "        \"id\": \"$offerPrice.products.productCode\",\r\n"
			+ "        \"name\": \"$offerPrice.products.productName\",\r\n"
			+ "        \"relation\": \"$offerPrice.products.productRelationship\",\r\n"
			+ "        \"unique\": \"$offerPrice.products.unique\"},\r\n" + "    \"productSpecification\": {\r\n"
			+ "        \"href\": \"\",\r\n" + "        \"version\": \"\"},\r\n" + "    \"characteristic\": {\r\n"
			+ "        \"name\": \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n"
			+ "        \"value\": \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n"
			+ "        \"isVisible\": \"$offerPrice.products.productspecifications.productspecattributevalue.isVisible\",\r\n"
			+ "        \"_id\": \"$offerPrice.products.productspecifications.productspecattributevalue.productAttributeProductAttributeCode\",\r\n"
			+ "        \"displayAttributeName\": \"$offerPrice.products.productspecifications.productspecattributevalue.displayAttributeName\",\r\n"
			+ "        \"unitOfMeasurement\": \"$offerPrice.products.productspecifications.productspecattributevalue.unitOfMeasurement\",\r\n"
			+ "        \"@type\": \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValueType\"},\r\n"
			+ "    \"productSpecification_id\": {$cond: { if: { $eq: [ { $toLower: \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeName\" }, \"cfs_specification_id\" ] }, then: \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValue\", else: null }},\r\n"
			+ "    \"productSpecification_name\": {$cond: { if: { $eq: [ { $toLower: \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeName\" }, \"cfs_name\" ] }, then: \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValue\", else: null }},\r\n"
			+ "    \"itemTerm\": [\"$offerPrice.productOfferingTerm\"],\r\n" + "    \"itemPrice\": [{\r\n"
			+ "        \"name\": \"oneTime\",\r\n" + "        \"description\": \"oneTime\",\r\n"
			+ "        \"priceType\": \"oneTime\",\r\n" + "        \"price\": {\r\n"
			+ "                \"taxIncludedAmount\": {\r\n"
			+ "                    \"value\": { $toDouble: \"$offerPrice.products.prices.onetimeCharge.price\" },\r\n"
			//+ "                    \"value\": { $convert: { input: \"$offerPrice.products.prices.onetimeCharge.price\", to: \"int\" } },\r\n"
			+ "                    \"unit\": \"Euro\"\r\n" + "                    },\r\n"
			+ "                \"taxRate\": 0\r\n" + "        }},\r\n" + "    {\r\n"
			+ "        \"priceType\": \"recuring\",\r\n" + "        \"description\": \"recuring\",\r\n"
			+ "        \"recurringChargePeriod\": \"$offerPrice.products.prices.recurringCharge.frequency\",\r\n"
			+ "        \"price\": {\r\n" + "            \"taxIncludedAmount\": {\r\n"
			+ "                    \"value\": { $toDouble: \"$offerPrice.products.prices.recurringCharge.price\" },\r\n"
			//+ "                    \"value\": { $convert: { input: \"$offerPrice.products.prices.recurringCharge.price\", to: \"int\" } },\r\n"
			+ "                    \"unit\": \"Euro\"\r\n" + "                },\r\n"
			+ "            \"taxRate\" : 0.0\r\n" + "            }\r\n" + "    }]}},\r\n" + "{ $group: {\r\n"
			+ "    \"_id\": {\r\n" + "        \"emailId\": \"$emailId\",\r\n"
			+ "        \"productType\": \"$productType\",\r\n"
			+ "        \"productOfferingRoot\": \"$productOfferingRoot\",\r\n"
			+ "        \"productOffering\": \"$productOfferingRoot\",\r\n" + "        \"prod\": \"$prod\",\r\n"
			+ "        \"productSpecification\": \"$productSpecification\",\r\n"
			+ "        \"itemPrice\": \"$itemPrice\",\r\n" + "        \"relatedParty\": \"$relatedParty\",\r\n"
			+ "        \"orderAction\": \"$orderAction\","
			+ "        \"channel\": \"$channel\",\r\n" + "        \"itemTerm\": \"$itemTerm\"\r\n" + "        },\r\n"
			+ "    \"characteristic\": {$push: \"$characteristic\"},\r\n"
			+ "    \"productSpecification_ids\": {$push: \"$productSpecification_id\"},\r\n"
			+ "    \"productSpecification_names\": {$push: \"$productSpecification_name\"}\r\n" + "    }},\r\n"
			+ "{$addFields: {\r\n" + "    \"_id.productSpecification.id\": {$cond: {\r\n"
			+ "        if: {$eq: [{$size: {$filter: {input: \"$productSpecification_ids\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]},\r\n"
			+ "        then: \"\",\r\n"
			+ "        else:  {$arrayElemAt: [{$filter: {input: \"$productSpecification_ids\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}, 0]}   \r\n"
			+ "        }},\r\n" + "    \"_id.productSpecification.name\": {$cond: {\r\n"
			+ "        if: {$eq: [{$size: {$filter: {input: \"$productSpecification_names\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]},\r\n"
			+ "        then: \"\",\r\n"
			+ "        else:  {$arrayElemAt: [{$filter: {input: \"$productSpecification_names\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}, 0]}   \r\n"
			+ "        }}\r\n" + "    }},\r\n" + "{ $project: {\r\n" + "    \"orderItem\": {\r\n"
			+ "        \"action\": \"$_id.orderAction\",\r\n"
			+ "        \"quantity\": { $convert: { input: \"1\", to: \"int\" } },\r\n"
			+ "        \"state\": \"Acknowledged\",\r\n" + "        \"itemPrice\": \"$_id.itemPrice\",\r\n"
			+ "        \"productOffering\": \"$_id.productOffering\",\r\n" + "        \"product\": {\r\n"
			+ "            \"id\": \"$_id.prod.id\",\r\n" + "            \"name\": \"$_id.prod.name\",\r\n"
			+ "            \"productRelationship\": \"$_id.prod.relation\",\r\n"
			+ "            \"productType\": \"$_id.productType\",\r\n"
			+ "            \"characteristic\": \"$characteristic\",\r\n"
			+ "            \"relatedParty\": \"$_id.relatedParty\",\r\n"
			+ "            \"productSpecification\": \"$_id.productSpecification\"\r\n" + "            },\r\n"
			+ "        \"itemTerm\": \"$_id.itemTerm\",\r\n" + "            \"payment\": \"$_id.payment\"\r\n"
			+ "        },\r\n" + "    \"relatedParty\": \"$_id.relatedParty\",\r\n"
			+ "    \"channel\": \"$_id.channel\"\r\n" + "    }},\r\n" + "{$group: {\r\n" + "    \"_id\": {\r\n"
			+ "        \"emailId\": \"$_id.emailId\",\r\n" + "        \"relatedParty\": \"$relatedParty\",\r\n"
			+ "        \"channel\": \"$channel\",\r\n"
			+ "        \"productOfferingRoot\": \"$_id.productOfferingRoot\"\r\n" + "        },\r\n"
			+ "    \"orderItemNest\": {$push: \"$orderItem\"}\r\n" + "    }},\r\n" + "{$project: {\r\n"
			+ "    \"_id\": 0,\r\n" + "    \"emailId\": \"$_id.emailId\",\r\n" + "    \"orderItemRoot\": {\r\n"
			+ "        \"action\": \"add\",\r\n"
			+ "        \"quantity\": { $convert: { input: \"1\", to: \"int\" } },\r\n"
			+ "        \"state\": \"Acknowledged\",\r\n" + "        \"orderItem\": \"$orderItemNest\",\r\n"
			+ "        \"productOffering\": \"$_id.productOfferingRoot\"\r\n" + "        },\r\n"
			+ "    \"relatedParty\": \"$_id.relatedParty\",\r\n" + "    \"channel\": \"$_id.channel\"\r\n"
			+ "    }},\r\n" + "{$group: {\r\n" + "    \"_id\": {\r\n" + "        \"emailId\": \"$emailId\",\r\n"
			+ "        \"relatedParty\": \"$relatedParty\",\r\n" + "        \"channel\": \"$channel\"\r\n"
			+ "        },\r\n" + "    \"orderItem\": {$push: \"$orderItemRoot\"}\r\n" + "    }},\r\n"
			+ "{$project: {\r\n" + "    \"_id\": 0,\r\n" + "    \"priority\": \"1\",\r\n"
			+ "    \"category\": \"\",\r\n" + "    \"state\": \"Acknowledged\",\r\n"
			+ "    \"notificationContact\": \"$_id.emailId\",\r\n" + "    \"@baseType\": \"productOrder\",\r\n"
			+ "    \"@type\": \"New Order\",\r\n" + "    \"orderItem\": \"$orderItem\",\r\n"
			+ "    \"relatedParty\": \"$_id.relatedParty\",\r\n" + "    \"channel\": \"$_id.channel\",\r\n"
			+ "    \"description\": \"string\"\r\n" + "    }}])";

	public static String placeOrderOld = "db.getCollection('customerCatalogs').aggregate([\r\n"
			+ "{ $match: { \"emailId\": \"{emailId}\", \"offerPrice.offerCode\": {$in: {offerCode}} }},\r\n"
			+ "{ $unwind: \"$offerPrice.products\" },\r\n"
			+ "{ $unwind: \"$offerPrice.products.productspecifications\" },\r\n"
			+ "{ $unwind: \"$offerPrice.products.prices\" },\r\n"
			+ "{ $unwind: \"$offerPrice.products.productspecifications.productspecattributevalue\" },\r\n"
			+ "{ $sort: { \"offerPrice.products.productspecifications.productspecattributevalue.sequence\": 1}},\r\n"
			+ "{ $project: {\r\n" + "    \"_id\": 0,\r\n" + "    \"emailId\": \"$emailId\",\r\n"
			+ "    \"relatedParty\": \"$relatedParty\",\r\n" + "    \"channel\": [{\r\n"
			+ "        \"id\": \"CSC\",\r\n" + "        \"name\": \"CSC\",\r\n" + "        \"role\": \"CSC\",\r\n"
			+ "        \"@type\": \"CSC\"\r\n" + "        }],\r\n" + "    \"productOfferingRoot\": {\r\n"
			+ "        \"id\": \"$offerPrice.offerCode\",\r\n" + "        \"name\": \"$offerPrice.offerName\"\r\n"
			+ "        },\r\n" + "    \"productOffering\": {\r\n"
			+ "        \"id\": \"$offerPrice.products.productCode\",\r\n"
			+ "        \"name\": \"$offerPrice.products.productName\"\r\n" + "        },\r\n"
			+ "    \"productSpecification\": {\r\n" + "        \"href\": \"\",\r\n" + "        \"version\": \"\"\r\n"
			+ "        },\r\n" + "    \"characteristic\": {\r\n" + "        \"name\": { $concat: [\r\n"
			+ "                    {$convert:{input:\"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValueID\",to:\"string\"}},\r\n"
			+ "                    \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeName\"] },\r\n"
			+ "        \"value\": \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n"
			+ "        \"@type\": \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValueType\"\r\n"
			+ "        },\r\n"
			+ "    \"productSpecification_id\": {$cond: { if: { $eq: [ { $toLower: \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeName\" }, \"cfs_specification_id\" ] }, then: \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValue\", else: null }},\r\n"
			+ "    \"productSpecification_name\": {$cond: { if: { $eq: [ { $toLower: \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeName\" }, \"cfs_name\" ] }, then: \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValue\", else: null }},\r\n"
			+ "    \"itemTerm\": [\"$offerPrice.productOfferingTerm\"],\r\n" + "    \"itemPrice\": [{\r\n"
			+ "        \"name\": \"oneTime\",\r\n" + "        \"description\": \"oneTime\",\r\n"
			+ "        \"priceType\": \"oneTime\",\r\n" + "        \"price\": {\r\n"
			+ "                \"taxIncludedAmount\": {\r\n"
			+ "                    \"value\": { $convert: { input: \"$offerPrice.products.prices.onetimeCharge.price\", to: \"int\" } },\r\n"
			+ "                    \"unit\": \"Euro\"\r\n" + "                    },\r\n"
			+ "                \"taxRate\": 0\r\n" + "        }\r\n" + "    },\r\n" + "    {\r\n"
			+ "        \"priceType\": \"recuring\",\r\n" + "        \"description\": \"recuring\",\r\n"
			+ "        \"recurringChargePeriod\": \"$offerPrice.products.prices.recurringCharge.frequency\",\r\n"
			+ "        \"price\": {\r\n" + "            \"taxIncludedAmount\": {\r\n"
			+ "                    \"value\": { $convert: { input: \"$offerPrice.products.prices.recurringCharge.price\", to: \"int\" } },\r\n"
			+ "                    \"unit\": \"Euro\"\r\n" + "                },\r\n"
			+ "            \"taxRate\" : 0.0\r\n" + "            }\r\n" + "    }]\r\n" + "    }},\r\n"
			+ "{ $group: {\r\n" + "    \"_id\": {\r\n" + "        \"emailId\": \"$emailId\",\r\n"
			+ "        \"productOfferingRoot\": \"$productOfferingRoot\",\r\n"
			+ "        \"productOffering\": \"$productOffering\",\r\n"
			+ "        \"productSpecification\": \"$productSpecification\",\r\n"
			+ "        \"itemPrice\": \"$itemPrice\",\r\n" + "        \"relatedParty\": \"$relatedParty\",\r\n"
			+ "        \"channel\": \"$channel\",\r\n" + "        \"itemTerm\": \"$itemTerm\"\r\n" + "        },\r\n"
			+ "    \"characteristic\": {$push: \"$characteristic\"},\r\n"
			+ "    \"productSpecification_ids\": {$push: \"$productSpecification_id\"},\r\n"
			+ "    \"productSpecification_names\": {$push: \"$productSpecification_name\"}\r\n" + "    }},\r\n"
			+ "{$addFields: {\r\n" + "    \"_id.productSpecification.id\": {$cond: {\r\n"
			+ "        if: {$eq: [{$size: {$filter: {input: \"$productSpecification_ids\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]},\r\n"
			+ "        then: \"\",\r\n"
			+ "        else:  {$arrayElemAt: [{$filter: {input: \"$productSpecification_ids\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}, 0]}   \r\n"
			+ "        }},\r\n" + "    \"_id.productSpecification.name\": {$cond: {\r\n"
			+ "        if: {$eq: [{$size: {$filter: {input: \"$productSpecification_names\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}}, 0]},\r\n"
			+ "        then: \"\",\r\n"
			+ "        else:  {$arrayElemAt: [{$filter: {input: \"$productSpecification_names\", as: \"d\", cond: {$ne: [ \"$$d\", null ]}}}, 0]}   \r\n"
			+ "        }}\r\n" + "    }},\r\n" + "{ $project: {\r\n" + "    \"orderItem\": {\r\n"
			+ "        \"action\": \"add\",\r\n"
			+ "        \"quantity\": { $convert: { input: \"1\", to: \"int\" } },\r\n"
			+ "        \"state\": \"Acknowledged\",\r\n" + "        \"itemPrice\": \"$_id.itemPrice\",\r\n"
			+ "        \"productOffering\": \"$_id.productOffering\",\r\n" + "        \"product\": {\r\n"
			+ "            \"id\": \"$_id.productOffering.id\",\r\n"
			+ "            \"name\": \"$_id.productOffering.name\",\r\n"
			+ "            \"characteristic\": \"$characteristic\",\r\n"
			+ "            \"relatedParty\": \"$_id.relatedParty\",\r\n"
			+ "            \"productSpecification\": \"$_id.productSpecification\"\r\n" + "            },\r\n"
			+ "        \"itemTerm\": \"$_id.itemTerm\",\r\n" + "            \"payment\": \"$_id.payment\"\r\n"
			+ "        },\r\n" + "    \"relatedParty\": \"$_id.relatedParty\",\r\n"
			+ "    \"channel\": \"$_id.channel\"\r\n" + "    }},\r\n" + "{$group: {\r\n" + "    \"_id\": {\r\n"
			+ "        \"emailId\": \"$_id.emailId\",\r\n" + "        \"relatedParty\": \"$relatedParty\",\r\n"
			+ "        \"channel\": \"$channel\",\r\n"
			+ "        \"productOfferingRoot\": \"$_id.productOfferingRoot\"\r\n" + "        },\r\n"
			+ "    \"orderItemNest\": {$push: \"$orderItem\"}\r\n" + "    }},\r\n" + "{$project: {\r\n"
			+ "    \"_id\": 0,\r\n" + "    \"emailId\": \"$_id.emailId\",\r\n" + "    \"orderItemRoot\": {\r\n"
			+ "        \"action\": \"add\",\r\n"
			+ "        \"quantity\": { $convert: { input: \"1\", to: \"int\" } },\r\n"
			+ "        \"state\": \"Acknowledged\",\r\n" + "        \"orderItem\": \"$orderItemNest\",\r\n"
			+ "        \"productOffering\": \"$_id.productOfferingRoot\"\r\n" + "        },\r\n"
			+ "    \"relatedParty\": \"$_id.relatedParty\",\r\n" + "    \"channel\": \"$_id.channel\"\r\n"
			+ "    }},\r\n" + "{$group: {\r\n" + "    \"_id\": {\r\n" + "        \"emailId\": \"$emailId\",\r\n"
			+ "        \"relatedParty\": \"$relatedParty\",\r\n" + "        \"channel\": \"$channel\"\r\n"
			+ "        },\r\n" + "    \"orderItem\": {$push: \"$orderItemRoot\"}\r\n" + "    }},\r\n"
			+ "{$project: {\r\n" + "    \"_id\": 0,\r\n" + "    \"priority\": \"1\",\r\n"
			+ "    \"category\": \"\",\r\n" + "    \"state\": \"Acknowledged\",\r\n"
			+ "    \"notificationContact\": \"$_id.emailId\",\r\n" + "    \"@baseType\": \"productOrder\",\r\n"
			+ "    \"@type\": \"New Order\",\r\n" + "    \"orderItem\": \"$orderItem\",\r\n"
			+ "    \"relatedParty\": \"$_id.relatedParty\",\r\n" + "    \"channel\": \"$_id.channel\",\r\n"
			+ "    \"description\": \"string\"\r\n" + "    }}\r\n" + "    \r\n" + "])";

	public static String orderSummaryOld = "db.getCollection('customerCatalogs').aggregate([\r\n" + 
			"{$match: {\"emailId\":\"{emailId}\",\"offerPrice.offerCode\":{$in:{offerCodes}}}},\r\n" + 
			"{$unwind: \"$offerPrice.products\"},\r\n" + 
			"{$match: {\"offerPrice.products.isActiveProduct\": true}},\r\n" + 
			"{$unwind: \"$offerPrice.products.prices\"},\r\n" + 
			"{$unwind: \"$offerPrice.products.productspecifications\"},\r\n" + 
			"{$unwind: \"$offerPrice.products.productspecifications.productspecattributevalue\"},\r\n" + 
			"{$match: {\"offerPrice.products.productspecifications.productspecattributevalue.isVisible\": true}},\r\n" + 
			"{$sort: {\"offerPrice.products.productspecifications.productspecattributevalue.sequence\": 1}},\r\n" + 
			"{$project:{\r\n" + 
			"    \"_id\":0,\r\n" + 
			"    \"customer\": {\r\n" + 
			"        \"organization\": \"$customerDetails.organizationName\",\r\n" + 
			"        \"agree\": \"$customerDetails.agree\",\r\n" + 
			"        \"cancelBtn\": \"$customerDetails.cancelBtn\",\r\n" + 
			"        \"givenName\": \"$customerDetails.givenName\",\r\n" + 
			"        \"familyName\": \"$customerDetails.familyName\",\r\n" + 
			"        \"emailId\": \"$customerDetails.emailId\",\r\n" + 
			"        \"country\": \"$customerDetails.country\",\r\n" + 
			"        \"postalCode\": \"$customerDetails.postalCode\",\r\n" + 
			"        \"city\": \"$customerDetails.city\",\r\n" + 
			"        \"address\": \"$customerDetails.postalAddress\",\r\n" + 
			"        \"error\": \"$error\"\r\n" + 
			"        },    \r\n" + 
			"    \"offer\":{\r\n" + 
			"        \"offerName\": \"$offerPrice.offerName\",\r\n" + 
			"        \r\n" + 
			"        \"offerType\": \"$offerPrice.offerType\",\r\n" + 
			"        \"offerDesc\": {$slice:[{$split: [\"$offerPrice.offerDescription\", \"|\"] },5]}},\r\n" + 
			"    \"products\":{\r\n" + 
			"        \"productName\": \"$offerPrice.products.productName\",\r\n" + 
			"        \"productType\": \"$offerPrice.products.productType\",\r\n" + 
			"        \"onetimeChargePrice\":\"$offerPrice.products.prices.onetimeCharge.price\",\r\n" + 
			"        \"recurringChargePrice\":\"$offerPrice.products.prices.recurringCharge.price\"\r\n" + 
			"        },\r\n" + 
			"    \"char\":{\r\n" + 
			"     \"name\": {$ifNull: [\"$offerPrice.products.productspecifications.productspecattributevalue.displayAttributeName\", \"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeName\"]},        \r\n" + 
			"     \"value\": {$concat: [\"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValue\", {$cond: {if: {$in: [{$toLower: {$ifNull: [{$toLower: \"$offerPrice.products.productspecifications.productspecattributevalue.unitOfMeasurement\"}, \"\"]}}, [\"\", \"na\"]]}, then: \"\", else: {$concat: [\" (\", \"$offerPrice.products.productspecifications.productspecattributevalue.unitOfMeasurement\", \")\"]}}}]}}}},\r\n" + 
			"{$group:{ \"_id\":{\"customer\": \"$customer\", \"offer\":\"$offer\",\"products\":\"$products\"}, \"char\":{$push:\"$char\"}}},\r\n" + 
			"{$addFields:{ \"_id.products.char\":\"$char\"}},\r\n" + 
			"{$group:{\r\n" + 
			"    \"_id\": {\"offer\": \"$_id.offer\", \"customer\": \"$_id.customer\" },\r\n" + 
			"    \"products\":{$push:\"$_id.products\"},\r\n" + 
			"    \"totalOneTime\":{$sum:{$toDouble:\"$_id.products.onetimeChargePrice\"}},\r\n" + 
			"    \"totalMonthly\":{$sum:{$toDouble:\"$_id.products.recurringChargePrice\"}}}},\r\n" + 
			"{$addFields:{ \"_id.offer.products\":\"$products\", \"_id.offer.totalOneTime\":\"$totalOneTime\", \"_id.offer.totalMonthly\":\"$totalMonthly\" }},\r\n" + 
			"{$group:{ \"_id\":\"$_id.customer\", \"offers\":{$push:\"$_id.offer\"}, \"totalOneTimePrice\": {$sum:\"$_id.offer.totalOneTime\"}, \"totalMonthlyPrice\": {$sum:\"$_id.offer.totalMonthly\"}}},\r\n" + 
			"{$project: { \"_id\": 0, \"customer\": \"$_id\", \"offers\": \"$offers\", \"totalOneTimePrice\": \"$totalOneTimePrice\", \"totalMonthlyPrice\": \"$totalMonthlyPrice\" }}\r\n" + 
			"])";
	
	public static String orderSummary="db.getCollection('customerCatalogs').aggregate([\r\n" + 
			"{$match: {\"emailId\":\"{emailId}\",\"offerPrice.offerCode\":{$in:{offerCodes}}}},\r\n" + 
			"{$unwind: \"$offerPrice.products\"},\r\n" + 
			"{$match: {\"offerPrice.products.isActiveProduct\": true}},\r\n" + 
			"{$unwind: \"$offerPrice.products.prices\"},\r\n" + 
			"{$unwind: \"$offerPrice.products.productspecifications\"},\r\n" + 
			"{$addFields: {\r\n" + 
			"    \"attributes\": {$filter: {input: \"$offerPrice.products.productspecifications.productspecattributevalue\", as: \"c\", cond: {$eq: [\"$$c.isVisible\", true]}}}\r\n" + 
			"    }},\r\n" + 
			"{$project:{\r\n" + 
			"    \"_id\":0,\r\n" + 
			"    \"customer\": {\r\n" + 
			"        \"organization\": \"$customerDetails.organizationName\",\r\n" + 
			"        \"agree\": \"$customerDetails.agree\",\r\n" + 
			"        \"cancelBtn\": \"$customerDetails.cancelBtn\",\r\n" + 
			"        \"givenName\": \"$customerDetails.givenName\",\r\n" + 
			"        \"familyName\": \"$customerDetails.familyName\",\r\n" + 
			"        \"emailId\": \"$customerDetails.emailId\",\r\n" + 
			"        \"country\": \"$customerDetails.country\",\r\n" + 
			"        \"postalCode\": \"$customerDetails.postalCode\",\r\n" + 
			"        \"city\": \"$customerDetails.city\",\r\n" + 
			"        \"address\": \"$customerDetails.postalAddress\",\r\n" + 
			"        \"error\": \"$error\"},    \r\n" + 
			"    \"offer\":{\r\n" + 
			"        \"offerName\": \"$offerPrice.offerName\",\r\n" + 
			"        \"offerType\": \"$offerPrice.offerType\",\r\n" + 
			"        \"offerDesc\": {$slice:[{$split: [\"$offerPrice.offerDescription\", \"|\"] },5]}},\r\n" + 
			"    \"products\":{\r\n" + 
			"        \"productCode\": \"$offerPrice.products.productCode\",\r\n" +
			"        \"unique\": \"$offerPrice.products.unique\",\r\n" +
			"        \"productName\": \"$offerPrice.products.productName\",\r\n" + 
			"        \"productType\": \"$offerPrice.products.productType\",\r\n" + 
			"        \"onetimeChargePrice\":\"$offerPrice.products.prices.onetimeCharge.price\",\r\n" + 
			"        \"recurringChargePrice\":\"$offerPrice.products.prices.recurringCharge.price\",\r\n" + 
			"        \"char\": \"$attributes\" }}},\r\n" + 
			"{$group:{\r\n" + 
			"    \"_id\": {\"offer\": \"$offer\", \"customer\": \"$customer\" },\r\n" + 
			"    \"products\":{$push:\"$products\"},\r\n" + 
			"    \"totalOneTime\":{$sum:{$toDouble:\"$products.onetimeChargePrice\"}},\r\n" + 
			"    \"totalMonthly\":{$sum:{$toDouble:\"$products.recurringChargePrice\"}}}},\r\n" + 
			"{$addFields:{ \"_id.offer.products\":\"$products\", \"_id.offer.totalOneTime\":\"$totalOneTime\", \"_id.offer.totalMonthly\":\"$totalMonthly\" }},\r\n" + 
			"{$group:{ \"_id\":\"$_id.customer\", \"offers\":{$push:\"$_id.offer\"}, \"totalOneTimePrice\": {$sum:\"$_id.offer.totalOneTime\"}, \"totalMonthlyPrice\": {$sum:\"$_id.offer.totalMonthly\"}}},\r\n" + 
			"{$project: { \"_id\": 0, \"customer\": \"$_id\", \"offers\": \"$offers\", \"totalOneTimePrice\": \"$totalOneTimePrice\", \"totalMonthlyPrice\": \"$totalMonthlyPrice\" }}\r\n" + 
			"])\r\n" + 
			"";

	public static String errorMessageOld = "db.getCollection('customerCatalogs').aggregate([\r\n"
			+ "{$match: {\"emailId\":\"{emailId}\",\"offerPrice.offerCode\":{$in:{offerCodes}}}},\r\n"
			+ "{$project:{\r\n" + "    \"_id\":0,\r\n" + "    \"customer\": {\r\n"
			+ "        \"organization\": \"$customerDetails.organizationName\",\r\n"
			+ "        \"givenName\": \"$customerDetails.givenName\",\r\n"
			+ "        \"familyName\": \"$customerDetails.familyName\",\r\n"
			+ "        \"emailId\": \"$customerDetails.emailId\",\r\n"
			+ "        \"country\": \"$customerDetails.country\",\r\n"
			+ "        \"postalCode\": \"$customerDetails.postalCode\",\r\n"
			+ "        \"city\": \"$customerDetails.city\",\r\n"
			+ "        \"address\": \"$customerDetails.postalAddress\" },       \r\n"
			+ "    \"errorMsg\": {$cond: {if: {$eq:[{$ifNull: ['$error', \"\"]}, \"\"]}, then: \"There is an issue in configuration!\", else: \"$error\"}},              \r\n"
			+ "    \"viewColorCodeMap\":{ \"cancelBtn\":\"disabled\" }}},\r\n"
			+ "{$group: {\"_id\": {\"customer\": \"$customer\", \"errorMsg\": \"$errorMsg\", \"viewColorCodeMap\": \"$viewColorCodeMap\"}}},\r\n"
			+ "{$project: {_id: 0, \"responseMap\": {\"ValueJson\": {\"customer\": \"$_id.customer\", \"errorBean\":{\"errorMsg\": \"$_id.errorMsg\",\"viewColorCodeMap\": \"$_id.viewColorCodeMap\"}}}}}\r\n"
			+ "])";

	public static String errorMessage = "db.getCollection('customerCatalogs').aggregate([\r\n"
			+ "{$match: {\"emailId\":\"{emailId}\",\"offerPrice.offerCode\":{$in:{offerCodes}}}},\r\n"
			+ "{$project:{\r\n" + "    \"_id\":0,\r\n" + "    \"customer\": {\r\n"
			+ "        \"organization\": \"$customerDetails.organizationName\",\r\n"
			+ "        \"agree\": \"$customerDetails.agree\",\r\n"
			+ "        \"cancelBtn\": \"$customerDetails.cancelBtn\",\r\n"
			+ "        \"givenName\": \"$customerDetails.givenName\",\r\n"
			+ "        \"familyName\": \"$customerDetails.familyName\",\r\n"
			+ "        \"emailId\": \"$customerDetails.emailId\",\r\n"
			+ "        \"country\": \"$customerDetails.country\",\r\n"
			+ "        \"postalCode\": \"$customerDetails.postalCode\",\r\n"
			+ "        \"city\": \"$customerDetails.city\",\r\n"
			+ "        \"address\": \"$customerDetails.postalAddress\" },       \r\n"
			+ "    \"errorMsg\": {$cond: {if: {$eq:[{$ifNull: ['$error', \"\"]}, \"\"]}, then: \"There is an invalid attributes configured in Offer catalog!\", else: \"$error\"}},              \r\n"
			+ "    \"viewColorCodeMap\":{ \"cancelBtn\":\"disabled\", \"agree\":\"disabled\" }}},\r\n"
			+ "{$group: {\"_id\": {\"customer\": \"$customer\", \"errorMsg\": \"$errorMsg\", \"viewColorCodeMap\": \"$viewColorCodeMap\"}}},\r\n"
			+ "{$project: {_id: 0, \"customer\": \"$_id.customer\", \"errorMsg\": \"$_id.errorMsg\"}}\r\n" + "])";
	public static String AMLOfferOld = "db.getCollection('catalogs').aggregate([\r\n"
			+ "{$match: {_id:\"{emailId}\"}},\r\n" + "{$unwind: \"$Catalogs\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook\"},\r\n" + "{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n"
			+ "{$match: {\"Catalogs.priceBook.offerPrices.offerCode\": \"{offerCode}\"}},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications\"},\r\n"
			+ "{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\"},\r\n"
			+ "{$match: {\"Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.isVisible\": true}},\r\n"
			+ "{$sort: {\"Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.sequence\": 1}},\r\n"
			+ "{$group:{\r\n" + "    \"_id\":{\r\n"
			+ "        \"offerName\":\"$Catalogs.priceBook.offerPrices.offerName\",\r\n"
			+ "        \"offerCode\":\"$Catalogs.priceBook.offerPrices.offerCode\",\r\n"
			+ "        \"productName\":\"$Catalogs.priceBook.offerPrices.products.productName\",\r\n"
			+ "        \"productCode\":\"$Catalogs.priceBook.offerPrices.products.productCode\"\r\n" + "        },\r\n"
			+ "        \"productSpecs\": {$push: {\r\n"
			+ "        \"productAttributeProductAttributeCode\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productAttributeProductAttributeCode\",\r\n"
			+ "        \"productSpecAttributeName\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n"
			+ "        \"displayAttributeName\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.displayAttributeName\",\r\n"
			+ "        \"unitOfMeasurement\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.unitOfMeasurement\",\r\n"
			+ "        \"productSpecAttributeValue\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n"
			+ "        \"productSpecAttributeValueType\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValueType\",\r\n"
			+ "        \"minCardinality\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.minCardinality\",\r\n"
			+ "        \"configurable\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.configurable\",\r\n"
			+ "        \"sequence\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.sequence\"\r\n"
			+ "        }}}},\r\n" + "{$addFields: {\r\n"
			+ "    \"offer\": {\"offerCode\": \"$_id.offerCode\", \"offerName\": \"$_id.offerName\"},\r\n"
			+ "    \"product\": {\"productCode\": \"$_id.productCode\", \"productName\": \"$_id.productName\", \"productSpecs\": \"$productSpecs\"}\r\n"
			+ "    }},\r\n" + "{$group: { \"_id\": \"$offer\", \"products\": {$push: \"$product\"}}},\r\n"
			+ "{$project: {\"_id\": 0, \"offerCode\":\"$_id.offerCode\",\"offerName\":\"$_id.offerName\", \"products\": \"$products\"}}])";
	
	public static String AMLOffer="db.getCollection('catalogs').aggregate([\r\n" + 
			"{$match: {_id:\"{emailId}\"}},\r\n" + 
			"{$unwind: \"$Catalogs\"},\r\n" + 
			"{$unwind: \"$Catalogs.priceBook\"},\r\n" + 
			"{$unwind: \"$Catalogs.priceBook.offerPrices\"},\r\n" + 
			"{$match: {\"Catalogs.priceBook.offerPrices.offerCode\": \"{offerCode}\"}},\r\n" + 
			"{$unwind: \"$Catalogs.priceBook.offerPrices.products\"},\r\n" + 
			"{$unwind: \"$Catalogs.priceBook.offerPrices.products.prices\"},\r\n" + 
			"{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications\"},\r\n" + 
			"{$unwind: \"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue\"},\r\n" + 
			"{$match: {\"Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.isVisible\": true}},\r\n" + 
			"{$sort: {\"Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.sequence\": 1}},\r\n" + 
			"{$group:{\r\n" + 
			"    \"_id\":{\r\n" + 
			"        \"offerName\":\"$Catalogs.priceBook.offerPrices.offerName\",\r\n" + 
			"        \"offerCode\":\"$Catalogs.priceBook.offerPrices.offerCode\",\r\n" + 
			"        \"productName\":\"$Catalogs.priceBook.offerPrices.products.productName\",\r\n" + 
			"        \"productCode\":\"$Catalogs.priceBook.offerPrices.products.productCode\",\r\n" + 
			"        \"customerDetails\":\"$customerDetails\",\r\n" + 
			"        \"charges\":{\r\n" + 
			"            \"oneTimeCharge\":{$concat:[\"$Catalogs.priceBook.offerPrices.products.prices.onetimeCharge.price\",\" €\"]},\r\n" + 
			"        \"recurringCharge\":{$concat:[\"$Catalogs.priceBook.offerPrices.products.prices.recurringCharge.price\",\" €\"]}\r\n" + 
			"        }},\r\n" + 
			"       \r\n" + 
			"        \"productSpecs\": {$push: {\r\n" + 
			"        \"productAttributeProductAttributeCode\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productAttributeProductAttributeCode\",\r\n" + 
			"        \"productSpecAttributeName\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n" + 
			"        \"displayAttributeName\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.displayAttributeName\",\r\n" + 
			"        \"unitOfMeasurement\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.unitOfMeasurement\",\r\n" + 
			"        \"productSpecAttributeValue\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n" + 
			"        \"productSpecAttributeValueType\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.productSpecAttributeValueType\",\r\n" + 
			"        \"minCardinality\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.minCardinality\",\r\n" + 
			"        \"configurable\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.configurable\",\r\n" + 
			"        \"sequence\":\"$Catalogs.priceBook.offerPrices.products.productspecifications.productspecattributevalue.sequence\" \r\n" + 
			"        }}}},\r\n" + 
			"{$addFields: {\r\n" + 
			"    \"offer\": {\"offerCode\": \"$_id.offerCode\", \"offerName\": \"$_id.offerName\",\"customerDetails\":\"$_id.customerDetails\",\"charges\":\"$_id.charges\"},\r\n" + 
			"    \"product\": {\"productCode\": \"$_id.productCode\", \"productName\": \"$_id.productName\", \"productSpecs\": \"$productSpecs\"}\r\n" + 
			"    }},\r\n" + 
			"{$group: { \"_id\": \"$offer\", \"products\": {$push: \"$product\"}}},\r\n" + 
			"{$project: {\"_id\": 0, \"offerCode\":\"$_id.offerCode\",\"offerName\":\"$_id.offerName\", \"products\": \"$products\", \"charges\":\"$_id.charges\",\"customerDetails\":\"$_id.customerDetails\"}}])";

	public static String AMLSummary = "db.getCollection('customerCatalogs').aggregate([\r\n"
			+ "{$match: {\"emailId\":\"{emailId}\",\"offerPrice.offerCode\":\"{offerCode}\"}},\r\n"
			+ "{$unwind: \"$offerPrice.products\"},\r\n"
			+ "{$unwind: \"$offerPrice.products.productspecifications\"},\r\n"
			+ "{$unwind: \"$offerPrice.products.productspecifications.productspecattributevalue\"},\r\n"
			+ "{$match: {\"offerPrice.products.productspecifications.productspecattributevalue.isVisible\": true}},\r\n"
			+ "{$sort: {\"offerPrice.products.productspecifications.productspecattributevalue.sequence\": 1}},\r\n"
			+ "{$group:{\r\n" + "    \"_id\":{\r\n" + "        \"offerName\":\"$offerPrice.offerName\",\r\n"
			+ "        \"offerCode\":\"$offerPrice.offerCode\",\r\n"
			+ "        \"productName\":\"$offerPrice.products.productName\",\r\n"
			+ "        \"productCode\":\"$offerPrice.products.productCode\"\r\n" + "        },\r\n"
			+ "        \"productSpecs\": {$push: {\r\n"
			+ "        \"productAttributeProductAttributeCode\":\"$offerPrice.products.productspecifications.productspecattributevalue.productAttributeProductAttributeCode\",\r\n"
			+ "        \"productSpecAttributeName\":\"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeName\",\r\n"
			+ "        \"displayAttributeName\":\"$offerPrice.products.productspecifications.productspecattributevalue.displayAttributeName\",\r\n"
			+ "        \"unitOfMeasurement\":\"$offerPrice.products.productspecifications.productspecattributevalue.unitOfMeasurement\",\r\n"
			+ "        \"productSpecAttributeValue\":\"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValue\",\r\n"
			+ "        \"productSpecAttributeValueType\":\"$offerPrice.products.productspecifications.productspecattributevalue.productSpecAttributeValueType\",\r\n"
			+ "        \"minCardinality\":\"$offerPrice.products.productspecifications.productspecattributevalue.minCardinality\",\r\n"
			+ "        \"configurable\":\"$offerPrice.products.productspecifications.productspecattributevalue.configurable\",\r\n"
			+ "        \"sequence\":\"$offerPrice.products.productspecifications.productspecattributevalue.sequence\"\r\n"
			+ "        }}}},\r\n" + "{$addFields: {\r\n"
			+ "    \"offer\": {\"offerCode\": \"$_id.offerCode\", \"offerName\": \"$_id.offerName\"},\r\n"
			+ "    \"product\": {\"productCode\": \"$_id.productCode\", \"productName\": \"$_id.productName\", \"productSpecs\": \"$productSpecs\"}\r\n"
			+ "    }},\r\n" + "{$group: { \"_id\": \"$offer\", \"products\": {$push: \"$product\"}}},\r\n"
			+ "{$project: {\"_id\": 0, \"offerCode\":\"$_id.offerCode\",\"offerName\":\"$_id.offerName\", \"products\": \"$products\"}}])";
}
