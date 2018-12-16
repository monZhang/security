package com.leyou.security.service;

import com.leyou.security.entity.User;

public interface UserService {

    User regist(String username ,String password);


    User findUserById(Long id);
}
