package com.example.demo.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.StudentListParam;
import com.example.demo.dto.StudentRequest;
import com.example.demo.entity.MstStudent;
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
	 * クラス新規登録画面を表示
	 * 
	 * @param model Model
	 * @return クラス情報一覧画面
	 */
	@GetMapping(value = "/mstStudent/add")
	public String displayAdd(Model model) {
		StudentRequest studentRequest = new StudentRequest();
		studentRequest.setDataStatus(0);
		model.addAttribute("studentRequest", studentRequest);
		model.addAttribute("gender", initRadioGender());
		return "MstStudent/add";
	}

	/**
	 * クラス情報詳細画面を表示
	 * 
	 * @param id    表示するクラスID
	 * @param model Model
	 * @return クラス情報詳細画面
	 */
	@GetMapping("/mstStudent/{id}")
	public String displayView(@PathVariable Long id, Model model) {
		Optional<MstStudent> mstStudent = mstStudentService.searchByID(id);
		if (mstStudent != null) {
			model.addAttribute("mstStudent", mstStudent.get());
		}
		return "MstStudent/detail";
	}

	/**
	 * create new class
	 * 
	 * @param classRequest request form client
	 * @param result       result error
	 * @return クラス情報詳細画面
	 */
	@RequestMapping(value = "/mstStudent/create", method = RequestMethod.POST)
	public String createNewStudent(@Validated @ModelAttribute StudentRequest studentRequest, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			return "MstStudent/add";
		}
		mstStudentService.create(studentRequest);
		return "redirect:/mstStudent/list";
	}

	/**
	 * delete a class
	 * 
	 * @param id    id of class delete
	 * @param model send data
	 * @return
	 */
	@GetMapping("/mstStudent/delete/{id}")
	public String delete(@PathVariable Long id, Model model) {
		mstStudentService.delete(id);
		return "redirect:/mstStudent/list";
	}
}
