package com.todomeet.todomeet.controller;
import com.todomeet.todomeet.dto.ProjectDto;
import com.todomeet.todomeet.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/project")
public class ProjectController {


    private final ProjectService projectService;

//   모임 등록
    @PostMapping
    public ResponseEntity addSchedule(@RequestBody  ProjectDto projectDto){
        ResponseEntity responseEntity = projectService.addSchedule(projectDto);
        return responseEntity;

    }

//  모임 삭제
    @DeleteMapping("/{projectId}")
    public ResponseEntity deleteSchedule(@PathVariable Long projectId){
        ResponseEntity responseEntity = projectService.deleteSchedule(projectId);
        return responseEntity;

    }

//모임 수정
    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectDto> patchSchedule(@PathVariable Long projectId, @RequestBody ProjectDto projectDto) {
        log.info("일정 수정 start");
        System.out.println("ProjectDto" + projectDto);
        log.info("일정 수정 End");
        ProjectDto updatedProject = projectService.patchSchedule(projectId, projectDto);

        // 수정된 프로젝트를 반환
        return ResponseEntity.ok(updatedProject);
    }




}