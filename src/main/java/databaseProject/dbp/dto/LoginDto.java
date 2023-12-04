package databaseProject.dbp.dto;

import databaseProject.dbp.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
    private String email;
    private String password;
    private Member member;
}
