package com.todomeet.todomeet.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.todomeet.todomeet.dto.ProjectDto;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDay;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDay;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    public ProjectEntity(ProjectDto projectDto){
        this.userId = projectDto.getUserEmail();
        this.eventName = projectDto.getEventName();
        this.startDay = projectDto.getStartDay();
        this.endDay = projectDto.getEndDay();
//        this.startTime = projectDto.getStartTime().toString();
//        this.endTime = projectDto.getEndTime().toString();
        this.startTime = projectDto.getStartTime() != null ? projectDto.getStartTime().toString() : null;
        this.endTime = projectDto.getEndTime() != null ? projectDto.getEndTime().toString() : null;
    }

    public static ProjectEntity toEntity(ProjectDto projectDto) {
        return ProjectEntity.builder()
                .userId(projectDto.getUserEmail())
                .eventName(projectDto.getEventName())
                .startDay(projectDto.getStartDay())
                .endDay(projectDto.getEndDay())
                .startTime(projectDto.getStartTime() != null ? projectDto.getStartTime().toString() : null)
                .endTime(projectDto.getEndTime() != null ? projectDto.getEndTime().toString() : null)
                .build();
    }
}
