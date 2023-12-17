package databaseProject.dbp.controller;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.dto.ReviewDto.CreateReviewDto;
import databaseProject.dbp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/project/dashboard/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{noticeId}")
    public ResponseDto<?> createReview(@AuthenticationPrincipal String email, @PathVariable("noticeId") Long noticeId, @RequestBody CreateReviewDto createReviewDto){
        System.out.println(email+noticeId);
        ResponseDto<?> result = reviewService.createReview(email, noticeId, createReviewDto);
        return result;
    }
}
