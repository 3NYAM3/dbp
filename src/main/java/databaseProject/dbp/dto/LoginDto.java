package databaseProject.dbp.dto;

import databaseProject.dbp.domain.Member;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
