package databaseProject.dbp.dto.memberDto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


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
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$",
//            message = "비밀번호는 영문 대소문자와 숫자, 특수문자를 포함한 8-20자로 입력하세요")
    private String password;
}
