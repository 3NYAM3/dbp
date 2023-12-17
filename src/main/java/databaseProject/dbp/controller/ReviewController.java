package databaseProject.dbp.controller;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.Review;
import databaseProject.dbp.dto.ReviewDto.CreateReviewDto;
import databaseProject.dbp.dto.ReviewDto.ReviewDto;
import databaseProject.dbp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/project/dashboard/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 댓글 생성
     * @param email
     * @param noticeId
     * @param createReviewDto
     * @return
     */
    @PostMapping("/{noticeId}")
    public ResponseDto<?> createReview(@AuthenticationPrincipal String email, @PathVariable("noticeId") Long noticeId, @RequestBody CreateReviewDto createReviewDto){
        System.out.println(email+noticeId);
        ResponseDto<?> result = reviewService.createReview(email, noticeId, createReviewDto);
        return result;
    }

    /**
     * 리뷰들 가져오기
     * @param noticeId
     * @return
     */
    @GetMapping("/{noticeId}")
    public ResponseDto<?> getReviews(@PathVariable("noticeId") Long noticeId){
        System.out.println(noticeId);
        ResponseDto<List<ReviewDto>> result = reviewService.getReviews(noticeId);
        return result;
    }

    /**
     * 리뷰 삭제
     * @param email
     * @param reviewId
     * @return
     */
    @DeleteMapping("/{reviewId}")
    public ResponseDto<?> deleteReview(@AuthenticationPrincipal String email, @PathVariable("reviewId") Long reviewId){
        System.out.println(email+reviewId);
        ResponseDto<?> result = reviewService.deleteReview(email, reviewId);
        return result;
    }
}
