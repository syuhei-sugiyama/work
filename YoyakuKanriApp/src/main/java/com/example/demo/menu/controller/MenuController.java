package com.example.demo.menu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.menu.model.Menus;
import com.example.demo.menu.model.Menus.AddGroup;
import com.example.demo.menu.model.Menus.UpdateGroup;
import com.example.demo.menu.repository.MenusRepository;
import com.example.demo.menu.service.MenuServiceImpl;
import com.example.demo.util.model.UtilColumn;
import com.example.demo.util.service.SaibanServiceImpl;
import com.example.demo.util.service.UtilColumnServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/menu")
public class MenuController {

	private final MenusRepository menusRepository;

	private final SaibanServiceImpl saibanServiceImpl;

	private final UtilColumnServiceImpl utilColumnServiceImpl;

	private final MenuServiceImpl menuServiceImpl;

	@GetMapping("/index")
	public String index(@ModelAttribute("formMenu") Menus formMenu, Model model) {
		return "menu/index";
	}

	@GetMapping("/register")
	public String register(@ModelAttribute("formMenu") Menus formMenu, Model model) {
		model.addAttribute("formMenu", formMenu);
		return "menu/register";
	}

	@PostMapping("/add")
	public String add(@Validated(AddGroup.class) @ModelAttribute("formMenu") Menus formMenu, BindingResult result, Authentication loginUser, Model model) {
		/*
		 * フォームの精査エラー
		 */
		if (result.hasErrors()) {
			return "menu/register";
		}

		/*
		 * 同名のメニュー名が存在する場合
		 */
		if (menusRepository.findByMenuName(formMenu.getMenuName()).size() > 0) {
			result.addError(new FieldError(result.getObjectName(), "menuName", "入力されたメニュー名は既に登録されています。"));
			return "menu/register";
		}
		/*
		 * 採番テーブルを使った、メニューIDの生成
		 */
		formMenu.setMenuId(saibanServiceImpl.createId("MN", loginUser.getName()));
		/*
		 * 共通機能を使った、作成日時などの4つのカラムの値生成
		 */
		UtilColumn utilColumnVal = utilColumnServiceImpl.createUtilColumnValue(loginUser.getName());
		formMenu.setCreateBy(utilColumnVal.getCreateBy());
		formMenu.setCreateTime(utilColumnVal.getCreateTime());
		formMenu.setUpdateBy(utilColumnVal.getUpdateBy());
		formMenu.setUpdateTime(utilColumnVal.getUpdateTime());
		/*
		 * メニューテーブルへ登録
		 */
		menusRepository.save(formMenu);

		return "redirect:/menu/index";
	}

	@PostMapping("/search")
	public String search(@Validated @ModelAttribute("formMenu") Menus formMenu, BindingResult result, Model model) {
		model.addAttribute("formMenu", formMenu);
		if (result.hasErrors()) {
			return "menu/index";
		}
		// 全検索かどうか
		if (formMenu.getMenuName() != null &&
				!formMenu.getMenuName().isEmpty()) {
			model.addAttribute("menus", menusRepository.findByMenuName(formMenu.getMenuName()));
		} else {
			model.addAttribute("menus", menusRepository.findAll());
		}
		return "menu/index";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		Menus editMenu = menusRepository.findByMenuId(id);
		model.addAttribute("formMenu", editMenu);
		return "menu/edit";
	}

	@PostMapping("/update")
	public String update(@Validated(UpdateGroup.class) @ModelAttribute("formMenu") Menus formMenu, BindingResult result,
			Authentication loginUser, Model model) {
		// フォーム精査エラー
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				if (!errorList.contains(error.getDefaultMessage())) {
					errorList.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("validationError", errorList);
			model.addAttribute("formMenu", formMenu);
			return "menu/edit";
		}
		/*
		 * 登録済みの更新対象データを取得
		 */
		Menus updateMenu = menusRepository.findByMenuId(formMenu.getMenuId());

		// 画面から入力された金額、所要時間をセット
		updateMenu.setPrice(formMenu.getPrice());
		updateMenu.setRequiredTime(formMenu.getRequiredTime());

		// 共通機能を使って、更新者、更新日時の値生成
		UtilColumn utilColumnVal = utilColumnServiceImpl.createUtilColumnValue(loginUser.getName());
		// 更新者、更新日時をセット
		updateMenu.setUpdateBy(utilColumnVal.getUpdateBy());
		updateMenu.setUpdateTime(utilColumnVal.getUpdateTime());

		// メニューテーブルの更新
		menusRepository.save(updateMenu);

		return "redirect:/menu/index";
	}

	@GetMapping("/delete/{id}")
	public String deleteConfirmation(@PathVariable String id, Model model) {
		Menus deleteMenu = menusRepository.findByMenuId(id);
		model.addAttribute("deleteMenu", deleteMenu);
		return "menu/delete";
	}

	@PostMapping("/delete")
	public String delete(@ModelAttribute("deleteMenu") Menus deleteMenu) {
		menuServiceImpl.delete(deleteMenu.getMenuId());
		return "redirect:/menu/index";
	}
}
