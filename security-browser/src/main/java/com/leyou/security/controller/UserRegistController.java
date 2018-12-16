package com.leyou.security.controller;

import com.leyou.security.JsonResult;
import com.leyou.security.entity.User;
import com.leyou.security.param.UserParam;
import com.leyou.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserRegistController {

    @Autowired
    private UserService userService;

    @PostMapping
    public JsonResult regist(@Valid UserParam userParam) {
        User registUser = userService.regist(userParam.getUsername(), userParam.getPassword());
        return JsonResult.ok();
    }


    @GetMapping("/{id:\\d+}")
    public JsonResult user(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return JsonResult.ok(user);
    }


}
