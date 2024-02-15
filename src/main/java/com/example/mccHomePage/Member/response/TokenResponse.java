package com.example.mccHomePage.Member.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenResponse {
    private String message;
    private String accessToken;
    private String refreshToken;
}
