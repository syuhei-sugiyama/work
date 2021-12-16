package com.example.demo.login.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login/login";
	}

	@GetMapping("/")
	public String loginSuccess(Authentication loginUser, Model model) {
		return "yoyaku/yoyakukanri";
	}

}