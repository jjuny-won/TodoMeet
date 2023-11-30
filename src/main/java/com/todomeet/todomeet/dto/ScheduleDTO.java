package com.todomeet.todomeet.dto;

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
public class ScheduleDTO {

    private Long projectId;
    private String startTime;
    private String endTime;
    private boolean isCheck;
    private String eventName;
    private String memo;

    private LocalDate day;


    public ScheduleDTO(Long projectId, String startTime, String endTime, boolean isCheck, String eventName , String memo){
        this.projectId = projectId;
        this.startTime = startTime;
        this.endTime= endTime;
        this.isCheck = isCheck;
        this.eventName =eventName;
        this.memo = memo;
    }

}
