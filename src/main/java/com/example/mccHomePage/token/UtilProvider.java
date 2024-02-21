package com.example.mccHomePage.token;

import org.springframework.stereotype.Component;

@Component
public class UtilProvider {

    public String getKey(){
        return "123456789123456789123456789";
    }

    public Long getAccessTokenExpiredTime(){
        //1초 * 1분 * 30분
        return 1000 * 60 * 60L ;
    }
    public Long getRefreshTokenExpiredTime(){
        //1초 * 1분 * 1시간 * 24시간 * 7일
        return 1000 * 60 * 60 * 12 * 7L;
    }
}
