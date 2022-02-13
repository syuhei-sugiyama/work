package com.example.demo.yoyaku.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.waitingList.model.WaitingListForm;
import com.example.demo.waitingList.model.WaitingListForm.ConvertToYoyakuGroup;
import com.example.demo.yoyaku.model.YoyakuRirekiForm;
import com.example.demo.yoyaku.service.YoyakuServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/yoyaku")
public class YoyakuController {

	private final YoyakuServiceImpl yoyakuServiceImpl;

	@GetMapping("/add/{dateTime}")
	public String addYoyaku(@ModelAttribute("yoyakuInfo") YoyakuRirekiForm yoyakuRirekiForm, Model model,
			@PathVariable String dateTime) {
		// 画面で選択された年月日時分を、年月日と時分に区切る(例「2021-11-23 1500」→「2021-11-23」「1500」)
		String[] dateTimeArr = dateTime.split(" ");
		yoyakuRirekiForm.setDate(dateTimeArr[0]);
		yoyakuRirekiForm.setStartTime(dateTimeArr[1]);
		model.addAttribute("yoyakuInfo", yoyakuRirekiForm);
		model.addAttribute("screenItems", yoyakuServiceImpl.getScreenItems());
		return "yoyaku/add";
	}

	@PostMapping("/register")
	public String register(@Validated @ModelAttribute("yoyakuInfo") YoyakuRirekiForm yoyakuRirekiForm, Model model,
			BindingResult result) {
		if (result.hasErrors()) {
			return "yoyaku/add";
		}
		// 登録処理
		yoyakuServiceImpl.register(yoyakuRirekiForm);

		return "redirect:/";
	}

	@GetMapping("/edit/{yoyakuId}")
	public String editYoyaku(Model model, @PathVariable String yoyakuId) {
		// カレンダーで選択された予約情報を取得して、編集画面で表示する
		YoyakuRirekiForm yoyakuRirekiForm = new YoyakuRirekiForm();
		yoyakuServiceImpl.getYoyakuById(yoyakuRirekiForm, yoyakuId);
		model.addAttribute("yoyakuInfo", yoyakuRirekiForm);
		model.addAttribute("screenItems", yoyakuServiceImpl.getScreenItems());
		return "yoyaku/edit";
	}

	@PostMapping("/update")
	public String updateYoyaku(@Validated @ModelAttribute("yoyakuInfo") YoyakuRirekiForm yoyakuRirekiForm, Model model,
			BindingResult result) {
		if (result.hasErrors()) {
			return "yoyaku/edit";
		}
		// 更新処理
		yoyakuServiceImpl.update(yoyakuRirekiForm);
		return "redirect:/";
	}

	@GetMapping("/delete/{yoyakuId}")
	public String deleteYoyaku(@PathVariable String yoyakuId) {
		if (yoyakuId == null || yoyakuId.isBlank()) {
			return "yoyaku/edit";
		}
		// 削除処理
		yoyakuServiceImpl.delete(yoyakuId);
		return "redirect:/";
	}

	/**
	 * 【機能】キャンセル待ち情報を使って予約登録画面を表示する
	 * @param waitingListForm キャンセル待ち情報が格納されたフォーム
	 * @param model モデル
	 * @param result フォームバリデーションチェック結果
	 * @return 予約登録画面
	 */
	@PostMapping("/convertToYoyaku")
	public String convertWListToYoyaku(
			@Validated(ConvertToYoyakuGroup.class) @ModelAttribute("waitingListInfo") WaitingListForm waitingListForm,
			Model model,
			BindingResult result) {
		if (result.hasErrors()) {
			return "waitingList/edit";
		}
		YoyakuRirekiForm yoyakuRirekiForm = new YoyakuRirekiForm();
		BeanUtils.copyProperties(waitingListForm, yoyakuRirekiForm);
		model.addAttribute("yoyakuInfo", yoyakuRirekiForm);
		model.addAttribute("screenItems", yoyakuServiceImpl.getScreenItems());
		return "yoyaku/add";
	}
}
