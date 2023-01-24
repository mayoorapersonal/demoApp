package com.spring.qburst.demoApp.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.spring.qburst.demoApp.exception.StudentException;
import com.spring.qburst.demoApp.model.Department;
import com.spring.qburst.demoApp.model.Student;
import com.spring.qburst.demoApp.repository.DepartmentRepository;
import com.spring.qburst.demoApp.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public Department saveDepartment(Department department) {
		
		return departmentRepository.save(department);
	}

	@Override
	public List<Department> findAll() {
		return departmentRepository.findAll();
	}

	@Override
	public void deleteByDepartmentId(Integer deptId) {
		
		try {
			departmentRepository.findById(deptId).orElseThrow(() -> new StudentException(messageSource.getMessage("DEPARTMENT.NOT.FOUND", null, null)));
		} catch (StudentException e) {
			throw new StudentException(e.getMessage());
		}
		
		departmentRepository.deleteById(deptId);
	}
	
	@Override
	public List<Department> getStudentByDepartments(Integer deptId) {
		return departmentRepository.getStudentByDepartment(deptId);
	}

}
