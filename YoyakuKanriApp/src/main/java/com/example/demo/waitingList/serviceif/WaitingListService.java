package com.example.demo.waitingList.serviceif;

import com.example.demo.waitingList.model.WaitingListForm;

public interface WaitingListService {

	String getAllWaitingList();

	WaitingListForm prepareItemOfRegisterScreen(WaitingListForm waitingListForm);

	void register(WaitingListForm waitingListForm, String loginUserName);
}
