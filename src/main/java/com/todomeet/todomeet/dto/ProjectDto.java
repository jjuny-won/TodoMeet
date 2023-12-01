package com.todomeet.todomeet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
    private LocalDate startDay;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
    private LocalDate endDay;

    private String memo;
    private Long projectId;

    private List<TimeSlot> timeSlots;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TimeSlot {

        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
        private LocalDate day;
        @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
        private LocalTime startTime = LocalTime.of(0, 0);
        @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
        private LocalTime endTime = LocalTime.of(23, 59);

        private boolean isCheck;
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
