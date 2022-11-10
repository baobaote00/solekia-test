package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MstClassStudent;
import com.example.demo.entity.MstClassStudentId;

@Repository
public interface MstClassStudentRepository extends JpaRepository<MstClassStudent, MstClassStudentId> {

}
