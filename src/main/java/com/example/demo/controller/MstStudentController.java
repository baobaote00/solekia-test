package com.example.demo.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.StudentListParam;
import com.example.demo.dto.StudentRequest;
import com.example.demo.service.MstStudentService;

/**
 * クラス情報 Controller
 */
@Controller
public class MstStudentController {
	public Map<String, Integer> initRadioGender() {
		Map<String, Integer> radio = new LinkedHashMap<>();
		radio.put("男", 1);
		radio.put("女性", 2);
		radio.put("他の", 3);
		return radio;
	}

	/**
	 * 学生情報 Service
	 */
	@Autowired
	private MstStudentService mstStudentService;

	/**
	 * Display the student information list screen
	 * 
	 * @param model Model
	 * @return Student information list screen
	 */
	@GetMapping(value = "/mstStudent/list")
	public String displayList(Model model) {
		StudentListParam listMstStudent = mstStudentService.searchAll();
		model.addAttribute("listMstStudent", listMstStudent);

		return "MstStudent/list";
	}

	/**
	 * 学生情報一覧更新
	 * @param userListParam
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/mstStudent/listUpdate")
	public String updateList(@Validated @ModelAttribute StudentListParam userListParam, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				if (!errorList.contains(error.getDefaultMessage())) {
					errorList.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("listMstStudent", userListParam);
			model.addAttribute("validationError", errorList);
			return "mstStudent/list";
		}
		
		// 学生情報の更新
		mstStudentService.updateAll(userListParam);
		return "redirect:/mstStudent/list";
	}

	@GetMapping("/mstStudent/delete/{id}")
	public String displayView(@PathVariable Long id, Model model) {
		mstStudentService.delete(id);

		return "redirect:/mstStudent/list";
	}

	@GetMapping(value = "/mstStudent/add")
	public String displayAdd(Model model) {
		StudentRequest studentRequest = new StudentRequest();
		studentRequest.setDataStatus(0);
		model.addAttribute("studentRequest", studentRequest);
		return "MstStudent/add";
	}

	/**
	 * 学生情報一覧更新
	 * @param userListParam
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/mstStudent/create")
	public String create(@Validated @ModelAttribute StudentRequest studentRequest, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				if (!errorList.contains(error.getDefaultMessage())) {
					errorList.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("listMstStudent", studentRequest);
			model.addAttribute("validationError", errorList);
			return "mstStudent/add";
		}
		
		// 学生情報の更新
		mstStudentService.create(studentRequest);
		return "redirect:/mstStudent/list";
	}

}
