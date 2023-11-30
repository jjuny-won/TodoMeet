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


    private String startTime;
    private String endTime;
    private boolean isCheck;
    private String eventName;
    private String memo;

}
