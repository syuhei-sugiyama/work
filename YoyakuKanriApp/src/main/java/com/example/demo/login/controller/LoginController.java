package com.example.demo.login.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.user.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {

	private final UserServiceImpl userServiceImpl;

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("users", userServiceImpl.findByUserId(null));
		return "login/login";
	}

	@GetMapping("/")
	public String loginSuccess(Authentication loginUser, Model model) {
		return "yoyaku/yoyakukanri";
	}

}