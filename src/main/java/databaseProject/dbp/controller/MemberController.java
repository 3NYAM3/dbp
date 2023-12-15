package databaseProject.dbp.controller;


import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.Member;
import databaseProject.dbp.dto.LoginDto;
import databaseProject.dbp.dto.LoginResponseDto;
import databaseProject.dbp.dto.SignUpDto;
import databaseProject.dbp.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
