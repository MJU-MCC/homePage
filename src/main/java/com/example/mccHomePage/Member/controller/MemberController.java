package com.example.mccHomePage.Member.controller;

import com.example.mccHomePage.Member.Dto.MemberDto;
import com.example.mccHomePage.Member.repository.MemberRepository;
import com.example.mccHomePage.Member.response.MemberResponse;
import com.example.mccHomePage.Member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        return ResponseEntity.ok(response);
    }

    /*
     *로그인
     **/

}
