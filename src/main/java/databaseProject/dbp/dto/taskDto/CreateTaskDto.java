package databaseProject.dbp.dto.taskDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {
    private String content;
    private String memo;
    private LocalDate startDate;
    private LocalDate lastDate;
}
