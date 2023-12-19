package databaseProject.dbp.controller;


import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.dto.memberDto.*;
import databaseProject.dbp.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    /**
     * 회원가입
     *
     * @param requestBody
     * @return true of false, message
     */
    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody) {
        ResponseDto<?> result = memberService.join(requestBody);
        return result;
    }

    /**
     * 로그인
     *
     * @param requestBody
     * @return LonginResponseDto
     */
    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody LoginDto requestBody) {
        ResponseDto<LoginResponseDto> result = memberService.login(requestBody);
        return result;
    }

    /**
     * 회원정보 가져오기
     *
     * @param email
     * @return LoginMemberResponseDto
     */
    @GetMapping("/info")
    public ResponseDto<?> info(@AuthenticationPrincipal String email) {
        ResponseDto<LoggedInMemberDto> result = memberService.getLoginMember(email);
        return result;
    }

    /**
     * 비밀번호 변경
     *
     * @param email
     * @return true or false, message
     */
    @PutMapping("/info")
    public ResponseDto<?> changePassword(@AuthenticationPrincipal String email, @RequestBody PasswordDto passwordDto) {
        ResponseDto<?> result = memberService.updatePassword(email, passwordDto);
        return result;
    }

    /**
     * 회원 탈퇴
     *
     * @param email
     * @return true of false, message
     */
    @DeleteMapping("/withdrawal")
    public ResponseDto<?> withdrawMember(@AuthenticationPrincipal String email) {
        ResponseDto<?> result = memberService.withdrawMember(email);
        return result;
    }


}
