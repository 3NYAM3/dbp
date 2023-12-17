package databaseProject.dbp.service;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.Member;
import databaseProject.dbp.domain.Notice;
import databaseProject.dbp.domain.Review;
import databaseProject.dbp.dto.ReviewDto.CreateReviewDto;
import databaseProject.dbp.dto.ReviewDto.ReviewDto;
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
        Review review = null;

        try {
            review = Review.createReview(notice, createReviewDto.getContent(), localDateTime, member);
            if(review == null) return ResponseDto.setFailed("create review failed");
            reviewRepository.save(review);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setWriter(member.getName());
        reviewDto.setContent(review.getContent());
        reviewDto.setWritingTime(reviewDto.getWritingTime());

        return ResponseDto.setSuccess("Success", reviewDto);

    }
}
