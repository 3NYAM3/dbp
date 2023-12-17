package databaseProject.dbp.controller;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.dto.noticeDto.CreateNoticeDto;
import databaseProject.dbp.dto.noticeDto.NoticeDto;
import databaseProject.dbp.service.NoticeService;
import databaseProject.dbp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/project/dashboard/{projectId}")
@RequiredArgsConstructor
public class DashboardController {

    private final ProjectService projectService;
    private final NoticeService noticeService;

    @PostMapping("/create")
    public ResponseDto<?> createNotice(@RequestBody CreateNoticeDto createNoticeDto, @AuthenticationPrincipal String email, @PathVariable("projectId") Long projectId){
        System.out.println(createNoticeDto);
        System.out.println(email);
        System.out.println(projectId);
        ResponseDto<?> result = noticeService.createNotice(createNoticeDto, email, projectId);
        return result;
    }
    @GetMapping("/notice")
    public ResponseDto<?> getNoticeList(@PathVariable("projectId") Long projectId){
        ResponseDto<List<NoticeDto>> result = noticeService.getNoticeList(projectId);
        return result;
    }
    @GetMapping("/")
    public ResponseDto<?> getProjectDetail(@PathVariable("projectId") Long projectId){
        System.out.println(projectId);
        ResponseDto<?> result = projectService.getProject(projectId);
        return result;
    }

//    @GetMapping("/")
//    public ResponseDto<?> getNoticeDetail(@)


}
