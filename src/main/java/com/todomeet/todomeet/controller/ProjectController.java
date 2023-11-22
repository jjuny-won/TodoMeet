package com.todomeet.todomeet.controller;
import com.todomeet.todomeet.dto.ProjectDto;
import com.todomeet.todomeet.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ProjectController {


    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity addSchedule(@RequestBody  ProjectDto projectDto){
        System.out.println(projectDto);
        ResponseEntity responseEntity = projectService.addSchedule(projectDto);
        return responseEntity;

    }


    @GetMapping("/{month}")
    public ResponseEntity getSchedule(@RequestParam String month){
        ResponseEntity responseEntity = projectService.getSchedule(month);
        return responseEntity;

    }

}
