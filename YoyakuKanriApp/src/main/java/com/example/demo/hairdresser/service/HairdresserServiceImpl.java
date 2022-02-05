package com.example.demo.hairdresser.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.hairdresser.HairdresserConst;
import com.example.demo.hairdresser.model.Hairdresser;
import com.example.demo.hairdresser.repository.HairdresserRepository;
import com.example.demo.hairdresser.serviceif.HairdresserService;
import com.example.demo.util.model.UtilColumn;
import com.example.demo.util.service.SaibanServiceImpl;
import com.example.demo.util.service.UtilColumnServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HairdresserServiceImpl implements HairdresserService {

	private final SaibanServiceImpl saibanServiceImpl;

	private final UtilColumnServiceImpl utilColumnServiceImpl;

	private final HairdresserRepository hairdresserRepository;

	@Override
	public void addHairdresser(Hairdresser hairdresserForm, String loginUserName) {
		// 採番テーブルを使った、美容師IDの生成
		hairdresserForm
				.setHairdresserId(saibanServiceImpl.createId(HairdresserConst.HAIRDRESSER_ID));

		// 共通カラムの値生成
		UtilColumn utilColumnVal = utilColumnServiceImpl.createUtilColumnValue(loginUserName);
		hairdresserForm.setCreateBy(utilColumnVal.getCreateBy());
		hairdresserForm.setCreateTime(utilColumnVal.getCreateTime());
		hairdresserForm.setUpdateBy(utilColumnVal.getUpdateBy());
		hairdresserForm.setUpdateTime(utilColumnVal.getUpdateTime());

		// 美容師テーブルへ登録
		hairdresserRepository.save(hairdresserForm);
	}

	@Override
	public void updateHairdresser(Hairdresser hairdresserForm, String loginUserName) {
		// 既存の情報取得
		Hairdresser registeredInfo = hairdresserRepository.findByHairdresserId(hairdresserForm.getHairdresserId());

		// 画面から入力された情報で上書き
		registeredInfo.setHairdresserName(hairdresserForm.getHairdresserName());

		// 更新者、更新日時の更新
		UtilColumn utilColumnVal = utilColumnServiceImpl.createUtilColumnValue(loginUserName);
		registeredInfo.setUpdateBy(utilColumnVal.getUpdateBy());
		registeredInfo.setUpdateTime(utilColumnVal.getUpdateTime());

		// 美容師テーブルを更新
		hairdresserRepository.save(registeredInfo);
	}

	@Override
	public void deleteHairdresser(String hairdresserId) {
		hairdresserRepository.deleteById(hairdresserId);
	}

	@Override
	public Map<String, String> getAllHairdresserMap() {
		List<Hairdresser> hairdresserList = hairdresserRepository.findAll();
		Map<String, String> resultMap = new HashMap<String, String>();
		for (Hairdresser hairdresser : hairdresserList) {
			resultMap.put(hairdresser.getHairdresserId(), hairdresser.getHairdresserName());
		}
		return resultMap;
	}
}
