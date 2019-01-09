package com.leyou.security.validate.code;

import com.leyou.security.authentication.CustomAuthenticationFailureHandler;
import com.leyou.security.properties.SecurityProperties;
import com.leyou.security.validate.code.exception.ValidateCodeException;
import com.leyou.security.validate.code.image.ImageCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 验证码过滤器，此filter先于usernamePasswordAuthenticationFilter执行
 */
public class ValidateCodeFilter extends OncePerRequestFilter {


    private SessionStrategy sessionSessionStrategy = new HttpSessionSessionStrategy();
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private SecurityProperties securityProperties;

    private Set<String> urls = new HashSet<>();

    public void initUrls() {
        String urls = securityProperties.getCode().getImage().getUrls();
        if (StringUtils.isNotEmpty(urls)) {
            for (String url : urls.split(",")) {
                this.urls.add(url);
            }
        }
        this.urls.add("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean needValid = false;
        for (String url : urls) {
            boolean match = antPathMatcher.match(url, request.getRequestURI());
            if (match) {
                needValid = true;
                break;
            }
        }

        if (needValid) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException ve) {
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, ve);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {

        Object imageCodeObj = sessionSessionStrategy.getAttribute(request, securityProperties.getCode().getSESSION_KEY());
        String imgCode = ServletRequestUtils.getStringParameter(request.getRequest(), "imgCode");

        if (Objects.isNull(imageCodeObj)) {
            throw new ValidateCodeException("验证码为空！！");
        }

        ImageCode imageCode = (ImageCode) imageCodeObj;

        if (StringUtils.isBlank(imageCode.getCode())) {
            throw new ValidateCodeException("验证码为空！！！");
        }

        if (imageCode.isExpired()) {
            sessionSessionStrategy.removeAttribute(request, securityProperties.getCode().getSESSION_KEY());
            throw new ValidateCodeException("验证码已过期！！！");
        }

        if (!StringUtils.equalsIgnoreCase(imageCode.getCode(), imgCode)) {
            throw new ValidateCodeException("验证码错误！！！");
        }

        sessionSessionStrategy.removeAttribute(request, securityProperties.getCode().getSESSION_KEY());
    }

    public void setCustomAuthenticationFailureHandler(CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
