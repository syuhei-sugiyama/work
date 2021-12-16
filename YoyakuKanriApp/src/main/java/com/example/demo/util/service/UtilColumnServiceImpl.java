package com.example.demo.util.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.demo.util.model.UtilColumn;
import com.example.demo.util.serviceif.UtilColumnService;

@Service
public class UtilColumnServiceImpl implements UtilColumnService{

	/**
	 * 登録/更新時のテーブル共通カラムの値を生成する
	 */
	@Override
	public UtilColumn createUtilColumnValue(String register) {
		UtilColumn createVal = new UtilColumn();
		createVal.setCreateBy(register);
		createVal.setUpdateBy(register);
		Date nowDate = new Date();
		createVal.setCreateTime(nowDate);
		createVal.setUpdateTime(nowDate);
		return createVal;
	}

}
