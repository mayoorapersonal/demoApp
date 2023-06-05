package com.spring.qburst.demoApp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.spring.qburst.demoApp.model.Department;
import com.spring.qburst.demoApp.model.Student;
import com.spring.qburst.demoApp.repository.StudentRepository;
import com.spring.qburst.demoApp.service.imp.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {


	// @InjectMocks also creates the mock implementation of annotated type and
	// injects the dependent mocks into it.
	@InjectMocks
	private StudentServiceImpl studentService;

	// @Mock annotation creates a mock implementation for the class it is annotated
	// with.
	@Mock
	private StudentRepository studentrepository;

	private Date dob;

	private Department department;

//	@BeforeEach
//	public void beforeEach() {
//
//		//MockitoAnnotations.initMocks(this);
//		MockitoAnnotations.openMocks(this);
//
//	}
	
	@BeforeEach
	public void getDob() throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));

		String dateInString = "22-01-2015 10:15:55 AM";
		dob = formatter.parse(dateInString);
	}
	
	@BeforeEach
	public void getDepartment() {
		
		department = Department.builder().id(1).name("CS").build();
	}

	@Test
	void getStudentListTest() {

		Student student = Student.builder().id(1).firstName("A").lastName("B").dob(dob).department(department).build();
		
		List<Student> students = new ArrayList<>();
		students.add(student);
		
//		when(studentrepository.findAll()).thenReturn(students);

		doReturn(students).when(studentrepository).findAll();

//		assertEquals(1, studentService.getStudentList().size());

		assertEquals(students, studentService.getStudentList());

		// verify uses equals method for comparision, it checks whether actual method is
		// invoked or not.
		verify(studentrepository).findAll();
		verify(studentrepository, times(1)).findAll();
	}

	@Test
	void saveStudentTest() {

		Student student = Student.builder().firstName("Lalitha").lastName("Kumari").dob(dob)
				.department(department).build();

		Student savedStudent = studentService.saveStudentDetails(student);

		assertThat(savedStudent).isNotNull();
	}

	@Test
	void updateStudentTest() {

		Student student = Student.builder().id(1).firstName("A").lastName("B").dob(dob).department(department).build();

		when(studentrepository.findById(1)).thenReturn(Optional.of(student));

		student.setLastName("Narayanan");

		Student updatedStudent = studentService.updateStudent(student);

		assertThat(updatedStudent.getLastName()).isEqualToIgnoringWhitespace("Narayanan");

	}

	@Test
	void deleteStudentByIdTest() {

		Student student = Student.builder().id(1).firstName("A").lastName("B").dob(dob).department(department).build();
		
		when(studentrepository.findById(1)).thenReturn(Optional.of(student));
		doNothing().when(studentrepository).deleteById(student.getId());

		studentService.deleteByStudentId(student.getId());

		verify(studentrepository, times(1)).deleteById(student.getId());

	}

}
