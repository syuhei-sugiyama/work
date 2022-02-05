package com.example.demo.util.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.util.constant.SaibanServiceConst;
import com.example.demo.util.model.Saiban;
import com.example.demo.util.repository.SaibanRepository;
import com.example.demo.util.serviceif.SaibanService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SaibanServiceImpl implements SaibanService {

	private final SaibanRepository saibanRepository;

	/**
	 * 採番キー毎の番号を生成する
	 */
	@Transactional
	@Override
	public String createId(String saibanKey) {
		// 採番テーブル検索
		Saiban saiban = saibanRepository.findBySaibanId(saibanKey);

		if (saiban == null) {
			/*
			 * 採番キーのレコードが存在しない場合
			 * 新規で採番テーブルにレコード登録
			 */
			Saiban newSaiban = new Saiban();
			newSaiban.setSaibanId(saibanKey);
			newSaiban.setCurrentNumber(SaibanServiceConst.initSaibanNum);

			/*
			 * 採番テーブルへの初回登録
			 */
			saibanRepository.save(newSaiban);
			return newSaiban.getSaibanId() + newSaiban.getCurrentNumber().toString();
		} else if (!(saiban.getSaibanId().isEmpty())) {
			/*
			 * 採番キーのレコードが存在する場合
			 * 現在番号を1ずつカウントアップ
			 */
			saiban.setCurrentNumber(saiban.getCurrentNumber() + SaibanServiceConst.initSaibanNum);
			/*
			 * 採番テーブルの更新
			 */
			saibanRepository.save(saiban);
			return saiban.getSaibanId() + saiban.getCurrentNumber().toString();
		}
		return null;
	}

}
