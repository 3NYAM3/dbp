package databaseProject.dbp.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(unique = true)
    private String email;

    private String name;

    @Column(length = 1023)
    private String password;

    @ManyToMany(mappedBy = "members")
    @JsonBackReference
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "member")
    @JsonBackReference
    private Set<Review> reviews = new HashSet<>();


    //==생성 메서드==//
    public static Member createMember(String name, String email, String password){
        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPassword(password);
        return member;
    }

}
