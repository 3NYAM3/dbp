package databaseProject.dbp.dto.ReviewDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewDto {
    private String content;
}
