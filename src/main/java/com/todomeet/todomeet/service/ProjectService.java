package com.todomeet.todomeet.service;


import com.todomeet.todomeet.Security.JwtAuthenticationFilter;
import com.todomeet.todomeet.Security.JwtTokenProvider;
import com.todomeet.todomeet.dto.ProjectDto;
import com.todomeet.todomeet.dto.ProjectTimeDTO;
import com.todomeet.todomeet.entity.ProjectEntity;
import com.todomeet.todomeet.entity.ProjectTimeEntity;
import com.todomeet.todomeet.exception.exception.BaseException;
import com.todomeet.todomeet.exception.exception.GlobalErrorCode;
import com.todomeet.todomeet.repository.ProjectRepository;
import com.todomeet.todomeet.repository.ProjectTimeRepository;
import com.todomeet.todomeet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
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
    UserRepository userRepository;

    @Autowired
    ProjectTimeRepository projectTimeRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ProjectDto addSchedule(ProjectDto projectDto) {
        //토큰에서 userEmail 추출
        String userEmail = JwtAuthenticationFilter.getUserEmailFromToken();

        //글  작성자가 DB에 등록되어있는지 확인 필요-> 이메일로 확인
        if (userRepository.existsById(userEmail)) {
            ProjectEntity projectEntity = new ProjectEntity(projectDto);
            projectRepository.save(projectEntity);
            Long projectId = projectEntity.getProjectId();
            List<ProjectTimeEntity> timeEntities = projectDto.getTimeSlots().stream()
                    .map(timeSlot -> {
                        ProjectTimeEntity projectTimeEntity = new ProjectTimeEntity();
                        projectTimeEntity.setProjectId(projectId);
                        projectTimeEntity.setDay(timeSlot.getDay());
                        projectTimeEntity.setStartTime(String.valueOf(timeSlot.getStartTime()));
                        projectTimeEntity.setEndTime(String.valueOf(timeSlot.getEndTime()));
                        projectTimeEntity.setUserEmail(projectDto.getUserEmail());
                        return projectTimeEntity;
                    })
                    .collect(Collectors.toList());
            projectDto.setProjectId(projectEntity.getProjectId());
            projectTimeRepository.saveAll(timeEntities);

          return projectDto;
        }
        throw new BaseException(GlobalErrorCode.NOT_FOUND_ERROR);
    }

    public ResponseEntity deleteSchedule(Long projectId) {
        String userEmail = JwtAuthenticationFilter.getUserEmailFromToken();

        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
        if(projectEntity.isPresent()) {
            if(projectEntity.get().getUserId().equals(userEmail)) {
                projectRepository.deleteById(projectId);
                projectTimeRepository.deleteByProjectId(projectId);
                return ResponseEntity.ok().build();
            }
            throw new BaseException(GlobalErrorCode.ACCESS_DENIED);
        }
        else{
          throw new BaseException(GlobalErrorCode.NOT_FOUND_ERROR);
        }

    }
    @Transactional
    public ProjectDto patchSchedule(Long projectId, ProjectDto projectDto) {
        String userEmail = JwtAuthenticationFilter.getUserEmailFromToken();

        // 프로젝트 ID로 엔터티 조회
//        ProjectEntity projectEntity = projectRepository.findById(projectId)
//                .orElseThrow(() -> new BaseException(GlobalErrorCode.NOT_FOUND_ERROR));
        Optional<ProjectEntity> optionalProjectEntity = projectRepository.findById(projectId);
        if (optionalProjectEntity.isPresent()) {
            ProjectEntity projectEntity = optionalProjectEntity.get();

            // 프로젝트 엔터티 수정
            projectEntity.updateFromDto(projectDto);
            projectRepository.save(projectEntity);

            // 기존 시간 데이터 삭제
            projectTimeRepository.deleteByProjectId(projectId);

            // 새로운 시간 데이터 저장
            List<ProjectTimeEntity> timeEntities = projectDto.getTimeSlots().stream()
                    .map(timeSlot -> {
                        ProjectTimeEntity projectTimeEntity = new ProjectTimeEntity();
                        projectTimeEntity.setProjectId(projectId);
                        projectTimeEntity.setDay(timeSlot.getDay());
                        projectTimeEntity.setStartTime(String.valueOf(timeSlot.getStartTime()));
                        projectTimeEntity.setEndTime(String.valueOf(timeSlot.getEndTime()));
                        projectTimeEntity.setUserEmail(projectDto.getUserEmail());
                        return projectTimeEntity;
                    })
                    .collect(Collectors.toList());

            projectTimeRepository.saveAll(timeEntities);
            projectDto.setProjectId(projectEntity.getProjectId());
            return projectDto;
        } else {
            throw new BaseException(GlobalErrorCode.NOT_FOUND_ERROR);

        }
    }

    // 프로젝트 Id로 조회
    public ProjectDto getProject(Long projectId) {
        String userEmail = JwtAuthenticationFilter.getUserEmailFromToken();
        Optional<ProjectEntity> optionalProjectEntity = projectRepository.findById(projectId);

        if (optionalProjectEntity.isPresent()) {
            ProjectEntity projectEntity = optionalProjectEntity.get();
            System.out.println("------"+projectEntity.getUserId()+userEmail+"--------");
            // 프로젝트의 사용자와 현재 사용자가 일치하는지 확인
            if (projectEntity.getUserId().equals(userEmail)) {
                ProjectDto projectDto = new ProjectDto();

                projectDto.setUserEmail(userEmail);
                projectDto.setEventName(projectEntity.getEventName());
                projectDto.setStartDay(projectEntity.getStartDay());
                projectDto.setEndDay(projectEntity.getEndDay());
                projectDto.setMemo(projectEntity.getMemo());
                projectDto.setProjectId(projectEntity.getProjectId());

                // 프로젝트 아이디에 해당하는 시간 데이터 조회
                List<ProjectTimeEntity> projectTimeEntities = projectTimeRepository.findByProjectId(projectId);
                projectDto.setTimeSlots(projectTimeEntities.stream()
                        .map(timeEntity -> {
                            ProjectDto.TimeSlot timeSlot = new ProjectDto.TimeSlot();
                            timeSlot.setDay(timeEntity.getDay());
                            timeSlot.setStartTime(LocalTime.parse(timeEntity.getStartTime()));
                            timeSlot.setEndTime(LocalTime.parse(timeEntity.getEndTime()));
                            timeSlot.setCheck(timeSlot.isCheck()); // isCheckd 대신에 isCheck 사용
                            return timeSlot;
                        })
                        .collect(Collectors.toList()));

                return projectDto;
            } else {
                throw new BaseException(GlobalErrorCode.ACCESS_DENIED);
            }
        } else {
            throw new BaseException(GlobalErrorCode.NOT_FOUND_ERROR);
        }
    }


    public List<ProjectTimeDTO> getProjectsForDate(String date) {
        // date를 기간으로 변환 (startDay <= date <= endDay)
        LocalDate targetDate = LocalDate.parse(date);
        LocalDateTime startDateTime = targetDate.atStartOfDay();
        LocalDateTime endDateTime = targetDate.atTime(LocalTime.MAX);

        // 해당 날짜 범위에 속하는 프로젝트 시간 데이터 조회
        List<ProjectTimeEntity> timeEntities = projectTimeRepository.findByDayBetween(startDateTime, endDateTime);

        // 조회된 시간 데이터로부터 프로젝트 조회
        List<ProjectTimeDTO> projects = timeEntities.stream()
                .map(ProjectTimeDTO::new)
                .collect(Collectors.toList());

        return projects;
    }

    //체크 상태 저장
    public void saveCheckStatus(Long projectId, LocalDate date, boolean isChecked) {
        // ProjectId가 존재하는지 확인
        if(!projectTimeRepository.findByProjectId(projectId).isEmpty()) {
            Optional<ProjectTimeEntity> projectTimeEntityOptional = projectTimeRepository.findByProjectIdAndDay(projectId, date);

            // 만약 찾은 경우에만 처리
            projectTimeEntityOptional.ifPresent(entity -> {
                entity.setCheck(isChecked);
                projectTimeRepository.save(entity);
            });
        }else{
            throw new BaseException(GlobalErrorCode.NOT_FOUND_ERROR);
        }
    }
}
