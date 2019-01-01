package com.leyou.security;

import com.leyou.security.authentication.CustomAutenticationSeccessHandler;
import com.leyou.security.authentication.CustomAuthenticationFailureHandler;
import com.leyou.security.validate.code.ValidCodeFilter;
import com.leyou.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private CustomAutenticationSeccessHandler customAutenticationSeccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new ValidCodeFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/loginGuide")
                .loginProcessingUrl("/login")
                .successHandler(customAutenticationSeccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/loginGuide",
                        "/user",
                        "/code/image",
                        "/" + securityProperties.getBrowser().getRegistPage(),
                        "/" + securityProperties.getBrowser().getLoginPage()
                ).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
