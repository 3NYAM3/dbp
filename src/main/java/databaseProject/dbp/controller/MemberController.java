package databaseProject.dbp.controller;


import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.dto.memberDto.LoggedInMemberDto;
import databaseProject.dbp.dto.memberDto.LoginDto;
import databaseProject.dbp.dto.memberDto.LoginResponseDto;
import databaseProject.dbp.dto.memberDto.SignUpDto;
import databaseProject.dbp.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/members")
@RequiredArgsConstructor
public class MemberController {


    private final MemberService memberService;


    /**
     * 회원가입
     * @param requestBody
     * @return
     */
    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody) {
        System.out.println(requestBody.toString());
        ResponseDto<?> result = memberService.join(requestBody);
        return result;
    }

    /**
     * 로그인
     * @param requestBody
     * @return
     */
    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody LoginDto requestBody) {
        System.out.println(requestBody.toString());
        ResponseDto<LoginResponseDto> result = memberService.login(requestBody);
        return result;
    }

    /**
     * 회원정보 가져오기
     * @param email
     * @return
     */
    @GetMapping("/info")
    public ResponseDto<?> info(@AuthenticationPrincipal String email) {
        System.out.println(email);
        ResponseDto<LoggedInMemberDto> result = memberService.getLoginMember(email);
        return result;
    }

    /**
     * 비밀번호 변경
     * @param email
     * @param password
     * @return
     */
    @PutMapping("/info")
    public ResponseDto<?> changePassword(@AuthenticationPrincipal String email, @RequestBody Map<String, String>password) {
        System.out.println(email);
        ResponseDto<?> result = memberService.updatePassword(email, password);
        return result;
    }

    /**
     * 회원 탈퇴
     * @param email
     * @return
     */
    @DeleteMapping("/withdrawal")
    public ResponseDto<?> withdrawMember(@AuthenticationPrincipal String email){
        ResponseDto<?> result = memberService.withdrawMember(email);
        return result;
    }



}
