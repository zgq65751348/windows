package com.microsoft.mongo.repository;


import com.microsoft.mongo.document.Express;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpressRepository  extends MongoRepository<Express,String> {
}
