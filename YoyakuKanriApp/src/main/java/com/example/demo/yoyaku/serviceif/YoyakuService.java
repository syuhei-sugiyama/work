package com.example.demo.yoyaku.serviceif;

import com.example.demo.yoyaku.model.YoyakuRirekiForm;

public interface YoyakuService {

	YoyakuRirekiForm init(YoyakuRirekiForm yoyakuRirekiForm);

	void register(YoyakuRirekiForm yoyakuRirekiForm, String loginUserName);

	String getAllYoyaku();

	void getYoyakuById(YoyakuRirekiForm yoyakuRirekiForm, String yoyakuRirekiId);

}