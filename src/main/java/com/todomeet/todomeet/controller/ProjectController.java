package com.todomeet.todomeet.controller;
import com.todomeet.todomeet.dto.ProjectDto;
import com.todomeet.todomeet.dto.ProjectTimeDTO;
import com.todomeet.todomeet.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/project")
public class ProjectController {


    private final ProjectService projectService;

//   모임 등록
    @PostMapping
    public ProjectDto addSchedule(@RequestBody  ProjectDto projectDto){
        log.info("일정 등록 start");
        return projectService.addSchedule(projectDto);
    }

//  모임 삭제
    @DeleteMapping("/{projectId}")
    public ResponseEntity deleteSchedule(@PathVariable Long projectId){
        ResponseEntity responseEntity = projectService.deleteSchedule(projectId);
        return responseEntity;
    }

    //모임 수정
    @PatchMapping("/{projectId}")
    public ResponseEntity<?> patchSchedule(@PathVariable Long projectId, @RequestBody ProjectDto projectDto) {
        ProjectDto result = projectService.patchSchedule(projectId, projectDto);
        return ResponseEntity.ok(result);
    }


    //모임 조회
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> patchSchedule(@PathVariable Long projectId) {
        log.info("일정 조회 start");
        ProjectDto updatedProject = projectService.getProject(projectId);
        log.info("일정 조회 End");
        // 수정된 프로젝트를 반환
        return ResponseEntity.ok(updatedProject);

    }

    //check
    @PostMapping("/check")
    public ResponseEntity<Void> checkProject(@RequestParam Long projectId, @RequestParam LocalDate day, @RequestParam boolean isChecked){
        projectService.saveCheckStatus(projectId, day, isChecked);
        return ResponseEntity.ok().build();
    }

}