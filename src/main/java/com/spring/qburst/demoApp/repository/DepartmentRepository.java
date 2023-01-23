package com.spring.qburst.demoApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.qburst.demoApp.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
