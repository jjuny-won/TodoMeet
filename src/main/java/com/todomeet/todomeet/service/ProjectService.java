package com.todomeet.todomeet.service;


import com.todomeet.todomeet.dto.ProjectDto;
import com.todomeet.todomeet.entity.ProjectEntity;
import com.todomeet.todomeet.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@RequiredArgsConstructor
@Service
@Configuration
@EnableTransactionManagement
public class ProjectService
{

    @Autowired
    ProjectRepository projectRepository;


    public ResponseEntity addSchedule(ProjectDto projectDto) {
        ProjectEntity projectEntity = new ProjectEntity(projectDto);
        System.out.println(projectEntity.getEndDay()); //null
        projectRepository.save(projectEntity);
        return ResponseEntity.ok("일정이 저장되었습니다");

    }

    public ResponseEntity deleteSchedule(Long projectId){
        try{
            projectRepository.deleteById(projectId);
            return ResponseEntity.ok("일정이 삭제되었습니다");
        }catch(Exception e ){
            return (ResponseEntity) ResponseEntity.notFound();
        }
    }

//    public List<ProjectEntity> getSchedule(int year,int month){
//
//        List<ProjectEntity>  projectEntityList = projectRepository.findByYearandMonth(year,month);
//        System.out.println(projectEntityList);
//        return projectEntityList;
//
//    }
}
