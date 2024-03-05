package com.example.mccHomePage.Member.service;

import com.example.mccHomePage.Member.entity.Member;
import com.example.mccHomePage.Member.message.MemberMessage;
import com.example.mccHomePage.Member.message.TokenMessage;
import com.example.mccHomePage.Member.repository.MemberRepository;
import com.example.mccHomePage.Member.response.InfoResponse;
import com.example.mccHomePage.Member.response.MemberResponse;
import com.example.mccHomePage.Member.response.TokenResponse;
import com.example.mccHomePage.token.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.mccHomePage.Member.message.MemberMessage.*;
import static com.example.mccHomePage.Member.message.TokenMessage.TOKEN_CREATE_FAIL;
import static com.example.mccHomePage.Member.message.TokenMessage.TOKEN_CREATE_SUCCESS;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final String TokenType = "Bearer ";

    /**
     * RequiredArgsConstructor 어노테이션을 이용하여 final 키워드가 붙은 필드들은
     * 생성자 주입으로 객체 주입을 받을 수 있도록 한다.
     * cf.final과 RequiredArgsConstructor를 사용하지 않으니 Repository에서 null 예외가 발생하였다.
     */

    private final MemberResponse memberResponse;
    private final TokenResponse tokenResponse;
    private final MemberRepository memberRepository;
    private final TokenUtil tokenUtil;


    public MemberResponse sign(String id , String ps){
        boolean isDuplicate = memberRepository.existsByMemberNumber(id);

        //학번은 중복 될 수 없으니 가입한 학번이라면 중복이라고 알려주기
        if(isDuplicate){
            memberResponse.setMessage(SIGN_DUPLICATE_MEMBERNUMBER);
            return memberResponse;
        }

        boolean isMemberNumber = isRightMemberNumber(id);
        //학번이 제대로 입력이 안될경우 알려주기
        if(!isMemberNumber){
            memberResponse.setMessage(SIGN_RECHECK_MEMBERNUMBER);
            return memberResponse;
        }

        boolean EmptyPassword = isEmptyPassword(ps);
        if(EmptyPassword){
            memberResponse.setMessage(SIGN_FAIL);
            return memberResponse;
        }
        Member signMember;
        if(id.equals("60215206")){
            signMember = Member.builder()
                    .memberNumber(id)
                    .memberPassword(ps)
                    .memberRole("ADMIN")
                    .build();
            memberResponse.setAdmin(true);
        }else {
            signMember = Member.builder()
                    .memberNumber(id)
                    .memberPassword(ps)
                    .memberRole("USER")
                    .build();
            memberResponse.setAdmin(false);
        }
        memberRepository.save(signMember);

        memberResponse.setMessage(SIGN_SUCCESS);
        return memberResponse;
    }
    private boolean isEmptyPassword(String ps){
        /*
        * 비밀번호를 입력하지 않았다면 알려주기
        * 비었다면 true , 비어있지 않다면 false
        * */
        if(!ps.isEmpty()){
            return false;
        }
        return true;
    }
    private boolean isRightMemberNumber(String id){
        /*
         * false를 반환한다면 올바르지 못한 학번
         * true를 반환한다면 올바른 학번
        */

        if(id.length()!= 8 || !id.startsWith("60")){
            //학번이 8글자가 아니거나 8글자인데 60으로 시작하지 않으면 실패
            return false;
        }

        return true;
    }

    public TokenResponse login(String id , String ps){

        Member findMember = memberRepository.findByMemberNumber(id);

        if (findMember.getMemberPassword().equals(ps)) {

            String accessToken = tokenUtil.accessToken(id);
            String refreshToken = tokenUtil.refreshToken();
            tokenResponse.setMessage(TOKEN_CREATE_SUCCESS);
            tokenResponse.setAccessToken(TokenType + accessToken);
            tokenResponse.setRefreshToken(TokenType + refreshToken);

            return  tokenResponse;
        }
        tokenResponse.setMessage(TOKEN_CREATE_FAIL);

        return tokenResponse;
    }

    public InfoResponse changePassword(String memberNumber,String current , String next){
        Member findMember = memberRepository.findByMemberNumber(memberNumber);

        InfoResponse infoResponse = new InfoResponse();

        if(next.isEmpty()){
            infoResponse.setMemberNumber(memberNumber);
            infoResponse.setMessage(INPUT_NOT_PASSWORD);

            return infoResponse;
        }

        if(findMember == null){
            infoResponse.setMemberNumber(memberNumber);
            infoResponse.setMessage(GET_FAIL_INFO);

            return infoResponse;
        }
        if(!findMember.getMemberPassword().equals(current)){
            infoResponse.setMemberNumber(memberNumber);
            infoResponse.setMessage(CORRECT_NOT_PASSWORD);

            return infoResponse;
        }

        findMember.setMemberPassword(next);
        memberRepository.save(findMember);

        infoResponse.setMemberNumber(memberNumber);
        infoResponse.setMessage(SUCCESS_CHANGE_PASSWORD);
        infoResponse.setAdmin(true);

        return  infoResponse;
    }
}
