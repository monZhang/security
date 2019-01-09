package com.leyou.security.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 短信验证码配置
 */
@Setter
@Getter
public class SmsCodeProperties {

    /**
     * 过期时间
     */
    private int expire = 60;
    /**
     * 验证码位数
     */
    private int length = 4;
    /**
     * 需要使用验证码的uri
     */
    private String urls;

}
