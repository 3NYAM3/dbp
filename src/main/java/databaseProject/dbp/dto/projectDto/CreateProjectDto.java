package databaseProject.dbp.dto.projectDto;

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
public class CreateProjectDto {
    private String projectName;
    private String type;
    private LocalDate startDate;
    private LocalDate lastDate;
    private List<String> memberList;
}
