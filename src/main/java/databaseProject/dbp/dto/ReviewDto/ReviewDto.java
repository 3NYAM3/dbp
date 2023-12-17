package databaseProject.dbp.dto.ReviewDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    String writerName;//이름
    String writer;//이메일
    Long reviewId;
    LocalDateTime writingTime;
    String content;
}
