package com.spring.qburst.demoApp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.qburst.demoApp.model.DepartmentDto;
import com.spring.qburst.demoApp.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
	
	@Query("select new com.spring.qburst.demoApp.model.DepartmentDto(s.id as studentId, s.firstName as firstName, s.lastName as lastName,"
			+ " d.id as deptId, d.name as deptName)"
			+ " from #{#entityName} s "
			+ " left join Department d on s.department.id = d.id "
			+ " where (:deptId is null or d.id = :deptId)"
			+ " AND (:deptName is null or d.name like %:deptName%)"
			+ " AND (:firstName is null or s.firstName like %:firstName%)"
			+ " AND (:lastName is null or s.lastName like %:lastName%)")
	List<DepartmentDto> studentSearchQuery(@Param("deptId") Integer deptId, @Param("deptName") String deptName,
			@Param("firstName") String firstName, @Param("lastName") String lastName, Pageable pagination);

}
