package com.example.mccHomePage.filter;

import com.example.mccHomePage.Member.message.MemberMessage;
import com.example.mccHomePage.Member.response.MemberResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticaitonEntryPoint implements AuthenticationEntryPoint {

    private final MemberResponse memberResponse;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ObjectMapper om = new ObjectMapper();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        memberResponse.setMessage(MemberMessage.NOT_AUTHENTICATE);
        memberResponse.setAdmin(false);

        String result = om.writeValueAsString(memberResponse);
        response.getWriter().write(result);
    }
}
