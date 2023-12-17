package databaseProject.dbp.dto.noticeDto;

import databaseProject.dbp.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
    private Long noticeId;
    private String title;
    private Member writer;
    private String createTime;
    private String content;


}
