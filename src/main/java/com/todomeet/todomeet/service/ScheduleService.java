package com.todomeet.todomeet.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.todomeet.todomeet.Security.JwtAuthenticationFilter;
import com.todomeet.todomeet.dto.ProjectTimeDTO;
import com.todomeet.todomeet.dto.ScheduleDTO;
import com.todomeet.todomeet.entity.ProjectEntity;
import com.todomeet.todomeet.entity.ProjectTimeEntity;
import com.todomeet.todomeet.exception.exception.BaseException;
import com.todomeet.todomeet.exception.exception.GlobalErrorCode;
import com.todomeet.todomeet.repository.ProjectRepository;
import com.todomeet.todomeet.repository.ProjectTimeRepository;
import com.todomeet.todomeet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Configuration
@EnableTransactionManagement
@Slf4j
public class ScheduleService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectTimeRepository projectTimeRepository;

    public List<ScheduleDTO> getProjectsByDate(LocalDate date) {
        String userEmail = JwtAuthenticationFilter.getUserEmailFromToken();
        System.out.println(userEmail);

        List<ProjectTimeEntity> timeEntities = projectTimeRepository.findByday(date);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        //해당 하는 날짜의 속한 데이터 조회 -> 등록된 데이터가 있을 경우에 List<SchduleDTO>반환
        if (timeEntities != null) {
            // 필터링된 엔티티들을 순회하면서 ScheduleDTO에 매핑
            for (ProjectTimeEntity entity : timeEntities) {
                if (Objects.equals(entity.getUserEmail(), userEmail)) {
                    ScheduleDTO scheduleDTO = new ScheduleDTO();

                    scheduleDTO.setStartTime(entity.getStartTime());
                    scheduleDTO.setEndTime(entity.getEndTime());
                    scheduleDTO.setCheck(entity.isCheck());
                    //LocalTime Date  ->  string 으로 변환 필요
                    scheduleDTO.setDay(entity.getDay());
                    ProjectEntity projectEntity = projectRepository.getReferenceById(entity.getProjectId());
                    scheduleDTO.setProjectId(projectEntity.getProjectId());
                    scheduleDTO.setMemo(projectEntity.getMemo());
                    scheduleDTO.setEventName(projectEntity.getEventName());

                    scheduleDTOList.add(scheduleDTO);
                    return scheduleDTOList;

                }
                //그날 일정 중 사용자 아이디로 등록한 일정이 없을 경우
                throw new BaseException("400","사용자가 등록한 일정이 없습니다", HttpStatus.NOT_FOUND);
            }

        }
            //등록된 일정이 없을경우
           throw new BaseException("400","그 날에 등록된 일정이 없습니다", HttpStatus.NOT_FOUND);

    }

    public List<ScheduleDTO> getProjectsByYearAndMonth(int year, int month) {
        String userEmail = JwtAuthenticationFilter.getUserEmailFromToken();
        System.out.println(userEmail);

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<ProjectTimeEntity> timeEntities = projectTimeRepository.findByDayBetween(startDate, endDate);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        // 해당 기간의 속한 데이터 조회 -> 등록된 데이터가 있을 경우 List<ScheduleDTO> 반환
        if (timeEntities != null && !timeEntities.isEmpty()) {
            // 필터링된 엔티티들을 순회하면서 ScheduleDTO에 매핑
            for (ProjectTimeEntity entity : timeEntities) {
                if (Objects.equals(entity.getUserEmail(), userEmail)) {
                    ScheduleDTO scheduleDTO = new ScheduleDTO();
                    scheduleDTO.setDay(entity.getDay());
                    scheduleDTO.setStartTime(entity.getStartTime());
                    scheduleDTO.setEndTime(entity.getEndTime());
                    scheduleDTO.setCheck(entity.isCheck());
                    // LocalTime 및 LocalDate를 String으로 변환 필요

                    ProjectEntity projectEntity = projectRepository.getReferenceById(entity.getProjectId());
                    scheduleDTO.setProjectId(projectEntity.getProjectId());
                    scheduleDTO.setMemo(projectEntity.getMemo());
                    scheduleDTO.setEventName(projectEntity.getEventName());

                    scheduleDTOList.add(scheduleDTO);
                }
            }

            // 등록된 일정이 있을 경우
            return scheduleDTOList;
        }

        // 등록된 일정이 없을 경우
        throw new BaseException("400", "해당 기간에 등록된 일정이 없습니다", HttpStatus.NOT_FOUND);
    }




}
