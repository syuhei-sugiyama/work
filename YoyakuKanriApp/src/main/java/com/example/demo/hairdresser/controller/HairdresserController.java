package com.example.demo.hairdresser.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.hairdresser.model.Hairdresser;
import com.example.demo.hairdresser.model.Hairdresser.AddGroup;
import com.example.demo.hairdresser.model.Hairdresser.DeleteGroup;
import com.example.demo.hairdresser.model.Hairdresser.UpdateGroup;
import com.example.demo.hairdresser.repository.HairdresserRepository;
import com.example.demo.hairdresser.service.HairdresserServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/hairdresser")
public class HairdresserController {

	private final HairdresserRepository hairdresserRepository;

	private final HairdresserServiceImpl hairdresserServiceImpl;

	@GetMapping("/index")
	public String index(@ModelAttribute("formInfo") Hairdresser hairdresser, Model model) {
		return "hairdresser/index";
	}

	@PostMapping("/search")
	public String search(@Validated @ModelAttribute("formInfo") Hairdresser hairdresser,
			BindingResult result, Model model) {
		model.addAttribute("formInfo", hairdresser);
		if (result.hasErrors()) {
			return "hairdresser/index";
		}
		// 全検索かどうか
		if (hairdresser.getHairdresserName() != null &&
				!hairdresser.getHairdresserName().isEmpty()) {
			model.addAttribute("hairdressers",
					hairdresserRepository.findByHairdresserName(hairdresser.getHairdresserName()));
		} else {
			model.addAttribute("hairdressers", hairdresserRepository.findAll());
		}
		return "hairdresser/index";
	}

	@GetMapping("/register")
	public String register(@ModelAttribute("formInfo") Hairdresser hairdresser, Model model) {
		return "hairdresser/register";
	}

	@PostMapping("/add")
	public String add(@Validated(AddGroup.class) @ModelAttribute("formInfo") Hairdresser hairdresser,
			BindingResult result, Model model, Authentication loginUser) {
		// フォーム精査エラー
		if (result.hasErrors()) {
			model.addAttribute("formInfo", hairdresser);
			return "hairdresser/register";
		}

		// 美容師名存在チェック
		if (hairdresserRepository.findByHairdresserName(hairdresser.getHairdresserName()).size() > 0) {
			model.addAttribute("formInfo", hairdresser);
			result.addError(new FieldError(result.getObjectName(), "hairdresserName", "入力された美容師名は既に登録されています。"));
			return "hairdresser/register";
		}

		// 美容師情報登録
		hairdresserServiceImpl.addHairdresser(hairdresser, loginUser.getName());

		return "redirect:/hairdresser/index";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		model.addAttribute("formInfo", hairdresserRepository.findByHairdresserId(id));
		return "hairdresser/edit";
	}

	@PostMapping("/update")
	public String update(@Validated(UpdateGroup.class) @ModelAttribute("formInfo") Hairdresser hairdresser,
			BindingResult result, Model model, Authentication loginUser) {
		// フォーム精査エラー
		if (result.hasErrors()) {
			model.addAttribute("formInfo", hairdresser);
			return "hairdresser/edit";
		}

		// 美容師名存在チェック
		if (hairdresserRepository.findByHairdresserName(hairdresser.getHairdresserName()).size() > 0) {
			List<String> errorList = new ArrayList<String>();
			errorList.add("入力された美容師名は既に登録されています。");
			model.addAttribute("validationError", errorList);
			model.addAttribute("formInfo", hairdresser);
			return "hairdresser/edit";
		}

		// 美容師情報更新
		hairdresserServiceImpl.updateHairdresser(hairdresser, loginUser.getName());

		return "redirect:/hairdresser/index";
	}

	@GetMapping("/delete/{id}")
	public String deleteConfirmation(@PathVariable String id, Model model) {
		model.addAttribute("deleteInfo", hairdresserRepository.findByHairdresserId(id));
		return "hairdresser/delete";
	}

	@PostMapping("/delete")
	public String delete(@Validated(DeleteGroup.class) @ModelAttribute("deleteInfo") Hairdresser hairdresser) {
		hairdresserServiceImpl.deleteHairdresser(hairdresser.getHairdresserId());
		return "redirect:/hairdresser/index";
	}
}
