package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.MyDataMongo;

/*
 * MongoDB用のリポジトリは、「MongoRepository」というインターフェースを継承して作成する
 * これは、pom.xmlに追加した、spring-boot-starter-data-mongodbによって追加された、ライブラリに用意されているもの
 */
@Repository
public interface MyDataMongoRepository extends MongoRepository<MyDataMongo, String> {

	public List<MyDataMongo> findByName(String name);
}
