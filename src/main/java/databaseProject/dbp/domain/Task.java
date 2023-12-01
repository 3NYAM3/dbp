package databaseProject.dbp.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {
    @Id @GeneratedValue
    @Column(name = "task_id")
    private Long TaskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private Long resMemberId;

    private String startDate;

    private String lastDate;

    private TaskStatus noticeStatus; //[PROGRESS, END]

}
