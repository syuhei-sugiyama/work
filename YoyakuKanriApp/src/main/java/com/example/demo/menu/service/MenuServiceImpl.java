package com.example.demo.menu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.menu.MenuConst;
import com.example.demo.menu.model.Menus;
import com.example.demo.menu.repository.MenusRepository;
import com.example.demo.menu.serviceif.MenuService;
import com.example.demo.util.service.SaibanServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

	private final MenusRepository menusRepository;

	private final SaibanServiceImpl saibanServiceImpl;

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

	@Transactional
	@Override
	public void add(Menus formMenu) {
		// 採番テーブルを使った、メニューIDの生成
		formMenu.setMenuId(saibanServiceImpl.createId(MenuConst.MENU_ID));

		// メニューテーブルへ登録
		menusRepository.save(formMenu);
	}

	@Transactional
	@Override
	public void update(Menus formMenu) {
		// 登録済みの更新対象データを取得
		Menus updateMenu = menusRepository.findByMenuId(formMenu.getMenuId());

		// 画面から入力された金額、所要時間をセット
		updateMenu.setPrice(formMenu.getPrice());
		updateMenu.setRequiredTime(formMenu.getRequiredTime());

		// メニューテーブルの更新
		menusRepository.save(updateMenu);
	}
}
