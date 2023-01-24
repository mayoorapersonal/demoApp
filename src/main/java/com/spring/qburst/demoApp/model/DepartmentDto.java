package com.spring.qburst.demoApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

	private Integer deptId;
	
	private String deptName;
	
	private Integer studentId;

	private String firstName;
	
	private String lastName;
}
