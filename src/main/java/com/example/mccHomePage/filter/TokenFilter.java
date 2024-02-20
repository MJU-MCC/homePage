package com.example.mccHomePage.filter;

import com.example.mccHomePage.token.TokenUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TokenFilter extends OncePerRequestFilter {

    private TokenUtil tokenUtil;

    public TokenFilter(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        /**
         * 요청에 담긴 헤더에 Authorization을 꺼내서 값이 있는지 체크하고
         * 없으면 다음 필터로 넘기고 (인증을 요하는 것이라면 다음 필터에서 deny가 될 것이다.
         * 로그인 , 회원가입은 다음 필터를 거쳐서 토큰을 받아야하니
         * "Bearer "이 아닌것은 걸러내고
         * 찐토큰들을 받아서 TokenUtil에서 만든 메서드 중에서 토큰이 유효한지 체크
         * 인증토큰을 만들고나서 인증토큰에 Details를 세팅
         * 인증토큰을 SecurityContextHolder안에 있는 SecurityContext에 인증을 추가
         */
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorization == null){
            filterChain.doFilter(request,response);
            return;
        }

        if(!authorization.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }


        String tk = authorization.split(" ")[1];

        if(tokenUtil.isExpiredToken(tk)){
            filterChain.doFilter(request,response);
            return;
        }
        String memberNumber = tokenUtil.getMemberNumber(tk);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(memberNumber, null, List.of(new SimpleGrantedAuthority("USER")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
