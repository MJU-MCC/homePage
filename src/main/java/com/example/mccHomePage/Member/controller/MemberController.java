package com.example.mccHomePage.Member.controller;

import com.example.mccHomePage.Member.dto.MemberDto;
import com.example.mccHomePage.Member.response.MemberResponse;
import com.example.mccHomePage.Member.response.TokenResponse;
import com.example.mccHomePage.Member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "회원 가입" , description = "학번 과 비밀번호를 입력 받아야합니다.")
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
    @Operation(summary = "로그인 Api" , description = "로그인을 통해 토큰을 발급합니다.")
    public ResponseEntity<TokenResponse> mcclogin(@RequestBody MemberDto memberDto){
        String id = memberDto.getMemberNumber();
        String ps = memberDto.getMemberPassword();


    }

}
