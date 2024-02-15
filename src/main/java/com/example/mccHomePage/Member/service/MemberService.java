package com.example.mccHomePage.Member.service;

import com.example.mccHomePage.Member.entity.Member;
import com.example.mccHomePage.Member.message.MemberMessage;
import com.example.mccHomePage.Member.repository.MemberRepository;
import com.example.mccHomePage.Member.response.MemberResponse;
import org.springframework.stereotype.Service;

import static com.example.mccHomePage.Member.message.MemberMessage.*;

@Service
public class MemberService {

    private MemberResponse response;
    private MemberRepository memberRepository;

    public MemberService(MemberResponse response) {
        this.response = response;
    }

    public MemberResponse sign(String id , String ps){
        boolean isDuplicate = memberRepository.existsByMemberNumber(id);

        //학번은 중복 될 수 없으니 가입한 학번이라면 중복이라고 알려주기
        if(isDuplicate){
            response.setMessage(SIGN_DUPLICATE_MEMBERNUMBER);
            return response;
        }

        boolean isMemberNumber = isRightMemberNumber(id);
        //학번이 제대로 입력이 안될경우 알려주기
        if(isMemberNumber){
            response.setMessage(SIGN_RECHECK_MEMBERNUMBER);
            return response;
        }

        boolean EmptyPassword = isEmptyPassword(ps);
        if(EmptyPassword){
            response.setMessage(SIGN_FAIL);
            return response;
        }

        Member signMember = Member.builder()
                .memberNumber(id)
                .memberPassword(ps)
                .memberRole("USER")
                .build();

        memberRepository.save(signMember);

        response.setMessage(SIGN_SUCCESS);
        return response;
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

        if(id.length()!=8 || id.startsWith("60")){
            //학번이 8글자가 아니거나 8글자인데 60으로 시작하지 않으면 실패
            return false;
        }

        return true;
    }
}
