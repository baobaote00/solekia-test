package com.example.demo.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class MstClassStudentId implements Serializable {

  @Column(name = "class_id")
  private Long classId;

  @Column(name = "student_id")
  private Long studentId;

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof MstClassStudentId)) {
      return false;
    }
    MstClassStudentId mstClassStudentId = (MstClassStudentId) o;
    return Objects.equals(classId, mstClassStudentId.classId) && Objects.equals(studentId, mstClassStudentId.studentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(classId, studentId);
  }

}
