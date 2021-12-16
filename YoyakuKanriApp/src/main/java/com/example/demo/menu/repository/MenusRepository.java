package com.example.demo.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.menu.model.Menus;

public interface MenusRepository extends JpaRepository<Menus, String> {

	List<Menus> findByMenuName(String menuName);

	Menus findByMenuId(String menuId);
}
