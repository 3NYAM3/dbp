package databaseProject.dbp.controller;


import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.dto.LoggedInMemberDto;
import databaseProject.dbp.dto.LoginDto;
import databaseProject.dbp.dto.LoginResponseDto;
import databaseProject.dbp.dto.SignUpDto;
import databaseProject.dbp.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp (@RequestBody SignUpDto requestBody){
        System.out.println(requestBody.toString());
        ResponseDto<?> result = memberService.join(requestBody);
        return result;
    }

    @PostMapping("/login")
    public ResponseDto<?> login (@RequestBody LoginDto requestBody){
        System.out.println(requestBody.toString());
        ResponseDto<LoginResponseDto> result = memberService.login(requestBody);
        return result;
    }

    @GetMapping("/info")
    public ResponseDto<?> info (@AuthenticationPrincipal String email){
        System.out.println(email);
        ResponseDto<LoggedInMemberDto> result = memberService.getLoginMember(email);
        return result;
    }

}
