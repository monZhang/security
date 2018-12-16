package com.leyou.security.controller;

import com.leyou.security.JsonResult;
import com.leyou.security.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/loginGuide")
public class LoginGuideController {

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @RequestMapping(produces = {"text/html"})
    public void loginAuthenticationRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
    }

    @RequestMapping
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public JsonResult loginAuthenticationJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return JsonResult.build("请引导用户登录！");
    }

}
