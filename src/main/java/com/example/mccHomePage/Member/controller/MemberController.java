package com.example.mccHomePage.Member.controller;

import com.example.mccHomePage.Member.dto.MemberDto;
import com.example.mccHomePage.Member.response.MemberResponse;
import com.example.mccHomePage.Member.response.TokenResponse;
import com.example.mccHomePage.Member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.mccHomePage.Member.message.MemberMessage.SIGN_SUCCESS;

@RestController
@RequestMapping("/member")
public class MemberController {


    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /*
    *회원가입
    **/
    @PostMapping("/sign")
    public ResponseEntity<MemberResponse> mccSign(@RequestBody MemberDto memberDto){

        String id = memberDto.getMemberNumber();
        String ps = memberDto.getMemberPassword();

        MemberResponse response = memberService.sign(id, ps);

        if(response.getMessage().equals(SIGN_SUCCESS))
            return ResponseEntity.ok(response);

        return  ResponseEntity.badRequest().body(response);
    }

    /*
     *로그인
     **/

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> mcclogin(@RequestBody MemberDto memberDto){
        String id = memberDto.getMemberNumber();
        String ps = memberDto.getMemberPassword();


    }

}
