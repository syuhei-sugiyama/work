package com.example.demo.yoyaku.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.hairdresser.service.HairdresserServiceImpl;
import com.example.demo.menu.service.MenuServiceImpl;
import com.example.demo.util.model.UtilColumn;
import com.example.demo.util.service.SaibanServiceImpl;
import com.example.demo.util.service.UtilColumnServiceImpl;
import com.example.demo.yoyaku.YoyakuRirekiConst;
import com.example.demo.yoyaku.model.YoyakuInfo;
import com.example.demo.yoyaku.model.YoyakuRireki;
import com.example.demo.yoyaku.model.YoyakuRirekiForm;
import com.example.demo.yoyaku.repository.YoyakuRirekiRepository;
import com.example.demo.yoyaku.serviceif.YoyakuService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class YoyakuServiceImpl implements YoyakuService {

	private final MenuServiceImpl menuServiceImpl;

	private final HairdresserServiceImpl HairdresserServiceImpl;

	private final SaibanServiceImpl saibanServiceImpl;

	private final UtilColumnServiceImpl utilColumnServiceImpl;

	private final YoyakuRirekiRepository yoyakuRirekiRepository;

	@Override
	public YoyakuRirekiForm init(YoyakuRirekiForm yoyakuRirekiForm) {
		yoyakuRirekiForm.setMenus(menuServiceImpl.getAllMenuMap());
		yoyakuRirekiForm.setHairdressers(HairdresserServiceImpl.getAllHairdresserMap());
		return yoyakuRirekiForm;
	}

	@Override
	public void register(YoyakuRirekiForm yoyakuRirekiForm, String loginUserName) {
		YoyakuRireki yoyakuRegisterInfo = new YoyakuRireki();
		// 画面から選択された値を設定する
		setYoyakuInfoFromScreen(yoyakuRegisterInfo, yoyakuRirekiForm);
		// 予約履歴IDの生成
		yoyakuRegisterInfo
				.setYoyakuRirekiId(saibanServiceImpl.createId(YoyakuRirekiConst.YOYAKURIREKI_ID, loginUserName));
		// 共通カラムの設定
		UtilColumn utilColumnVal = utilColumnServiceImpl.createUtilColumnValue(loginUserName);
		yoyakuRegisterInfo.setCreateBy(utilColumnVal.getCreateBy());
		yoyakuRegisterInfo.setUpdateBy(utilColumnVal.getUpdateBy());
		yoyakuRegisterInfo.setCreateTime(utilColumnVal.getCreateTime());
		yoyakuRegisterInfo.setUpdateTime(utilColumnVal.getUpdateTime());
		// 予約履歴テーブルへ登録
		yoyakuRirekiRepository.save(yoyakuRegisterInfo);
	}

	/**
	 * [機能] String→List<Integer>へ変換する
	 * @param conStr 変換対象文字列
	 * @param sepStr 区切り文字
	 * @return 変換後の値を格納したint配列
	 */
	private List<Integer> convertStrToIntArr(String conStr, String sepStr) {
		List<Integer> result = new ArrayList<Integer>();
		String[] conArr = conStr.split(sepStr);
		for (String str : conArr) {
			result.add(Integer.parseInt(str));
		}
		return result;
	}

	/**
	 * [機能] String[]の要素を連結する
	 * @param conStrArr 変換対象文字列配列
	 * @param sepStr 連結用区切り文字
	 * @return 連結文字列
	 */
	private String convertStrArrToStr(String[] conStrArr, String sepStr) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < conStrArr.length; i++) {
			if (i + 1 == conStrArr.length) {
				// 最後の要素を追加するときは区切り文字無し
				sb.append(conStrArr[i]);
			} else {
				sb.append(conStrArr[i]);
				sb.append(sepStr);
			}
		}
		return sb.toString();
	}

	/**
	 * [機能] 全ての予約情報をjson形式のStringでfullCalendarの画面に返す
	 * @return 全ての予約情報のjson形式文字列
	 */
	@Override
	public String getAllYoyaku() {
		String resJsonStr = null;
		List<YoyakuRireki> allYoyakuRireki = yoyakuRirekiRepository.findAll();
		List<YoyakuInfo> refillYoyakuList = new ArrayList<YoyakuInfo>();
		for (YoyakuRireki yoyakuRireki : allYoyakuRireki) {
			YoyakuInfo yoyakuInfo = new YoyakuInfo();
			Calendar yoyakuDate = Calendar.getInstance();
			yoyakuDate.setTime(yoyakuRireki.getYoyakuDate());
			String yoyakuDateToStr = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm").format(yoyakuDate.getTime());
			yoyakuInfo.setStart(yoyakuDateToStr);
			// 終了時間もセットする
			yoyakuDate.setTime(yoyakuRireki.getEndDate());
			String yoyakuEndDateToStr = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm").format(yoyakuDate.getTime());
			yoyakuInfo.setEnd(yoyakuEndDateToStr);
			// タイトルのセット
			String title = yoyakuRireki.getCustomer();
			yoyakuInfo.setTitle(title);
			// 後で更新用ページのパスに変更必要
			yoyakuInfo.setUrl("/yoyaku/edit/" + yoyakuRireki.getYoyakuRirekiId());
			refillYoyakuList.add(yoyakuInfo);
		}
		// returnするjson形式文字列の生成
		ObjectMapper mapper = new ObjectMapper();
		try {
			resJsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(refillYoyakuList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return resJsonStr;
	}

	@Override
	public void getYoyakuById(YoyakuRirekiForm yoyakuRirekiForm, String yoyakuRirekiId) {
		YoyakuRireki yoyakuInfo = yoyakuRirekiRepository.findByYoyakuRirekiId(yoyakuRirekiId);
		yoyakuRirekiForm.setSelectHairdresserId(yoyakuInfo.getBiyoshiId());
		yoyakuRirekiForm.setSelectMenuId(yoyakuInfo.getMenus().split(YoyakuRirekiConst.MENU_ID_SEPARATER));
		yoyakuRirekiForm.setCustomer(yoyakuInfo.getCustomer());
		Calendar yoyakuDate = Calendar.getInstance();
		yoyakuDate.setTime(yoyakuInfo.getYoyakuDate());
		yoyakuRirekiForm.setDate(new SimpleDateFormat("YYYY-MM-dd").format(yoyakuDate.getTime()));
		yoyakuRirekiForm.setStartTime(new SimpleDateFormat("HH:mm").format(yoyakuDate.getTime()));
		yoyakuDate.setTime(yoyakuInfo.getEndDate());
		yoyakuRirekiForm.setEndTime(new SimpleDateFormat("HH:mm").format(yoyakuDate.getTime()));
		yoyakuRirekiForm.setYoyakuRirekiId(yoyakuInfo.getYoyakuRirekiId());
	}

	@Override
	public void update(YoyakuRirekiForm yoyakuRirekiForm, String loginUserName) {
		// 登録済み情報の取得
		YoyakuRireki yoyakuInfo = yoyakuRirekiRepository.findByYoyakuRirekiId(yoyakuRirekiForm.getYoyakuRirekiId());
		// 画面にて選択された値を設定する
		setYoyakuInfoFromScreen(yoyakuInfo, yoyakuRirekiForm);
		// 共通カラムの設定
		UtilColumn utilColumnVal = utilColumnServiceImpl.createUtilColumnValue(loginUserName);
		yoyakuInfo.setUpdateBy(utilColumnVal.getUpdateBy());
		yoyakuInfo.setUpdateTime(utilColumnVal.getUpdateTime());
		// 予約履歴テーブルを更新
		yoyakuRirekiRepository.save(yoyakuInfo);
	}

	/**
	 * [機能] 画面にて選択された値を予約履歴モデルにセットする
	 * @param yoyakuInfo 値をセットする予約履歴モデル
	 * @param yoyakuRirekiForm 画面にて選択された値を保持してるフォーム
	 */
	private void setYoyakuInfoFromScreen(YoyakuRireki yoyakuInfo, YoyakuRirekiForm yoyakuRirekiForm) {
		// 美容師IDのセット
		yoyakuInfo.setBiyoshiId(yoyakuRirekiForm.getSelectHairdresserId());
		// お客様名のセット
		yoyakuInfo.setCustomer(yoyakuRirekiForm.getCustomer());
		// 予約日時の設定
		Calendar yoyakuDate = Calendar.getInstance();
		List<Integer> dateList = convertStrToIntArr(yoyakuRirekiForm.getDate(), YoyakuRirekiConst.DATE_SEPARATER);
		List<Integer> startTimeList = convertStrToIntArr(yoyakuRirekiForm.getStartTime(),
				YoyakuRirekiConst.TIME_SEPARATER);
		List<Integer> endTimeList = convertStrToIntArr(yoyakuRirekiForm.getEndTime(), YoyakuRirekiConst.TIME_SEPARATER);
		// 年、月、日、時、分、秒
		yoyakuDate.set(dateList.get(0), dateList.get(1) - 1, dateList.get(2), startTimeList.get(0),
				startTimeList.get(1), 0);
		// 開始時間のセット
		yoyakuInfo.setYoyakuDate(yoyakuDate.getTime());
		// 終了時間のセット
		yoyakuDate.set(Calendar.HOUR_OF_DAY, endTimeList.get(0));
		yoyakuDate.set(Calendar.MINUTE, endTimeList.get(1));
		yoyakuInfo.setEndDate(yoyakuDate.getTime());
		// メニューIDの設定
		yoyakuInfo
				.setMenus(convertStrArrToStr(yoyakuRirekiForm.getSelectMenuId(), YoyakuRirekiConst.MENU_ID_SEPARATER));
		// 予約履歴IDのセット(更新用)
		yoyakuInfo.setYoyakuRirekiId(yoyakuRirekiForm.getYoyakuRirekiId());
	}

	@Override
	public void delete(String yoyakuRirekiId) {
		yoyakuRirekiRepository.deleteById(yoyakuRirekiId);
	}

}
