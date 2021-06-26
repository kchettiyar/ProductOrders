package com.kchetty.ProductOrders.Utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;

public class ExecuteMongoQuery {

	public Map<String, Object> getMongoData(String host, int port, String dbName, String query) {
		DB db = new MongoClientException(host, port).getDB(dbName);
		Map<String, Object> data = (Map<String, Object>) db.eval(query);

		if (((List) data.get("_batch")).size() > 0)
			return (Map) ((List) data.get("_batch")).get(0);
		else
			return new HashMap<>();
	}
}
