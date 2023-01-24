package com.spring.qburst.demoApp.service;

import java.util.List;

import com.spring.qburst.demoApp.model.Student;

public interface StudentService {

	Student saveStudentDetails(Student student);
	
	List<Student> getStudentList();
	
	Student updateStudent(Student student);
	
	void deleteByStudentId(int id);

}
