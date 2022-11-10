package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
@Table(name = "trn_enrollement")
public class MstClassStudent implements Serializable {

  @EmbeddedId
  private MstClassStudentId primaryKey = new MstClassStudentId();

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  @MapsId("studentId")
  @JoinColumn(name = "student_id")
  private MstStudent student;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  @MapsId("classId")
  @JoinColumn(name = "class_id")
  private MstClass class1;

  @Column(name = "registered_date", updatable = false)
  private Date registeredDate;

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof MstClassStudent)) {
      return false;
    }
    MstClassStudent mstClassStudent = (MstClassStudent) o;
    if (primaryKey == null) {
      if (mstClassStudent.primaryKey != null)
        return false;
    } else if (!primaryKey.equals(mstClassStudent.primaryKey))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(primaryKey, student, class1, registeredDate);
  }

}
