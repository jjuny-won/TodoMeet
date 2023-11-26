package com.todomeet.todomeet.controller;
import com.todomeet.todomeet.dto.ProjectDto;
import com.todomeet.todomeet.entity.ProjectEntity;
import com.todomeet.todomeet.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ProjectController {


    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity addSchedule(@RequestBody  ProjectDto projectDto){
        ResponseEntity responseEntity = projectService.addSchedule(projectDto);
        return responseEntity;

    }


    @DeleteMapping("/{projectId}")
    public ResponseEntity deleteSchedule(@PathVariable Long projectId){
        ResponseEntity responseEntity = projectService.deleteSchedule(projectId);
        return responseEntity;

    }

    @PatchMapping("/{projectId}")
    public ResponseEntity patchSchedule(@PathVariable Long projectId, ProjectDto projectDto){
        ResponseEntity responseEntity = projectService.patchSchedule(projectId,projectDto);
        return responseEntity;

    }

}
