package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.dto.ClassRequest;
import com.example.demo.entity.MstClass;
import com.example.demo.entity.MstStudent;
import com.example.demo.repository.MstClassRepository;
import com.example.demo.repository.MstStudentRepository;

/**
 * クラス情報 Service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MstClassService {

	static Logger logger = LoggerFactory.getLogger(MstClassService.class);
	ModelMapper modelMapper = new ModelMapper();
	/**
	 * クラス情報 Repository
	 */
	@Autowired
	private MstClassRepository mstClassRepository;
	@Autowired
	private MstStudentRepository mstStudentRepository;
	/**
	 * クラス情報 全検索
	 * 
	 * @return 検索結果
	 */
	@ModelAttribute("mstClass")
	public List<MstClass> searchAll() {
		return mstClassRepository.findAll();
	}

	@ModelAttribute("mstClass")
	public Optional<MstClass> searchByID(Long id) {
		return mstClassRepository.findById(id);
	}

	@ModelAttribute("mstClass")
	public MstClass create(ClassRequest classRequest) {
		Date now = new Date();
		MstClass mstClass = modelMapper.map(classRequest, MstClass.class);
		mstClass.setRegisteredDate(now);
		mstClass.setUpdatedDate(now);
		return mstClassRepository.save(mstClass);
	}

	@ModelAttribute("mstClass")
	public MstClass update(ClassRequest classRequest) {
		Date now = new Date();
		MstClass mstClass = modelMapper.map(classRequest, MstClass.class);
		mstClass.setUpdatedDate(now);
		return mstClassRepository.save(mstClass);
	}

	@ModelAttribute("mstClass")
	public MstClass updateClassStudent(ClassRequest classRequest) {
		Date now = new Date();
		MstClass mstClass = modelMapper.map(classRequest, MstClass.class);
		mstClass.setUpdatedDate(now);
		mstClass.setRegisteredDate(now);

		if (mstClass.getStudents() == null) {
			mstClass.setStudents(new ArrayList<>());
		}

		for (String studentId : classRequest.getStudentsId()) {
			Optional<MstStudent> student = mstStudentRepository.findById(Long.parseLong(studentId)); 
			if (student.isPresent()) {
				mstClass.getStudents().add(student.get());
			}
		}

		return mstClassRepository.save(mstClass);
	}

	@ModelAttribute("mstClass")
	public void delete(Long id) {
		mstClassRepository.deleteById(id);
	}
}
