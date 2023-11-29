package com.todomeet.todomeet.repository;



import com.todomeet.todomeet.entity.ProjectTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface  ProjectTimeRepository  extends JpaRepository<ProjectTimeEntity,Long> {
    @Transactional
    List<ProjectTimeEntity> deleteByProjectId(Long projectId);
}
