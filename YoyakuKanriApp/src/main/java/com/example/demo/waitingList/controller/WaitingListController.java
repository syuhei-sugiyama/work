package com.example.demo.waitingList.controller;

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

import com.example.demo.waitingList.model.WaitingListForm;
import com.example.demo.waitingList.service.WaitingListServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/waitingList")
public class WaitingListController {

	private final WaitingListServiceImpl waitingListServiceImpl;

	@GetMapping("/index")
	public String index(Model model) {
		return "waitingList/index";
	}

	@GetMapping("/add/{dateTime}")
	public String addWaitingList(@PathVariable String dateTime, Model model, WaitingListForm waitingListForm) {
		String[] dateTimeArr = dateTime.split(" ");
		waitingListForm.setDate(dateTimeArr[0]);
		waitingListForm.setStartTime(dateTimeArr[1]);
		model.addAttribute("waitingListInfo", waitingListServiceImpl.prepareItemOfRegisterScreen(waitingListForm));
		return "waitingList/add";
	}

	@PostMapping("/register")
	public String register(@Validated @ModelAttribute("waitingListInfo") WaitingListForm waitingListForm,
			BindingResult result, Authentication loginUser) {
		if (result.hasErrors()) {
			return "waitingList/add";
		}
		// 登録処理
		waitingListServiceImpl.register(waitingListForm, loginUser.getName());
		return "redirect:/waitingList/index";
	}
}
