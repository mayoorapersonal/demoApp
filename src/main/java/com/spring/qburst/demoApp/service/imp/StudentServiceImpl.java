package com.spring.qburst.demoApp.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.spring.qburst.demoApp.exception.StudentException;
import com.spring.qburst.demoApp.model.Student;
import com.spring.qburst.demoApp.repository.StudentRepository;
import com.spring.qburst.demoApp.service.StudentService;

import jakarta.validation.constraints.NotEmpty;

@Service
@PropertySources(@PropertySource("classpath:/messages/api_error_message.properties"))
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Value("${STUDENT.NOT.FOUND}")
	private String studentError;
	
	@Override
	public Student saveStudentDetails(Student student) {
		
		try {
			 studentRepository.save(student);
		} catch (Exception e) {
			throw new StudentException(messageSource.getMessage("ERR_STUDENT", null, studentError, null));
		}
		return student;
	}

	@Override
	public List<Student> getStudentList() {
		return studentRepository.findAll();
	}

	@Override
	public Student updateStudent(Student student) {
		
		Student existingStudentObj = null;
		try {
			existingStudentObj = studentRepository.findById(student.getId()).orElseThrow(() -> new StudentException("Student details not found for updation."));
		} catch (StudentException e) {
			throw new StudentException(e.getMessage());
		}
		existingStudentObj.setFirstName(student.getFirstName());
		existingStudentObj.setLastName(student.getLastName());
		existingStudentObj.setDob(student.getDob());
		existingStudentObj.setDepartment(student.getDepartment());
		studentRepository.save(existingStudentObj);
		
		return existingStudentObj;
	}

	@Override
	public void deleteByStudentId(int id) {
		
		try {
			studentRepository.findById(id).orElseThrow(() -> new StudentException(studentError));
		} catch (StudentException e) {
			throw new StudentException(e.getMessage());
		}
		
		studentRepository.deleteById(id);
		 
	}

	

}
