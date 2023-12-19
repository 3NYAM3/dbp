package databaseProject.dbp.domain;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonManagedReference
    private Project project;

    private String title;

    @Column(length = 8191)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    @JsonManagedReference
    private Member member;

    private String createTime;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Review> reviews = new HashSet<>();


    //==생성 메서드==//
    public static Notice createNotice(Project project, String title, Member writer, String content, String createTime) {
        Notice notice = new Notice();
        notice.setProject(project);
        notice.setTitle(title);
        notice.setMember(writer);
        notice.setContent(content);
        notice.setCreateTime(createTime);

        return notice;
    }

}
