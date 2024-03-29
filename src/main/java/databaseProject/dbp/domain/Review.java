package databaseProject.dbp.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    @JsonManagedReference
    private Notice notice;

    @Column(length = 8191)
    private String Content;

    private String dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonManagedReference
    private Member member;


    //==생성 메서드==//
    public static Review createReview(Notice notice, String content, String localDateTime, Member member) {
        Review review = new Review();
        review.setNotice(notice);
        review.setContent(content);
        review.setDateTime(localDateTime);
        review.setMember(member);

        return review;
    }
}

