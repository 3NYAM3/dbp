package databaseProject.dbp.controller;


import databaseProject.dbp.controller.dto.ResponseDto;
import databaseProject.dbp.domain.Member;
import databaseProject.dbp.dto.LoginDto;
import databaseProject.dbp.dto.SignUpDto;
import databaseProject.dbp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
public class MemberController {
    public final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp (@RequestBody SignUpDto requestBody){
        System.out.println(requestBody.toString());

        Member member = new Member();
        member.setName(requestBody.getName());
        member.setEmail(requestBody.getEmail());
        member.setPassword(requestBody.getPassword());

        ResponseDto<?> result = memberService.join(member);
        return result;
    }

    @PostMapping("/login")
    public ResponseDto<?> login (@RequestBody LoginDto requestBody){

    }


}
