package com.example.demo.util.service;

import org.springframework.stereotype.Service;

import com.example.demo.util.model.Saiban;
import com.example.demo.util.model.UtilColumn;
import com.example.demo.util.repository.SaibanRepository;
import com.example.demo.util.serviceif.SaibanService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SaibanServiceImpl implements SaibanService {

	private final SaibanRepository saibanRepository;

	private final UtilColumnServiceImpl utilColumnServiceImpl;


	/**
	 * 採番キー毎の番号を生成する
	 */
	@Override
	public String createId(String saibanKey, String torokuUser) {
		/*
		 * 共通カラムの値生成用のサービス呼び出し
		 */
		UtilColumn utilColumnVal = utilColumnServiceImpl.createUtilColumnValue(torokuUser);

		Saiban saiban = saibanRepository.findBySaibanId(saibanKey);

		if (saiban == null) {
			/*
			 * 採番キーのレコードが存在しない場合
			 * 新規で採番テーブルにレコード登録
			 */
			Saiban newSaiban = new Saiban();
			newSaiban.setSaibanId(saibanKey);
			newSaiban.setCurrentNumber((long) 1);

			newSaiban.setCreateBy(utilColumnVal.getCreateBy());
			newSaiban.setUpdateBy(utilColumnVal.getUpdateBy());
			newSaiban.setCreateTime(utilColumnVal.getCreateTime());
			newSaiban.setUpdateTime(utilColumnVal.getUpdateTime());

			/*
			 * 採番テーブルへの初回登録
			 */
			saibanRepository.save(newSaiban);
			return newSaiban.getSaibanId() + "1";
		} else if (!(saiban.getSaibanId().isEmpty())) {
			/*
			 * 採番キーのレコードが存在する場合
			 * 現在番号を更新する
			 */
			saiban.setCurrentNumber(saiban.getCurrentNumber() + 1);
			saiban.setUpdateBy(utilColumnVal.getUpdateBy());
			saiban.setUpdateTime(utilColumnVal.getUpdateTime());

			/*
			 * 採番テーブルの更新
			 */
			saibanRepository.save(saiban);
			return saiban.getSaibanId() + saiban.getCurrentNumber().toString();
		}
		return null;
	}

}
