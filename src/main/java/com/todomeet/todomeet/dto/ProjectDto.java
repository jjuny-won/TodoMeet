package com.todomeet.todomeet.dto;

import com.todomeet.todomeet.entity.ProjectEntity;
import com.todomeet.todomeet.entity.ProjectTimeEntity;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    private String memo;

    private List<TimeSlot> timeSlots;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TimeSlot {
        private LocalDate day;
        private LocalTime startTime;
        private LocalTime endTime;
    }


    public static ProjectDto projectToDto(ProjectEntity projectEntity) {
        return ProjectDto.builder()
                .userEmail(projectEntity.getUserId())
                .eventName(projectEntity.getEventName())
                .startDay(projectEntity.getStartDay())
                .endDay(projectEntity.getEndDay())
                .memo(projectEntity.getMemo())
                .build();
    }


}
