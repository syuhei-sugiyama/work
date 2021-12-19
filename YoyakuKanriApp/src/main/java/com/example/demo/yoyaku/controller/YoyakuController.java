package com.example.demo.yoyaku.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.yoyaku.model.SelectMenus;
import com.example.demo.yoyaku.model.YoyakuRirekiForm;
import com.example.demo.yoyaku.service.YoyakuServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/yoyaku")
public class YoyakuController {

	private final YoyakuServiceImpl yoyakuServiceImpl;

	@GetMapping("/add/{dateTime}")
	public String addYoyaku(@ModelAttribute("yoyakuInfo") YoyakuRirekiForm yoyakuRirekiForm, Model model, @PathVariable String dateTime) {
		// 画面で選択された年月日時分を、年月日と時分に区切る(例「2021-11-23 1500」→「2021-11-23」「1500」)
		String[] dateTimeArr = dateTime.split(" ");
		yoyakuRirekiForm.setDate(dateTimeArr[0]);
		yoyakuRirekiForm.setStartTime(dateTimeArr[1]);
		model.addAttribute("yoyakuInfo", yoyakuServiceImpl.init(yoyakuRirekiForm));
		return "yoyaku/add";
	}

	@PostMapping("/register")
	public String register(@Validated @ModelAttribute("yoyakuInfo") YoyakuRirekiForm yoyakuRirekiForm, Model model,
			BindingResult result, Authentication loginUser) {
		if (result.hasErrors()) {
			return "yoyaku/add";
		}
		// 登録処理
		yoyakuServiceImpl.register(yoyakuRirekiForm, loginUser.getName());

		return "redirect:/";
	}

	@GetMapping("/edit/{yoyakuId}")
	public String editYoyaku(Model model, @PathVariable String yoyakuId, SelectMenus selectMenus) {
		// 画面に表示する項目の値取得する
		// カレンダーで選択された予約情報を取得して、編集画面で表示する
		YoyakuRirekiForm yoyakuRirekiForm = new YoyakuRirekiForm();
		yoyakuServiceImpl.getYoyakuById(yoyakuRirekiForm, yoyakuId);
		model.addAttribute("yoyakuInfo", yoyakuServiceImpl.init(yoyakuRirekiForm));
		selectMenus.setSelectMenus(yoyakuRirekiForm.getSelectMenuId());
		model.addAttribute("selectMenus", selectMenus);
		return "yoyaku/edit";
	}
}
