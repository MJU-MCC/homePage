package com.example.mccHomePage.Member.controller;

import com.example.mccHomePage.Member.dto.ChangePassword;
import com.example.mccHomePage.Member.dto.MemberDto;
import com.example.mccHomePage.Member.response.InfoResponse;
import com.example.mccHomePage.Member.response.MemberResponse;
import com.example.mccHomePage.Member.response.TokenResponse;
import com.example.mccHomePage.Member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static com.example.mccHomePage.Member.message.MemberMessage.*;
import static com.example.mccHomePage.Member.message.TokenMessage.TOKEN_CREATE_FAIL;

@Api(tags = "MCC 동아리 홈페이지 Api 문서")
@RestController
@RequestMapping("/member")
@Slf4j
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
    log.info("회원가입 컨트롤러 진입");
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

        TokenResponse response = memberService.login(id, ps);

        if(response.getMessage().equals(TOKEN_CREATE_FAIL)){
            ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/get/myinfo")
    @Operation(summary = "내 정보 꺼내기 Api" , description = "토큰을 주고 정보를 받습니다.")
    public ResponseEntity<InfoResponse> memberInfo(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication = {}", authentication);
        InfoResponse response = new InfoResponse();

        if (authentication == null) {
            response.setMessage(GET_FAIL_INFO);
            return ResponseEntity.badRequest().body(response);
        }
        String memberNumber = authentication.getPrincipal().toString();

        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ADMIN"::equals);

        log.info("꺼낸 memberNumber = {}" , memberNumber);
        log.info("isUser = {}", isAdmin);

        response.setMemberNumber(memberNumber);
        response.setAdmin(isAdmin);
        response.setMessage(GET_SUCCESS_INFO);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/change/password")
    @Operation(summary = "비밀번호 변경하기 Api" , description = "토큰을 이용하여 비밀번호를 변경합니다.")
    public ResponseEntity<InfoResponse> changePassword(@RequestBody ChangePassword changePassword){

        String currentPassword = changePassword.getCurrentPassword();
        String nextPassword = changePassword.getNextPassword();
        log.info("nextPassword = {} ", nextPassword);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String memberNumber = authentication.getPrincipal().toString();

        InfoResponse response = memberService.changePassword(memberNumber, currentPassword, nextPassword);

        if(response.getMessage().equals(SUCCESS_CHANGE_PASSWORD)){
            return ResponseEntity.ok().body(response);
        }

        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/getTest")
    public ResponseEntity<MemberResponse> responseMem(){
        MemberResponse response = new MemberResponse();
        response.setMessage("Test1");
        return ResponseEntity.ok().body(response);
    }

}
