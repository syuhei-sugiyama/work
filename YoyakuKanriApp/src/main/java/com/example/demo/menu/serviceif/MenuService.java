package com.example.demo.menu.serviceif;

import java.util.Map;

import com.example.demo.menu.model.Menus;

public interface MenuService {

	void delete(String menuId);

	Map<String, String> getAllMenuMap();

	Menus findByMenuId(String menuId);

	void add(Menus formMenu);

	void update(Menus formMenu);
}
