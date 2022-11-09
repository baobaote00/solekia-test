package com.example.demo.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * クラス情報 リクエストデータ
 *
 */
@Data
public class StudentRequest implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

   /**
	 * 学生ID
	 * 
	 */
	@Min(value=0, message="学生IDは、0以上の数値に設定しないといけません")
	private Long studentId;
	/**
	 * 名前
	 */
	@NotEmpty(message = "学生名が未入力です")
	@Size(max = 64, message = "学生名は64文字以内で入力してください")
	private String studentName;
	/**
	 * 性的（1:male、2:female）
	 */
	@Min(value=1, message="性的を男か女かどちらか選択してください")
	@Max(value=2, message="性的を男か女かどちらか選択してください")
	private Integer studentSex;
	/**
	 * 年齢
	 */
	@Min(value=6, message="年齢を6～100歳の範囲に入力してください")
	@Max(value=100, message="年齢を6～100歳の範囲に入力してください")
	private Integer studentAge;
	
	/**
	 * データ状態(0:valid、1:deleted)
	 */
	@Min(value=0, message="データ状態を有効か無効かどちらか選択してください")
	@Max(value=1, message="データ状態を有効か無効かどちらか選択してください")
	private Integer dataStatus;

  /**
	 * 登録日時
	 */
	private Date registeredDate;
	/**
	 * 登録日時
	 */
	private Date updatedDate;
}
