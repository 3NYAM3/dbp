package databaseProject.dbp.service;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.Member;
import databaseProject.dbp.domain.Notice;
import databaseProject.dbp.domain.Project;
import databaseProject.dbp.dto.noticeDto.CreateNoticeDto;
import databaseProject.dbp.dto.noticeDto.NoticeDto;
import databaseProject.dbp.repository.MemberRepository;
import databaseProject.dbp.repository.NoticeRepository;
import databaseProject.dbp.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;


    @Transactional
    public ResponseDto<?> createNotice(CreateNoticeDto createNoticeDto, String email, Long projectId) {
        Member member = memberRepository.findByEmail(email);
        String noticeTitle = createNoticeDto.getTitle();
        String content = createNoticeDto.getContent();
        String writer = member.getName();
        String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Project project = projectRepository.findOne(projectId);

        Notice notice = null;

        try {
            notice = Notice.createNotice(project, noticeTitle, writer, content, createDate);
            if (notice == null) return ResponseDto.setFailed("create failed");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        noticeRepository.save(notice);

        return ResponseDto.setSuccessNotIncludeData("Success");

    }

    public ResponseDto<List<NoticeDto>> getNoticeList(Long projectId) {
        List<Notice> notices = null;

        try {
            notices = noticeRepository.findNoticesByProjectId(projectId);
            if (notices == null) return ResponseDto.setFailed("notices get failled");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        List<NoticeDto> noticeDtoList = notices.stream()
                .map(notice -> {
                    NoticeDto noticeDto = new NoticeDto();
                    noticeDto.setNoticeId(notice.getNoticeId());
                    noticeDto.setWriter(notice.getWriter());
                    noticeDto.setTitle(notice.getTitle());
                    noticeDto.setCreateTime(notice.getCreateTime());

                    return noticeDto;
                })
                .collect(Collectors.toList());

        return ResponseDto.setSuccess("Success", noticeDtoList);
    }
}
