package databaseProject.dbp.service;

import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.Member;
import databaseProject.dbp.domain.Notice;
import databaseProject.dbp.domain.Project;
import databaseProject.dbp.domain.Review;
import databaseProject.dbp.dto.noticeDto.CreateNoticeDto;
import databaseProject.dbp.dto.noticeDto.NoticeDto;
import databaseProject.dbp.repository.MemberRepository;
import databaseProject.dbp.repository.NoticeRepository;
import databaseProject.dbp.repository.ProjectRepository;
import databaseProject.dbp.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final ReviewRepository reviewRepository;


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
                    noticeDto.setWriter(notice.getMember().getName());
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

        try{
            notice = noticeRepository.findOne(noticeId);
            if (notice==null) return ResponseDto.setFailed("cannot find notice");

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setTitle(notice.getTitle());
        noticeDto.setNoticeId(noticeId);
        noticeDto.setWriter(notice.getMember().getName());
        noticeDto.setContent(notice.getContent());
        noticeDto.setEmail(notice.getMember().getEmail());
        noticeDto.setCreateTime(notice.getCreateTime());
        return ResponseDto.setSuccess("Success", noticeDto);
    }

    @Transactional
    public ResponseDto<?> deleteNotice(String email, Long noticeId) {
        Member member = memberRepository.findByEmail(email);
        Notice notice = noticeRepository.findOne(noticeId);
        List<Review> reviews = reviewRepository.findByNoticeId(noticeId);

        try {
            if (!Objects.equals(member.getMemberId(), notice.getMember().getMemberId())){
                return ResponseDto.setFailed("not your notice");
            }

            for (Review review: reviews){
                reviewRepository.deleteReview(review);
            }

            noticeRepository.removeNotice(notice);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }

        return ResponseDto.setSuccessNotIncludeData("Success");
    }

    @Transactional
    public ResponseDto<?> updateNotice(String email, Long noticeId, CreateNoticeDto updateNoticeDto) {
        Member member = null;
        Notice notice = null;
        try{
            member = memberRepository.findByEmail(email);
            notice = noticeRepository.findOne(noticeId);
            if(!Objects.equals(member.getMemberId(), notice.getMember().getMemberId())){
                return ResponseDto.setFailed("not your notice");
            }

            if (updateNoticeDto.getTitle()!=null){
                notice.setTitle(updateNoticeDto.getTitle());
            }

            if (updateNoticeDto.getContent()!=null){
                notice.setContent(updateNoticeDto.getContent());
            }

            noticeRepository.update(notice);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("database error");
        }
        return ResponseDto.setSuccessNotIncludeData("Success");
    }
}
