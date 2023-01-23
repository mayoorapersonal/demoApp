package com.spring.qburst.demoApp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.qburst.demoApp.model.Department;

@Service
public interface DepartmentService {

	Department saveDepartment(Department department) ;

	List<Department> findAll();

	void deleteByDepartmentId(Integer deptId);

}
