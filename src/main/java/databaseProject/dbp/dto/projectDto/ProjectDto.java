package databaseProject.dbp.dto.projectDto;

import databaseProject.dbp.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private Long projectId;
    private String title;
    private String type;
    private String leaderEmail;
}
