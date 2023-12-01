package databaseProject.dbp.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long memberId;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private String phoneNum;

    @ManyToMany(mappedBy = "members")
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "member")
    private Set<Review> reviews = new HashSet<>();


}
