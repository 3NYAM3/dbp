package databaseProject.dbp.controller;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.dto.noticeDto.CreateNoticeDto;
import databaseProject.dbp.dto.noticeDto.NoticeDto;
import databaseProject.dbp.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/project/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final NoticeService noticeService;

    /**
     * 게시물 만들기
     *
     * @param createNoticeDto
     * @param email
     * @param projectId
     * @return true of false, message
     */
    @PostMapping("/{projectId}/create")
    public ResponseDto<?> createNotice(@RequestBody CreateNoticeDto createNoticeDto, @AuthenticationPrincipal String email, @PathVariable("projectId") Long projectId) {
        ResponseDto<?> result = noticeService.createNotice(createNoticeDto, email, projectId);
        return result;
    }

    /**
     * 게시물 리스트 가져오기
     *
     * @param projectId
     * @return List{NoticeDto},true of false, message
     */
    @GetMapping("/{projectId}/notice")
    public ResponseDto<?> getNoticeList(@PathVariable("projectId") Long projectId) {
        ResponseDto<List<NoticeDto>> result = noticeService.getNoticeList(projectId);
        return result;
    }


    /**
     * 게시물 상세 페이지 정보 가져오기
     *
     * @param noticeId
     * @return Notice ,true of false, message
     */
    @GetMapping("/notice/{noticeId}")
    public ResponseDto<?> getNoticeDetail(@PathVariable("noticeId") Long noticeId) {
        ResponseDto<?> result = noticeService.getNotice(noticeId);
        return result;
    }

    /**
     * 게시물 삭제
     *
     * @param email
     * @param noticeId
     * @return true of false, message
     */
    @DeleteMapping("/{noticeId}")
    public ResponseDto<?> deleteNotice(@AuthenticationPrincipal String email, @PathVariable("noticeId") Long noticeId) {
        ResponseDto<?> result = noticeService.deleteNotice(email, noticeId);
        return result;
    }

    /**
     * 게시물 수정
     *
     * @param email
     * @param noticeId
     * @param updateNoticeDto
     * @return true of false, message
     */
    @PutMapping("/{noticeId}")
    public ResponseDto<?> updateNotice(@AuthenticationPrincipal String email, @PathVariable("noticeId") Long noticeId, @RequestBody CreateNoticeDto updateNoticeDto) {
        ResponseDto<?> result = noticeService.updateNotice(email, noticeId, updateNoticeDto);
        return result;
    }


}
