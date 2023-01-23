package com.spring.qburst.demoApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.qburst.demoApp.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
	
	@Query("select d.student from Department d where d.id = :deptId")
	List<Student> getStudentByDepartment(@Param("deptId") Integer deptId);

}
