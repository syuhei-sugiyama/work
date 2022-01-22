package com.example.demo.waitingList.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.hairdresser.service.HairdresserServiceImpl;
import com.example.demo.menu.service.MenuServiceImpl;
import com.example.demo.util.constant.UtilServiceConst;
import com.example.demo.util.model.UtilColumn;
import com.example.demo.util.service.SaibanServiceImpl;
import com.example.demo.util.service.UtilColumnServiceImpl;
import com.example.demo.util.service.UtilServiceImpl;
import com.example.demo.waitingList.WaitingListConst;
import com.example.demo.waitingList.model.WaitingListForm;
import com.example.demo.waitingList.model.WaitingListHistory;
import com.example.demo.waitingList.model.WaitingListInfo;
import com.example.demo.waitingList.repository.WaitingListHistoryRepository;
import com.example.demo.waitingList.serviceif.WaitingListService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WaitingListServiceImpl implements WaitingListService {

	private final WaitingListHistoryRepository waitingListHistoryRepository;

	private final MenuServiceImpl menuServiceImpl;

	private final HairdresserServiceImpl hairdresserServiceImpl;

	private final UtilServiceImpl utilServiceImpl;

	private final SaibanServiceImpl saibanServiceImpl;

	private final UtilColumnServiceImpl utilColumnServiceImpl;

	/**
	 * [機能] 全てのキャンセル待ち情報をjson形式のStringにして返す
	 * @return 全てのキャンセル待ち情報のjson形式文字列
	 */
	@Override
	public String getAllWaitingList() {
		String resJsonStr = null;
		List<WaitingListHistory> allWaitingListHistory = waitingListHistoryRepository.findAll();
		List<WaitingListInfo> refillWaitingList = new ArrayList<WaitingListInfo>();
		for (WaitingListHistory waitingListHistory : allWaitingListHistory) {
			WaitingListInfo waitingListInfo = new WaitingListInfo();
			Calendar waitingYoyakuDate = Calendar.getInstance();
			waitingYoyakuDate.setTime(waitingListHistory.getStartDate());
			waitingListInfo.setStart(new SimpleDateFormat("YYYY-MM-dd'T'HH:mm").format(waitingYoyakuDate.getTime()));
			waitingYoyakuDate.setTime(waitingListHistory.getEndDate());
			waitingListInfo.setEnd(new SimpleDateFormat("YYYY-MM-dd'T'HH:mm").format(waitingYoyakuDate.getTime()));
			waitingListInfo.setTitle(waitingListHistory.getCustomer());
			waitingListInfo.setUrl("/waitingList/edit/" + waitingListHistory.getWaitingListHistoryId());
			refillWaitingList.add(waitingListInfo);
		}
		ObjectMapper om = new ObjectMapper();
		try {
			resJsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(refillWaitingList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resJsonStr;
	}

	/**
	 * [機能] キャンセル待ち登録画面の項目を準備する
	 * @param waitingListForm キャンセル待ちのフォーム
	 * @return waitingListForm キャンセル待ち登録画面の項目がセットされたフォーム
	 */
	@Override
	public WaitingListForm prepareItemOfRegisterScreen(WaitingListForm waitingListForm) {
		waitingListForm.setMenus(menuServiceImpl.getAllMenuMap());
		waitingListForm.setHairdressers(hairdresserServiceImpl.getAllHairdresserMap());
		return waitingListForm;
	}

	/**
	 * [機能] キャンセル待ち情報を登録する
	 * @param waitingListForm 画面から入力されたキャンセル待ち情報
	 * @param loginUserName ログインユーザ名
	 */
	@Override
	public void register(WaitingListForm waitingListForm, String loginUserName) {
		WaitingListHistory registerEntity = new WaitingListHistory();
		// 画面から選択された値を設定
		setWaitingListInfoFromScreen(registerEntity, waitingListForm);
		// キャンセル待ち履歴IDの生成
		registerEntity.setWaitingListHistoryId(
				saibanServiceImpl.createId(WaitingListConst.WAITING_LIST_HISTORY_ID, loginUserName));
		// 共通カラムの設定
		UtilColumn utilColumnVal = utilColumnServiceImpl.createUtilColumnValue(loginUserName);
		registerEntity.setCreateBy(utilColumnVal.getCreateBy());
		registerEntity.setUpdateBy(utilColumnVal.getUpdateBy());
		registerEntity.setCreateTime(utilColumnVal.getCreateTime());
		registerEntity.setUpdateTime(utilColumnVal.getUpdateTime());
		// キャンセル待ち履歴テーブルへ登録
		waitingListHistoryRepository.save(registerEntity);
	}

	/**
	 * [機能] 画面にて選択された値をキャンセル待ち履歴モデルにセットする
	 * @param registerEntity 値をセットするキャンセル待ち履歴モデル
	 * @param waitingListForm 画面にて選択された値を保持してるフォーム
	 */
	private void setWaitingListInfoFromScreen(WaitingListHistory registerEntity, WaitingListForm waitingListForm) {
		// 美容師IDのセット
		registerEntity.setBiyoshiId(waitingListForm.getSelectHairdresserId());
		// お客様名のセット
		registerEntity.setCustomer(waitingListForm.getCustomer());
		// 予約日時の設定
		Calendar yoyakuDate = Calendar.getInstance();
		List<Integer> dateList = utilServiceImpl.convertStrToIntArr(waitingListForm.getDate(),
				UtilServiceConst.DATE_SEPARATER);
		List<Integer> startTimeList = utilServiceImpl.convertStrToIntArr(waitingListForm.getStartTime(),
				UtilServiceConst.TIME_SEPARATER);
		List<Integer> endTimeList = utilServiceImpl.convertStrToIntArr(waitingListForm.getEndTime(),
				UtilServiceConst.TIME_SEPARATER);
		// 年、月、日、時、分、秒
		yoyakuDate.set(dateList.get(0), dateList.get(1) - 1, dateList.get(2), startTimeList.get(0), startTimeList.get(1),
				0);
		// 開始時間のセット
		registerEntity.setStartDate(yoyakuDate.getTime());
		// 終了時間のセット
		yoyakuDate.set(Calendar.HOUR_OF_DAY, endTimeList.get(0));
		yoyakuDate.set(Calendar.MINUTE, endTimeList.get(1));
		registerEntity.setEndDate(yoyakuDate.getTime());
		// メニューIDの設定
		registerEntity.setMenus(utilServiceImpl.convertStrArrToStr(waitingListForm.getSelectMenuId(),
				UtilServiceConst.MENU_ID_SEPARATER));
		// キャンセル待ち履歴IDのセット(更新用)
		registerEntity.setWaitingListHistoryId(waitingListForm.getWaitingListHistoryId());
	}

}
