package databaseProject.dbp.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Notice {
    @Id @GeneratedValue
    @Column(name = "notice_id")
    private Long noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private String title;

    private String content;

    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "notice")
    private Set<Review> reviews = new HashSet<>();

}
