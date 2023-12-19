package databaseProject.dbp.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    private String projectName;

    private String type;

    private Long leaderId;

    private LocalDate startDate;

    private LocalDate lastDate;

    @ManyToMany
    @JoinTable(name = "member_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    @JsonManagedReference
    private Set<Member> members = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Task> works = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Notice> notices = new HashSet<>();

    //==생성 메서드==//
    public static Project createProject(String projectName, String type, LocalDate startDate, LocalDate lastDate, Long memberId) {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setType(type);
        project.setStartDate(startDate);
        project.setLastDate(lastDate);
        project.setLeaderId(memberId);

        return project;
    }
}
