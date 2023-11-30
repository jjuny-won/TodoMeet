package com.todomeet.todomeet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


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
    private String startTime="00:00:00";

    @JsonFormat(pattern = "HH:mm:ss")
    private String endTime="23:59:59";

    private boolean isCheck = false;






}
