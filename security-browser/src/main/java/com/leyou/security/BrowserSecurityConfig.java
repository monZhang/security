package com.leyou.security;

import com.leyou.security.authentication.CustomAutenticationSuccessHandler;
import com.leyou.security.authentication.CustomAuthenticationFailureHandler;
import com.leyou.security.properties.SecurityProperties;
import com.leyou.security.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomAutenticationSuccessHandler customAutenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validCodeFilter = new ValidateCodeFilter();
        validCodeFilter.setCustomAuthenticationFailureHandler(customAuthenticationFailureHandler);
        validCodeFilter.setSecurityProperties(securityProperties);
        validCodeFilter.initUrls();

        http.addFilterBefore(validCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                    .loginPage("/login-guide")
                    .loginProcessingUrl("/login")
                    .successHandler(customAutenticationSuccessHandler)
                    .failureHandler(customAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                    .antMatchers("/login-guide",
                            "/user",
                            "/code/image",
                            "/" + securityProperties.getBrowser().getRegistPage(),
                            "/" + securityProperties.getBrowser().getLoginPage()
                    ).permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(3600)
                    .userDetailsService(userDetailsService)
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false); //记住我初始化数据库表
        return jdbcTokenRepository;
    }

}
