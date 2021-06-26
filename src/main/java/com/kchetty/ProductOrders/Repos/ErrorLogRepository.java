package com.kchetty.ProductOrders.Repos;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kchetty.ProductOrders.Beans.ErrorLog;

public interface ErrorLogRepository extends MongoRepository<ErrorLog,Serializable>{

}
