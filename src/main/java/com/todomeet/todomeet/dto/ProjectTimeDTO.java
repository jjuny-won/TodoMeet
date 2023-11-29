package com.todomeet.todomeet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTimeDTO {


        private Long projectTimeId;
        private Long projectId;
        private String day;
        private String userEmail;
        private String startTime;
        private String endTime;




}
