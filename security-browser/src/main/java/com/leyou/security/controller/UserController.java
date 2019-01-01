package com.leyou.security.controller;

import com.leyou.security.JsonResult;
import com.leyou.security.entity.User;
import com.leyou.security.param.UserParam;
import com.leyou.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public JsonResult regist(@Valid UserParam userParam) {
        User registUser = userService.regist(userParam.getUsername(), userParam.getPassword());
        return JsonResult.ok();
    }

    @GetMapping("/me")
    public JsonResult user(Authentication authentication) {
        return JsonResult.ok(authentication);
    }

}
