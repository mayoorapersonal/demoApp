package com.spring.qburst.demoApp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.qburst.demoApp.model.Department;
import com.spring.qburst.demoApp.model.Student;

@Service
public interface DepartmentService {

	Department saveDepartment(Department department) ;

	List<Department> findAll();

	void deleteByDepartmentId(Integer deptId);

	List<Department> getStudentByDepartments(Integer deptId);
	
}
