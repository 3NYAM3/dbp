package databaseProject.dbp.dto;

import databaseProject.dbp.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String token;
    private int exprTime;
    private Member member;
}
