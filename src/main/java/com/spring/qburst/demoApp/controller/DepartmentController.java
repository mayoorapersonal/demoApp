package com.spring.qburst.demoApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.qburst.demoApp.model.Department;
import com.spring.qburst.demoApp.model.DepartmentDto;
import com.spring.qburst.demoApp.model.Student;
import com.spring.qburst.demoApp.repository.DepartmentRepository;
import com.spring.qburst.demoApp.service.DepartmentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/department")
@SecurityRequirement(name = "bearerAuth")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@PostMapping("/saveDepartment")
	public Department saveDepartment(@RequestBody Department department) {
		return departmentService.saveDepartment(department);
	}
	
	@GetMapping("/departmentList")
	public List<Department> getDepartmentList(){
		
		return departmentService.findAll();
	}
	
	@DeleteMapping("/deleteDepartment")
	public ResponseEntity<String> deleteDepartment(@RequestParam("deptId") Integer deptId) {
		departmentService.deleteByDepartmentId(deptId);
		return new ResponseEntity<String>("Successfully deleted department !!!", HttpStatus.OK);
	}
	
	@GetMapping("/studentByDept/{deptId}")
	public List<Department> getStudentByDepartment(@PathVariable("deptId") Integer deptId){
		return departmentService.getStudentByDepartments(deptId);
	}
	
//	@GetMapping("/studentByDept/{deptId}")
//	public List<Department> getStudentByDepartment(@PathVariable("deptId") Integer deptId){
//		return departmentService.getStudentByDepartments(deptId);
//	}
	
	@GetMapping("/departments")
	public List<Department> getGivenDepartment(@RequestParam("deptNames") String[] deptNames){
		
		System.out.println("Department names : " + deptNames);
		return departmentRepository.findAllDepartent(deptNames);
	}
}
