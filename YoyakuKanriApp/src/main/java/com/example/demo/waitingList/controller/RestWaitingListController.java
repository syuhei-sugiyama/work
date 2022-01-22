package com.example.demo.waitingList.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.waitingList.service.WaitingListServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/waitingList")
public class RestWaitingListController {

	private final WaitingListServiceImpl waitingListServiceImpl;

	@GetMapping(value = "/get/all")
	public String getAllWaitingList() {
		return waitingListServiceImpl.getAllWaitingList();
	}
}
