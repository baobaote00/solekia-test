package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import lombok.Data;

/**
 * 学生情報リスト データリクエスト
 *
 */
@Data
public class StudentListParam {

	/**
	 * コンストラクタ
	 */
	public StudentListParam() {
		dataList = new ArrayList<StudentRequest>();
	}
	
	/**
	 * 学生情報リスト
	 */
	@Valid
	private List<StudentRequest> dataList; 
	
}//end of class
