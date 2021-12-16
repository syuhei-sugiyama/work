package com.example.demo.yoyaku.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.yoyaku.service.YoyakuServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/event")
@RequiredArgsConstructor
public class RestYoyakuController {

	private final YoyakuServiceImpl yoyakuServiceImpl;

	@GetMapping(value = "/all")
	public String getAllYoyaku() {
		return yoyakuServiceImpl.getAllYoyaku();
	}
}
