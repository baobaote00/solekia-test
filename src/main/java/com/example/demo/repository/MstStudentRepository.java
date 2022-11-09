package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MstStudent;
/**
 * クラス情報 Repository
 */

@Repository
public interface MstStudentRepository extends JpaRepository<MstStudent, Long> {}