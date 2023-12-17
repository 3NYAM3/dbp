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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            if (review == null) return ResponseDto.setFailed("create review failed");
            reviewRepository.save(review);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setWriter(email);
        reviewDto.setMemberId(member.getMemberId());
        reviewDto.setWriterName(member.getName());
        reviewDto.setContent(review.getContent());
        reviewDto.setWritingTime(review.getDateTime());

        return ResponseDto.setSuccess("Success", reviewDto);

    }

    public ResponseDto<?> deleteReview(String email, Long reviewId) {
        Member member = memberRepository.findByEmail(email);
        Review review = reviewRepository.findOne(reviewId);

        try {
            if (!Objects.equals(member.getMemberId(), review.getMember().getMemberId())) {
                return ResponseDto.setFailed("not your review");
            }
            reviewRepository.deleteReview(review);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        return ResponseDto.setSuccessNotIncludeData("Success");

    }

    public ResponseDto<List<ReviewDto>> getReviews(Long noticeId) {
        List<Review> reviews = null;

        try {
            reviews = reviewRepository.findByNoticeId(noticeId);
            if (reviews == null) return ResponseDto.setFailed("review get failed");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        List<ReviewDto> reviewDtoList = reviews.stream()
                .map(review -> {
                    ReviewDto reviewDto = new ReviewDto();
                    if (review.getMember()==null){
                        reviewDto.setMemberId(null);
                    }else {
                        reviewDto.setMemberId(review.getMember().getMemberId());
                    }
                    reviewDto.setWriter(review.getMember().getEmail());
                    reviewDto.setWriterName(review.getMember().getName());
                    reviewDto.setContent(review.getContent());
                    reviewDto.setWritingTime(review.getDateTime());

                    return reviewDto;
                }).collect(Collectors.toList());

        return ResponseDto.setSuccess("Success", reviewDtoList);
    }
}
