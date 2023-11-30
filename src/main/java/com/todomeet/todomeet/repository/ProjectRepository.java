package com.todomeet.todomeet.repository;

import com.todomeet.todomeet.entity.ProjectEntity;
import com.todomeet.todomeet.entity.ProjectTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface ProjectRepository extends JpaRepository<ProjectEntity,Long> {



}


