package com.todomeet.todomeet.service;


import com.todomeet.todomeet.Security.JwtAuthenticationFilter;
import com.todomeet.todomeet.Security.JwtTokenProvider;
import com.todomeet.todomeet.dto.ProjectDto;
import com.todomeet.todomeet.entity.ProjectEntity;
import com.todomeet.todomeet.exception.exception.BaseException;
import com.todomeet.todomeet.exception.exception.GlobalErrorCode;
import com.todomeet.todomeet.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Configuration
@EnableTransactionManagement
@Slf4j
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;


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


    public ProjectDto patchSchedule(Long projectId, ProjectDto projectDto) {
        String userEmail = JwtAuthenticationFilter.getUserEmailFromToken();

        // 프로젝트 ID로 엔터티 조회
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.NOT_FOUND_ERROR));

        // 수정을 요청한 사람의 Email이 프로젝트 생성한 사람인 확인하는 로직 필요
        // accessToken에서 Email 빼와서 현재 Project Repo에 등록된 UserEmail과 일치하는지 확인 필요
        if (projectRepository.existsById(projectId)) {
            // 만약에 수정을 요청한 userEmail이 프로젝트에 저장한 userId와 같다면
            if (projectEntity.getUserId().equals(userEmail)) {
                // ProjectDto의 내용으로 업데이트
                projectEntity.setEndDay(projectDto.getEndDay());
                projectEntity.setStartDay(projectDto.getStartDay());
                projectEntity.setStartTime(projectDto.getStartTime().toString());
                projectEntity.setEndTime(projectDto.getEndTime().toString());
                projectEntity.setEventName(projectDto.getEventName());
                log.info("수정한 Entity" + projectEntity);
                // 수정된 엔터티 저장
                projectRepository.save(projectEntity);
                // 수정된 프로젝트를 반환
                return projectDto;
            } else {
                throw new BaseException(GlobalErrorCode.ACCESS_DENIED);
            }
        }
        // 프로젝트를 찾을 수 없는 경우
        throw new BaseException(GlobalErrorCode.NOT_FOUND_ERROR);
    }

    //아이디로 조회하면 반환하는 함수
    public ProjectDto getProject(Long projectId)
    {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.NOT_FOUND_ERROR));
        return ProjectDto.projectToDto(projectEntity);

    }
}
