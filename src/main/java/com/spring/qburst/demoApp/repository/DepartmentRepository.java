package com.spring.qburst.demoApp.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.qburst.demoApp.model.Department;
import com.spring.qburst.demoApp.model.DepartmentDto;
import com.spring.qburst.demoApp.model.Student;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	@Query("select d from Department d where d.id = :deptId")
	List<Department> getStudentByDepartment(@Param("deptId") Integer deptId);
	

	@Query("select d from Department d where d.name in :name")
	List<Department> findAllDepartent(@Param("name") String[] deptName);
}
