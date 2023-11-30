package com.todomeet.todomeet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="project_time")
public class ProjectTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectTimeId;

    @Column(nullable = false)
    private Long projectId;

    @Column(nullable = false)
    private LocalDate day;

    @Column(nullable = false)
    private String userEmail;

    @JsonFormat(pattern = "HH:mm:ss")
    private String startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private String endTime;

    private boolean isCheck = false;






}
