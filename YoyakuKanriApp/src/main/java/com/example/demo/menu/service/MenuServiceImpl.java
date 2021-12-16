package com.example.demo.menu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.menu.model.Menus;
import com.example.demo.menu.repository.MenusRepository;
import com.example.demo.menu.serviceif.MenuService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

	private final MenusRepository menusRepository;

	@Override
	public void delete(String menuId) {
		menusRepository.deleteById(menuId);
	}

	@Override
	public Map<String, String> getAllMenuMap() {
		List<Menus> menuList = menusRepository.findAll();
		Map<String, String> resultMap = new HashMap<String, String>();
		for (Menus menus : menuList) {
			resultMap.put(menus.getMenuId(), menus.getMenuName());
		}
		return resultMap;
	}

	@Override
	public Menus findByMenuId(String menuId) {
		return menusRepository.findByMenuId(menuId);
	}
}
