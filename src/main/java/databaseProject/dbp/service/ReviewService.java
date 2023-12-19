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
import java.time.format.DateTimeFormatter;
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
        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
        reviewDto.setReviewId(review.getReviewId());
        try{
            Member member1 = review.getMember();
            if(member1 !=null){
                reviewDto.setWriterName(member.getName());
            }else {
                reviewDto.setWriter(null);
            }

        }catch (NullPointerException e){
            reviewDto.setWriter(null);
        }
        reviewDto.setContent(review.getContent());
        reviewDto.setWritingTime(review.getDateTime());

        return ResponseDto.setSuccess("Success", reviewDto);

    }

    @Transactional
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
                    try {
                        if (review.getMember()==null){
                            reviewDto.setReviewId(null);
                            reviewDto.setWriter(null);
                            reviewDto.setWriterName(null);
                        }else {
                            reviewDto.setReviewId(review.getReviewId());
                            reviewDto.setWriter(review.getMember().getEmail());
                            reviewDto.setWriterName(review.getMember().getName());
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                        reviewDto.setReviewId(review.getReviewId());
                        reviewDto.setWriter(review.getMember().getEmail());
                        reviewDto.setWriterName(review.getMember().getName());
                    }


                    reviewDto.setContent(review.getContent());
                    reviewDto.setWritingTime(review.getDateTime());

                    return reviewDto;
                }).collect(Collectors.toList());

        return ResponseDto.setSuccess("Success", reviewDtoList);
    }
}
