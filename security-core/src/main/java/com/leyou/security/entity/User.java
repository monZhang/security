package com.leyou.security.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 用户实体类
 */
@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    protected Long id;

    protected String username;

    protected String password;

    private String phone;

    private String nickname;

    protected boolean expired;

    protected boolean locked;

    protected boolean enabled;

}
