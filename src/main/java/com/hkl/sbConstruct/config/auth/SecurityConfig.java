package com.hkl.sbConstruct.config.auth;

import com.hkl.sbConstruct.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests() //URL별 권한 관리를 위한 옵션의 시작, authorizeRequests이 선언되어야 andMatchers 옵션 사용 가능
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                    .antMatchers("/v1/**").hasRole(Role.USER.name()) //권한 관리 대상 지정 옵션
                    .anyRequest().authenticated() //설정 값 이외 나머지 URL은 인증된 사용자들에게만 허용하도록.. ( 로그인 )
                .and()
                    .logout().logoutSuccessUrl("/")
                .and()
                    .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService); // 소셜 로그인 성 공 후 진행할 UserService 인터페이스 구현체를 등록,
                                                                                            // 소셜 서비스들에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시
    }
}
