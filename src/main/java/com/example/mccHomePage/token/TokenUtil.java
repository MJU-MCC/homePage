package com.example.mccHomePage.token;

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
        byte[] encodeBytes = Base64.getEncoder().encode(utilProvider.getKey().getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + utilProvider.getAccessTokenExpiredTime() ))
                .signWith(SignatureAlgorithm.HS256 , encodeBytes)
                .compact();

    }
    public String refreshToken(String memberNumber){

        //토큰에 필요한 서명 알고리즘
        byte[] encodeBytes = Base64.getEncoder().encode(utilProvider.getKey().getBytes());

        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + utilProvider.getRefreshTokenExpiredTime() ))
                .signWith(SignatureAlgorithm.HS256 , encodeBytes)
                .compact();

    }

}
