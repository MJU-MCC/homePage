package com.example.mccHomePage.token;

import antlr.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class TokenUtil {
    /*
     * 의문점 : 스프링빈을 이용하여 객체로 해도 될거같고 , static메서드를 이용해서 토큰을 생성해도 될꺼같은데 무슨 차이일지 나중에 공부하기
     */
    private final UtilProvider utilProvider;

    public TokenUtil(UtilProvider keyProvider) {
        this.utilProvider = keyProvider;
    }

    public String accessToken(String memberNumber){

        Claims claims = Jwts.claims();
        claims.put("memberNumber" , memberNumber);

        //토큰에 필요한 서명 알고리즘
        byte[] encodeBytes = encodeByte();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + utilProvider.getAccessTokenExpiredTime() ))
                .signWith(SignatureAlgorithm.HS256 , encodeBytes)
                .compact();

    }
    public String refreshToken(){

        //토큰에 필요한 서명 알고리즘
        byte[] encodeBytes = encodeByte();

        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + utilProvider.getRefreshTokenExpiredTime() ))
                .signWith(SignatureAlgorithm.HS256 , encodeBytes)
                .compact();

    }

    /**
     * 토큰의 만료시간 체크하기
     * @param inputToken
     * @return 토큰이 유효한지
     */
    public boolean isExpiredToken(String inputToken){
        return getClaims(inputToken)
                .getExpiration()
                .before(new Date());
    }

    /**
     * 토큰의 Claim에서 학번 꺼내기
     * @param inputToken
     * @return 토큰에 있는 학번 꺼내기
     */
    public String getMemberNumber(String inputToken){
        return getClaims(inputToken)
                .get("memberNumber")
                .toString();
        }

    //jwt 토큰의 구조에서는 Header / payload / Signature가 있는데
    //payload안에 한줄씩을 우리는 Claim이라고 부른다.
    private Claims getClaims(String inputToken){
        return
                Jwts.parser()
                        .setSigningKey(
                                encodeByte()
                        )
                        .parseClaimsJws(inputToken)
                        .getBody();
    }

    // 비밀키 바이트형식으로 변환하기
    private byte[] encodeByte(){
        return Base64.getEncoder().encode(utilProvider.getKey().getBytes());
    }
}
