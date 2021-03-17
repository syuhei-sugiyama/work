package com.example.demo;

import java.io.Serializable;
import java.util.List;

public interface MyDataDao <T> extends Serializable{
	public List<T> getAll();
	public T findById(long id);
	public List<T> findByName(String name);
	public List<T> find(String fstr);
	public List<T> find2(String fstr);
	public List<T> findByBindVariable(String fstr);
	public List<T> findByNamedQuery(String fstr);
	public List<T> findByAge(int min, int max);
	public List<T> getAllByCriteriaAPI();
	public List<T> findByCriteriaAPI(String fstr);
	public List<T> getAllSortByCriteriaAPI();
	public List<T> getAllPaging();
}