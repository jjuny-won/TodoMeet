package com.todomeet.todomeet.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private String userEmail;
    private String eventName;
    private LocalDate startDay;
    private LocalDate endDay;
    private LocalTime startTime;
    private LocalTime endTime;


}
