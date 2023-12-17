package databaseProject.dbp.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long TaskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonManagedReference
    private Project project;

    private String content;

    private String memo;

    private LocalDate startDate;

    private LocalDate lastDate;


    //==생성 메서드==//
    public static Task createTask(Project project, String content, String memo, LocalDate startDate, LocalDate lastDate) {
        Task task = new Task();
        task.setProject(project);
        task.setContent(content);
        task.setMemo(memo);
        task.setStartDate(startDate);
        task.setLastDate(lastDate);

        return task;
    }

}
