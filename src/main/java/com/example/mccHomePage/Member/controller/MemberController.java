package com.example.mccHomePage.Member.controller;

import com.example.mccHomePage.Member.Dto.MemberDto;
import com.example.mccHomePage.Member.response.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    /*
    *회원가입
    **/
    @PostMapping("/sign")
    public ResponseEntity<MemberResponse> mccSign(@RequestBody MemberDto memberDto){

        String id = memberDto.getMemberNumber();
        String ps = memberDto.getMemberPassword();


        return null;
    }

    /*
     *로그인
     **/

}
