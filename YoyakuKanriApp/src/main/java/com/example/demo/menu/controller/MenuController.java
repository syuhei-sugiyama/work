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

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/menu")
public class MenuController {

	private final MenusRepository menusRepository;

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
		// メニュー登録
		menuServiceImpl.add(formMenu);

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
		model.addAttribute("formMenu", menusRepository.findByMenuId(id));
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
		// メニュー更新
		menuServiceImpl.update(formMenu);

		return "redirect:/menu/index";
	}

	@GetMapping("/delete/{id}")
	public String deleteConfirmation(@PathVariable String id, Model model) {
		model.addAttribute("deleteMenu", menusRepository.findByMenuId(id));
		return "menu/delete";
	}

	@PostMapping("/delete")
	public String delete(@ModelAttribute("deleteMenu") Menus deleteMenu) {
		menuServiceImpl.delete(deleteMenu.getMenuId());
		return "redirect:/menu/index";
	}
}
