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
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;


    @Transactional
    public ResponseDto<?> createNotice(CreateNoticeDto createNoticeDto, String email, Long projectId) {
        Member member = memberRepository.findByEmail(email);
        String noticeTitle = createNoticeDto.getTitle();
        String content = createNoticeDto.getContent();
        String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Project project = projectRepository.findOne(projectId);

        Notice notice = null;

        try {
            notice = Notice.createNotice(project, noticeTitle, member, content, createDate);
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
                    try {
                        Member member = notice.getMember();
                        if (member != null) {
                            noticeDto.setWriter(member.getName());
                        } else {
                            noticeDto.setWriter(null);
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        noticeDto.setWriter(null);
                    }

                    noticeDto.setContent(notice.getContent());
                    noticeDto.setTitle(notice.getTitle());
                    noticeDto.setCreateTime(notice.getCreateTime());

                    return noticeDto;
                })
                .collect(Collectors.toList());

        return ResponseDto.setSuccess("Success", noticeDtoList);
    }

    public ResponseDto<?> getNotice(Long noticeId) {
        Notice notice = null;

        try {
            notice = noticeRepository.findOne(noticeId);
            if (notice == null) return ResponseDto.setFailed("cannot find notice");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setTitle(notice.getTitle());
        noticeDto.setNoticeId(noticeId);
        try {
            Member member = notice.getMember();
            if (member != null) {
                noticeDto.setWriter(notice.getMember().getName());
                noticeDto.setEmail(notice.getMember().getEmail());
            } else {
                noticeDto.setWriter(null);
                noticeDto.setEmail(null);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        noticeDto.setContent(notice.getContent());
        noticeDto.setCreateTime(notice.getCreateTime());
        return ResponseDto.setSuccess("Success", noticeDto);
    }

    @Transactional
    public ResponseDto<?> deleteNotice(String email, Long noticeId) {
        Member member = memberRepository.findByEmail(email);
        Notice notice = noticeRepository.findOne(noticeId);

        try {
            if (!Objects.equals(member.getMemberId(), notice.getMember().getMemberId())) {
                return ResponseDto.setFailed("not your notice");
            }

            noticeRepository.removeNotice(notice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        return ResponseDto.setSuccessNotIncludeData("Success");
    }

    @Transactional
    public ResponseDto<?> updateNotice(String email, Long noticeId, CreateNoticeDto updateNoticeDto) {
        Member member = null;
        Notice notice = null;
        try {
            member = memberRepository.findByEmail(email);
            notice = noticeRepository.findOne(noticeId);
            if (!Objects.equals(member.getMemberId(), notice.getMember().getMemberId())) {
                return ResponseDto.setFailed("not your notice");
            }

            if (updateNoticeDto.getTitle() != null) {
                notice.setTitle(updateNoticeDto.getTitle());
            }

            if (updateNoticeDto.getContent() != null) {
                notice.setContent(updateNoticeDto.getContent());
            }

            noticeRepository.update(notice);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }
        return ResponseDto.setSuccessNotIncludeData("Success");
    }
}
