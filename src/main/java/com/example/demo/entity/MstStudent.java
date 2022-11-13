package com.example.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Entity
@Data
@Table(name = "mst_student")
public class MstStudent implements Serializable {
	public static Map<Integer, String> initRadioGender() {
		Map<Integer, String> radio = new LinkedHashMap<>();
		radio.put(1, "男");
		radio.put(2, "女性");
		radio.put(3, "他の");
		return radio;
	}

	/**
	 * 学生ID
	 */
	@Id
	@Column(name = "student_id", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentId;
	/**
	 * 学生名
	 */
	@Column(name = "student_name")
	private String studentName;
	/**
	 * 学生性的（1:male、2:female）
	 */
	@Column(name = "student_sex")
	private Integer studentSex;
	/**
	 * 学生年齢（6歳以上）
	 */
	@Column(name = "student_age")
	private Integer studentAge;
	/**
	 * データ状態(0:valid、1:deleted)
	 */
	@Column(name = "data_status")
	private Integer dataStatus;
	/**
	 * 登録日時
	 */
	@Column(name = "registered_date", updatable = false)
	private Date registeredDate;
	/**
	 * 登録日時
	 */
	@Column(name = "updated_date")
	private Date updatedDate;

	@JsonIgnore
	@ManyToMany(mappedBy = "students")
	private Collection<MstClass> className = new ArrayList<>();

	public String getDataStatusText() {
		return MstClass.initRadioStatus().get(dataStatus);
	}

	public String getStudentSexText() {
		return MstStudent.initRadioGender().get(studentSex);
	}
}
