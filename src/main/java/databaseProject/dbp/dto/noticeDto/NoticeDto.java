package databaseProject.dbp.dto.noticeDto;

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
    private String writer;
    private String email;
    private String createTime;
    private String content;


}
