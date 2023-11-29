package com.todomeet.todomeet.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableDto {

    private Long projectTimeId;

    private Long projectId;
    private String userEmail;

    private String day;

    private String startHour;

    private String endHour;



}
