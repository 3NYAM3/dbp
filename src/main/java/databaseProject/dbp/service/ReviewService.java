package databaseProject.dbp.service;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.Member;
import databaseProject.dbp.domain.Notice;
import databaseProject.dbp.domain.Review;
import databaseProject.dbp.dto.ReviewDto.CreateReviewDto;
import databaseProject.dbp.repository.MemberRepository;
import databaseProject.dbp.repository.NoticeRepository;
import databaseProject.dbp.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseDto<?> createReview(String email, Long noticeId, CreateReviewDto createReviewDto) {
        Notice notice = noticeRepository.findOne(noticeId);
        LocalDateTime localDateTime = LocalDateTime.now();
        Member member = memberRepository.findByEmail(email);
        Review review = Review.createReview(notice, createReviewDto.getContent(), localDateTime, member);

//        try {
//
//        }

        return null;

    }
}
