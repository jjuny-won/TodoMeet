package com.todomeet.todomeet.dto;

import com.todomeet.todomeet.entity.ProjectEntity;
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


    public static ProjectDto toDto(ProjectEntity projectEntity) {
        return ProjectDto.builder()
                .userEmail(projectEntity.getUserId())
                .eventName(projectEntity.getEventName())
                .startDay(projectEntity.getStartDay())
                .endDay(projectEntity.getEndDay())
                .startTime(projectEntity.getStartTime() != null ? LocalTime.parse(projectEntity.getStartTime()) : null)
                .endTime(projectEntity.getEndTime() != null ? LocalTime.parse(projectEntity.getEndTime()) : null)
                .build();
    }
}
