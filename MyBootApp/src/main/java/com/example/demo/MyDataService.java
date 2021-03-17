package com.example.demo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

/*
 * @Serviceアノテーション
 * サービスとして使うクラスには必須のアノテーション
 * 付与することで、サービスとして本クラスが登録される
 * また、@Serviceアノテーションによって、自動的にMyDataServiceクラスは、アプリケーション内でBean化される
 */
@Service
public class MyDataService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<MyData> getAll() {
		return (List<MyData>) entityManager
				.createQuery("from MyData").getResultList();
	}

	public MyData get(int num) {
		return (MyData) entityManager
				.createQuery("from MyData where id = " + num)
				.getSingleResult();
	}

	public List<MyData> find(String fstr) {
		List<MyData> list = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MyData> query = builder.createQuery(MyData.class);
		Root<MyData> root = query.from(MyData.class);
		query.select(root).where(builder.equal(root.get("name"), fstr));
		list = (List<MyData>) entityManager.createQuery(query).getResultList();
		return list;
	}
}
