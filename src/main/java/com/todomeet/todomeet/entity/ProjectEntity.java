package com.todomeet.todomeet.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.todomeet.todomeet.dto.ProjectDto;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    private String memo;

    public ProjectEntity(ProjectDto projectDto){
        this.userId = projectDto.getUserEmail();
        this.eventName = projectDto.getEventName();
        this.startDay = projectDto.getStartDay();
        this.endDay = projectDto.getEndDay();
        this.memo = projectDto.getMemo();
    }

    public void updateFromDto(ProjectDto projectDto) {
        this.setStartDay(projectDto.getStartDay());
        this.setEndDay(projectDto.getEndDay());
        this.setEventName(projectDto.getEventName());
        this.setMemo(projectDto.getMemo());
        // 기타 필요한 필드 업데이트
    }

    public static ProjectEntity toEntity(ProjectDto projectDto) {
        return ProjectEntity.builder()
                .userId(projectDto.getUserEmail())
                .eventName(projectDto.getEventName())
                .startDay(projectDto.getStartDay())
                .endDay(projectDto.getEndDay())
                .memo(projectDto.getMemo())
                .build();
    }
}
