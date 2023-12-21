package databaseProject.dbp.dto.memberDto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    @NotBlank(message = "이메일은 필수입니다.")
    @Email
    private String email;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank
    private String password;
}
