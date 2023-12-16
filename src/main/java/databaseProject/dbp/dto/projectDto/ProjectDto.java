package databaseProject.dbp.dto.projectDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private String title;
    private String type;
    private String leaderEmail;
}
