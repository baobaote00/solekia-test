package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.dto.StudentListParam;
import com.example.demo.dto.StudentRequest;
import com.example.demo.entity.MstStudent;
import com.example.demo.repository.MstStudentRepository;

/**
 * クラス情報 Service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MstStudentService {

	ModelMapper modelMapper = new ModelMapper();
	/**
	 * クラス情報 Repository
	 */
	@Autowired
	private MstStudentRepository mstStudentRepository;

	/**
	 * クラス情報 全検索
	 * 
	 * @return 検索結果
	 */
	@ModelAttribute("mstStudent")
	public StudentListParam searchAll() {
		List<MstStudent> allDataList = mstStudentRepository.findAll();

		StudentListParam retData = new StudentListParam();
		retData.setDataList(modelMapper.map(allDataList, new TypeToken<List<StudentRequest>>() {
		}.getType()));

		return retData;
	}

	@ModelAttribute("mstStudent")
	public List<MstStudent> updateAll(StudentListParam studentListParam) {

		Date now = new Date();
		List<StudentRequest> allDataList = studentListParam.getDataList();
		List<MstStudent> retData = new ArrayList<MstStudent>();

		for (StudentRequest dataItem : allDataList) {

			MstStudent newParam = new MstStudent();
			BeanUtils.copyProperties(dataItem, newParam);
			newParam.setUpdatedDate(now);
			retData.add(newParam);

		}

		return mstStudentRepository.saveAll(retData);
	}

	@ModelAttribute("mstStudent")
	public Optional<MstStudent> searchByID(Long id) {
		return mstStudentRepository.findById(id);
	}

	@ModelAttribute("mstStudent")
	public MstStudent create(StudentRequest studentRequest) {
		Date now = new Date();
		MstStudent mstStudent = modelMapper.map(studentRequest, MstStudent.class);
		mstStudent.setUpdatedDate(now);
		mstStudent.setRegisteredDate(now);
		return mstStudentRepository.save(mstStudent);
	}

	@ModelAttribute("mstStudent")
	public void delete(Long id) {
		mstStudentRepository.deleteById(id);
	}
}
