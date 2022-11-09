package com.example.demo.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * クラス情報 Entity
 */
@Entity
@Data
@Table(name = "mst_class")
public class MstClass {
	public static Map<Integer, String> initRadioStatus() {
		Map<Integer, String> radio = new LinkedHashMap<>();
		radio.put(0, "有効");
		radio.put(1, "無効");
		return radio;
	}

	/**
	 * クラスID
	 */
	@Id
	@Column(name = "class_id", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long classId;
	/**
	 * クラス名
	 */
	@Column(name = "class_name")
	private String className;
	/**
	 * データ状態(0:valid、1:deleted)
	 */
	@Column(name = "data_status")
	private Integer dataStatus;

	public String getDataStatusText() {
		return MstClass.initRadioStatus().get(dataStatus);
	}

	/**
	 * 登録日時
	 */
	@Column(name = "registered_date", updatable = false)
	private Date registeredDate;
	/**
	 * 更新日時
	 */
	@Column(name = "updated_date")
	private Date updatedDate;

	public String getRegisteredDate() {
		return new SimpleDateFormat("yyyy/MM/dd").format(this.registeredDate);
	}

	public String getUpdatedDate() {
		return new SimpleDateFormat("yyyy/MM/dd").format(this.updatedDate);
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(name = "trn_enrollement", joinColumns = { @JoinColumn(name = "class_id") }, inverseJoinColumns = {
			@JoinColumn(name = "student_id") })
	private List<MstStudent> students = new ArrayList<>();

	@Override
	public String toString() {
		return this.classId + " " + this.className + " " + this.dataStatus;
	}
}
