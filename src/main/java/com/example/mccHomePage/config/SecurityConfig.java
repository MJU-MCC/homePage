package com.example.mccHomePage.config;

import com.example.mccHomePage.filter.CustomAccessDeniedHandler;
import com.example.mccHomePage.filter.CustomAuthenticaitonEntryPoint;
import com.example.mccHomePage.filter.TokenFilter;
import com.example.mccHomePage.token.TokenUtil;
import com.example.mccHomePage.token.UtilProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticaitonEntryPoint customAuthenticaitonEntryPoint;
    private final TokenUtil tokenUtil;
    private final UtilProvider utilProvider;


    private static final String[] PERMIT_URL_ARRAY = {
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/member/login"
    };

    private static final String[]  NEED_PERMISSION_USER_URL_ARRAY = {
        "/change/password",
        "get/myinfo"
    };

    private static final String[]  NEED_PERMISSION_ADMIN_URL_ARRAY = {
        "/member/sign"
    };



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                .csrf().disable()
                .cors()
                .and()

                .httpBasic().disable()
                .formLogin().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .addFilterBefore(new TokenFilter(tokenUtil), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(NEED_PERMISSION_USER_URL_ARRAY).hasAuthority("USER")
                .antMatchers(NEED_PERMISSION_ADMIN_URL_ARRAY).hasAuthority("ADMIN")
                .antMatchers(PERMIT_URL_ARRAY).permitAll()
                .anyRequest().permitAll()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticaitonEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)

                .and()
                .build();


    }
}
