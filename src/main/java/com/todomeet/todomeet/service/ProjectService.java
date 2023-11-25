package com.todomeet.todomeet.service;


import com.todomeet.todomeet.dto.ProjectDto;
import com.todomeet.todomeet.entity.ProjectEntity;
import com.todomeet.todomeet.exception.exception.BaseException;
import com.todomeet.todomeet.exception.exception.CustomErrorController;
import com.todomeet.todomeet.exception.exception.GlobalErrorCode;
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
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;


    public ResponseEntity addSchedule(ProjectDto projectDto) {
        ProjectEntity projectEntity = new ProjectEntity(projectDto);
        System.out.println(projectEntity.getEndDay()); //null
        projectRepository.save(projectEntity);
        return ResponseEntity.ok("일정이 저장되었습니다");

    }

    public ResponseEntity deleteSchedule(Long projectId) {
        try {
            projectRepository.deleteById(projectId);
            return ResponseEntity.ok("일정이 삭제되었습니다");
        } catch (Exception e) {
            return (ResponseEntity) ResponseEntity.notFound();
        }
    }


    public ResponseEntity patchSchedule(Long projectId, ProjectDto projectDto) {
        try {
            //수정을 요청한 사람의 Id가 프로젝트 생성한 사람인 확인하는 로직 필요
            if (projectRepository.existsById(projectId)) {
                ProjectEntity projectEntity = new ProjectEntity(projectDto);
                projectRepository.save(projectEntity);
                return ResponseEntity.ok(projectEntity);
            } else {
                throw new BaseException(GlobalErrorCode.NOT_FOUND_ERROR);
            }
        } catch (Exception e) {
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
