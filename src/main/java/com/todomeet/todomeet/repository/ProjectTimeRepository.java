package com.todomeet.todomeet.repository;



import com.todomeet.todomeet.entity.ProjectTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface  ProjectTimeRepository  extends JpaRepository<ProjectTimeEntity,Long> {
    @Transactional
    List<ProjectTimeEntity> deleteByProjectId(Long projectId);


    List<ProjectTimeEntity> findByProjectId(Long projectId);
    Optional<ProjectTimeEntity> findByProjectIdAndDay(Long projectId, LocalDate date);

    List<ProjectTimeEntity> findByday(LocalDate day);

    List<ProjectTimeEntity> findByDayBetween(LocalDate startDate, LocalDate endDate);


    List<ProjectTimeEntity> findByDayBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
