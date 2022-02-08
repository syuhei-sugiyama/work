package com.example.demo.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.login.model.Users;
import com.example.demo.login.model.Users.DeleteGroup;
import com.example.demo.login.model.Users.UpdateGroup;
import com.example.demo.user.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {


	private final UserServiceImpl userServiceImpl;

	@GetMapping("/index")
	public String index(@ModelAttribute("userInfo") Users user, Model model) {
		return "user/index";
	}

	@PostMapping("/search")
	public String search(@Validated @ModelAttribute("userInfo") Users users,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user/index";
		}
		model.addAttribute("users", userServiceImpl.findByUserId(users.getUserId()));
		return "user/index";
	}

	@GetMapping("/register")
	public String register(@ModelAttribute("user") Users user, Model model) {
		return "user/register";
	}

	@PostMapping("/add")
	public String add(@Validated @ModelAttribute("user") Users user, Model model,
			BindingResult result) {
		if (result.hasErrors()) {
			return "user/register";
		}
		// 登録処理
		userServiceImpl.addUser(user);
		return "redirect:/user/index";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		model.addAttribute("user", userServiceImpl.findByUserId(id).get(0));
		return "user/edit";
	}

	@PostMapping("/update")
	public String update(@Validated(UpdateGroup.class) @ModelAttribute("user") Users user, Model model,
			BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "user/edit";
		}

		// ユーザ情報更新
		userServiceImpl.updateUser(user);

		return "redirect:/user/index";
	}

	@GetMapping("/delete/{id}")
	public String deleteConfirmation(@PathVariable String id, Model model) {
		model.addAttribute("user", userServiceImpl.findByUserId(id).get(0));
		return "user/delete";
	}

	@PostMapping("/delete")
	public String delete(@Validated(DeleteGroup.class) @ModelAttribute("user") Users user, BindingResult result) {
		if (result.hasErrors()) {
			return "user/delete";
		}
		// ユーザ削除
		userServiceImpl.deleteUser(user.getUserId());
		return "redirect:/user/index";
	}

	@GetMapping("/registerOfSystemUser")
	public String registerOfSystemUser() {
		userServiceImpl.registerOfSystemUser();
		return "redirect:/";
	}
}
