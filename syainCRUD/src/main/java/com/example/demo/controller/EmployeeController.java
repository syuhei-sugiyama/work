package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class EmployeeController {

	private final EmployeeRepository employeeRepository;

	private final DepartmentRepository departmentRepository;

	@GetMapping("/")
	public String showList(Model model) {
		model.addAttribute("employees", employeeRepository.findAll());
		return "index";
	}

	@GetMapping("/add")
	public String addEmployee(@ModelAttribute Employee employee) {
		return "form";
	}

	@GetMapping("/add_busho")
	public String addDepartment(@ModelAttribute Department department) {
		return "bushoForm";
	}

	@PostMapping("/process")
	public String process(@Validated @ModelAttribute Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			return "form";
		}
		employeeRepository.save(employee);
		return "redirect:/";
	}

	@PostMapping("/depProcess")
	public String depProcess(@Validated @ModelAttribute Department department, BindingResult result) {
		if (result.hasErrors()) {
			return "bushoForm";
		}
		departmentRepository.save(department);
		return "redirect:/";
	}

	/*
	 * @PathVariable
	 * パスの値を変数に格納することができるアノテーション
	 * 例えば、「http://localhost:8080/edit/2」というパスでアクセスした際に
	 * 「2」という値を取得できる
	 */
	@GetMapping("/edit/{id}")
	public String editEmployee(@PathVariable Long id, Model model) {
		model.addAttribute("employee", employeeRepository.findById(id));
		return "form";
	}

	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		employeeRepository.deleteById(id);
		return "redirect:/";
	}
}
