package com.spring.qburst.demoApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.qburst.demoApp.model.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment,Integer>{

	Optional<Attachment> findByName(String name);
}
