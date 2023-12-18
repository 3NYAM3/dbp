package databaseProject.dbp.dto.projectDto;

import databaseProject.dbp.domain.Member;
import databaseProject.dbp.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private Long projectId;
    private String projectName;
    private String type;
    private String leaderEmail;
    private LocalDate startDate;
    private LocalDate lastDate;
    private List<Member> members;

}
