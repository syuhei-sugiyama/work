package com.example.demo.waitingList.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.hairdresser.service.HairdresserServiceImpl;
import com.example.demo.menu.service.MenuServiceImpl;
import com.example.demo.util.constant.UtilServiceConst;
import com.example.demo.util.service.SaibanServiceImpl;
import com.example.demo.util.service.UtilServiceImpl;
import com.example.demo.waitingList.WaitingListConst;
import com.example.demo.waitingList.model.WListScreenSelectionItems;
import com.example.demo.waitingList.model.WaitingListForm;
import com.example.demo.waitingList.model.WaitingListHistory;
import com.example.demo.waitingList.model.WaitingListInfo;
import com.example.demo.waitingList.repository.WaitingListHistoryRepository;
import com.example.demo.waitingList.serviceif.WaitingListService;
import com.example.demo.yoyaku.repository.YoyakuRirekiRepository;
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

	private final YoyakuRirekiRepository yoyakuRirekiRepository;

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
	 * {@inheritDoc}
	 */
	@Override
	public WListScreenSelectionItems getScreenItems() {
		WListScreenSelectionItems wListScreenSelectionItems = new WListScreenSelectionItems();
		wListScreenSelectionItems.setMenus(menuServiceImpl.getAllMenuMap());
		wListScreenSelectionItems.setHairdressers(hairdresserServiceImpl.getAllHairdresserMap());
		return wListScreenSelectionItems;
	}

	/**
	 * [機能] キャンセル待ち情報を登録する
	 * @param waitingListForm 画面から入力されたキャンセル待ち情報
	 */
	@Transactional
	@Override
	public void register(WaitingListForm waitingListForm) {
		WaitingListHistory registerEntity = new WaitingListHistory();
		// 画面から選択された値を設定
		setWaitingListInfoFromScreen(registerEntity, waitingListForm);
		// キャンセル待ち履歴IDの生成
		registerEntity.setWaitingListHistoryId(
				saibanServiceImpl.createId(WaitingListConst.WAITING_LIST_HISTORY_ID));
		// キャンセル待ち履歴テーブルへ登録
		waitingListHistoryRepository.save(registerEntity);
		// キャンセル待ち履歴フォームに予約履歴IDの値が設定されていた場合、紐づく予約を削除
		if (waitingListForm.getYoyakuRirekiId() != null && !(waitingListForm.getYoyakuRirekiId().isEmpty())) {
			yoyakuRirekiRepository.deleteById(waitingListForm.getYoyakuRirekiId());
		}
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setWaitingListInfoToForm(WaitingListForm waitingListForm, String waitingListHistoryId) {
		WaitingListHistory waitingListInfo = waitingListHistoryRepository.findByWaitingListHistoryId(waitingListHistoryId);
		waitingListForm.setSelectHairdresserId(waitingListInfo.getBiyoshiId());
		waitingListForm.setSelectMenuId(waitingListInfo.getMenus().split(UtilServiceConst.MENU_ID_SEPARATER));
		waitingListForm.setCustomer(waitingListInfo.getCustomer());
		Calendar yoyakuDate = Calendar.getInstance();
		yoyakuDate.setTime(waitingListInfo.getStartDate());
		waitingListForm.setDate(new SimpleDateFormat("YYYY-MM-dd").format(yoyakuDate.getTime()));
		waitingListForm.setStartTime(new SimpleDateFormat("HH:mm").format(yoyakuDate.getTime()));
		yoyakuDate.setTime(waitingListInfo.getEndDate());
		waitingListForm.setEndTime(new SimpleDateFormat("HH:mm").format(yoyakuDate.getTime()));
		waitingListForm.setWaitingListHistoryId(waitingListInfo.getWaitingListHistoryId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public void updateWaitingList(WaitingListForm waitingListForm) {
		// 登録済み情報の取得
		WaitingListHistory waitingListInfo = waitingListHistoryRepository
				.findByWaitingListHistoryId(waitingListForm.getWaitingListHistoryId());
		// 画面にて入力された値を設定する
		setWaitingListInfoFromScreen(waitingListInfo, waitingListForm);
		// キャンセル待ち履歴テーブルを更新
		waitingListHistoryRepository.save(waitingListInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteWaitingList(String waitingListHistoryId) {
		waitingListHistoryRepository.deleteById(waitingListHistoryId);
	}

}
