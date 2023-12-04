package databaseProject.dbp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

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
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$",
            message = "비밀번호는 영문 대소문자와 숫자, 특수문자를 포함한 8-20자로 입력하세요")
    private String password;



}
