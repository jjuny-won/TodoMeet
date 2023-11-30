package com.todomeet.todomeet.controller;


import com.todomeet.todomeet.dto.ScheduleDTO;
import com.todomeet.todomeet.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/schedule")
public class SchduleController
{
    @Autowired
    private final ScheduleService scheduleService;

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForDate(
            @PathVariable int year,
            @PathVariable int month,
            @PathVariable int day) {


        LocalDate date = LocalDate.of(year, month, day);
        List<ScheduleDTO> ScheduleDTO = scheduleService.getProjectsByDate(date);
        return ResponseEntity.ok(ScheduleDTO);
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForMonth(
            @PathVariable int year,
            @PathVariable int month) {


//        LocalDate date = LocalDate.of(year, month);
        List<ScheduleDTO> ScheduleDTO = scheduleService.getProjectsByYearAndMonth(year,month);
        return ResponseEntity.ok(ScheduleDTO);
    }

}
