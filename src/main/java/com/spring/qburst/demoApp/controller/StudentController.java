package com.spring.qburst.demoApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.qburst.demoApp.model.DepartmentDto;
import com.spring.qburst.demoApp.model.Student;
import com.spring.qburst.demoApp.repository.StudentRepository;
import com.spring.qburst.demoApp.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentRepository studentRepository;
	
	//localhost:8080/student/helloStudent
	@GetMapping("/hello")
	public String hello(){
		
		
		return "Hello";
	}

	@PostMapping("/createStudent")
	public ResponseEntity<Student> saveStudent(@RequestBody(required = false) Student student){
		
		
		return new ResponseEntity<Student>(studentService.saveStudentDetails(student), HttpStatus.CREATED);
	}
	
	@GetMapping("/getStudentList")
	public List<Student> getStudentList(){
		
		return studentService.getStudentList();
	}
	
	@PutMapping("/updateStudent")
	public Student updateStudent(@RequestBody Student student) {
		System.out.println(student);
		return studentService.updateStudent(student);
	}
	
	@DeleteMapping("/deleteStudentById/{id}")
	public ResponseEntity<String> deleteStudentById(@PathVariable(name="id") int id) {
		studentService.deleteByStudentId(id);
		return new ResponseEntity<String>("Successfully deleted employee !!!", HttpStatus.OK);
	}
	
	@GetMapping("/studentSearch")
	public List<DepartmentDto> getStudentsByDepartment(@RequestParam(name = "deptId", required = false) Integer deptId,
			@RequestParam(name = "deptName", required = false) String deptName,
			@RequestParam(name = "firstName", required = false) String firstName,
			@RequestParam(name = "lastName", required = false) String lastName,
			@RequestParam(name = "pageNo", required = false) Integer pageNo,
			@RequestParam(name = "size", required = false) Integer size) {
		
		Pageable pagination = null;
				
		if(null != pageNo && null != size) {
			pagination = PageRequest.of(pageNo, size, Sort.by("id").descending());
		}

		return studentRepository.studentSearchQuery(deptId, deptName, firstName, lastName, pagination);

	}
}
