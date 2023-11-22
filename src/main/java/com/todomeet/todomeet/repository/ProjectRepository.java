package com.todomeet.todomeet.repository;

import com.todomeet.todomeet.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity,Long> {



}


