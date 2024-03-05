package com.example.mccHomePage.Member.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class InfoResponse {
    boolean isAdmin;
    String memberNumber;
    String message;
}
