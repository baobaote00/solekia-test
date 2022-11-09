package com.example.demo.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
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

import com.example.demo.dto.ClassRequest;
import com.example.demo.dto.StudentRequest;
import com.example.demo.entity.MstClass;
import com.example.demo.service.MstClassService;
import com.example.demo.service.MstStudentService;

/**
 * クラス情報 Controller
 */
@Controller
public class MstClassController {

	public Map<String, Integer> initRadioStatus() {
		Map<String, Integer> radio = new LinkedHashMap<>();
		radio.put("有効", 0);
		radio.put("無効", 1);
		return radio;
	}

	/**
	 * クラス情報 Service
	 */
	@Autowired
	private MstClassService mstClassService;

	@Autowired
	private MstStudentService mstStudentService;
	ModelMapper modelMapper = new ModelMapper();

	/**
	 * クラス情報一覧画面を表示
	 * 
	 * @param model Model
	 * @return クラス情報一覧画面
	 */
	@GetMapping(value = "/mstClass/list")
	public String displayList(Model model) {
		List<MstClass> mstClasslist = mstClassService.searchAll();
		model.addAttribute("mstClasslist", mstClasslist);

		return "MstClass/list";
	}

	/**
	 * クラス新規登録画面を表示
	 * 
	 * @param model Model
	 * @return クラス情報一覧画面
	 */
	@GetMapping(value = "/mstClass/add")
	public String displayAdd(Model model) {
		ClassRequest classRequest = new ClassRequest();
		classRequest.setDataStatus(0);
		model.addAttribute("classRequest", classRequest);
		return "MstClass/add";
	}

	/**
	 * クラス情報詳細画面を表示
	 * 
	 * @param id    表示するクラスID
	 * @param model Model
	 * @return クラス情報詳細画面
	 */
	@GetMapping("/mstClass/{id}")
	public String displayView(@PathVariable Long id, Model model) {
		Optional<MstClass> mstClass = mstClassService.searchByID(id);

		ClassRequest classRequest;

		if (mstClass.isPresent()) {
			classRequest = modelMapper.map(mstClass.get(), ClassRequest.class);
		} else {
			classRequest = new ClassRequest();
			classRequest.setClassId(id);
			model.addAttribute("notFound", true);
		}

		model.addAttribute("type", initRadioStatus());
		model.addAttribute("classRequest", classRequest);
		model.addAttribute("studentRequest", mstStudentService.searchAll().getDataList());
		
		return "MstClass/detail";
	}

	/**
	 * create new class
	 * 
	 * @param classRequest request form client
	 * @param result       result error
	 * @return クラス情報詳細画面
	 */
	@RequestMapping(value = "/mstClass/create", method = RequestMethod.POST)
	public String createNewClass(@Validated @ModelAttribute ClassRequest classRequest, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			return "MstClass/add";
		}
		mstClassService.create(classRequest);
		return "redirect:/mstClass/list";
	}

	@RequestMapping(value = "/mstClass/update", method = RequestMethod.POST, params = "action=update")
	public String update(@Validated @ModelAttribute ClassRequest classRequest, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			model.addAttribute("classRequest", classRequest);
			model.addAttribute("type", initRadioStatus());

			return "MstClass/detail";
		}
		mstClassService.update(classRequest);
		return "redirect:/mstClass/list";
	}

	@RequestMapping(value = "/mstClass/update", method = RequestMethod.POST, params = "action=delete")
	public String delete(@Validated @ModelAttribute ClassRequest classRequest, BindingResult result, Model model) {
		try {
			mstClassService.delete(classRequest.getClassId());
		} catch (Exception e) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			errorList.add(e.getMessage());
			model.addAttribute("validationError", errorList);
			model.addAttribute("type", initRadioStatus());
			return "MstClass/detail";
		}
		return "redirect:/mstClass/list";
	}
}
