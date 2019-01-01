package com.leyou.security.validate.code;

import com.leyou.security.authentication.CustomAuthenticationFailureHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class ValidCodeFilter extends OncePerRequestFilter {

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    SessionStrategy sessionSessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals(request.getRequestURI(), "/login") && StringUtils.equals(request.getMethod(), "POST")) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidCodeException ve) {
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, ve);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {

        Object imageCodeObj = sessionSessionStrategy.getAttribute(request, ValidCodeController.SESSION_KEY);
        String imgCode = ServletRequestUtils.getStringParameter(request.getRequest(), "imgCode");

        if (Objects.isNull(imageCodeObj)) {
            throw new ValidCodeException("验证码为空！！");
        }

        ImageCode imageCode = (ImageCode) imageCodeObj;

        if (StringUtils.isBlank(imageCode.getCode())) {
            throw new ValidCodeException("验证码为空！！！");
        }

        if (imageCode.isExpired()) {
            sessionSessionStrategy.removeAttribute(request, ValidCodeController.SESSION_KEY);
            throw new ValidCodeException("验证码已过期！！！");
        }

        if (!StringUtils.equalsIgnoreCase(imageCode.getCode(), imgCode)) {
            throw new ValidCodeException("验证码错误！！！");
        }

        sessionSessionStrategy.removeAttribute(request, ValidCodeController.SESSION_KEY);
    }


}
