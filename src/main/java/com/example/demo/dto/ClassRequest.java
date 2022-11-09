package com.example.demo.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.demo.entity.MstStudent;

import lombok.Data;

/**
 * クラス情報 リクエストデータ
 *
 */
@Data
public class ClassRequest implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

   /**
   * クラスID
   */
  @Min(value = 0,message = "ok")
  private Long classId;
  /**
   * クラス名
   */
  @NotEmpty(message = "クラス名を入力してください")
  @Size(max = 64, message = "クラス名は64文字以内で入力してください")
  private String className;
  /**
   * データ状態(0:valid、1:deleted)
   */
  @Min(value=0, message="データ状態を有効か無効かどちらか選択してください")
	@Max(value=1, message="データ状態を有効か無効かどちらか選択してください")
  private Integer dataStatus;

  private List<MstStudent> students;

  private List<String> studentsId;
}
