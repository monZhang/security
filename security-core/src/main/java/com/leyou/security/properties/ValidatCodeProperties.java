package com.leyou.security.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 验证码配置类（图片验证码，短信验证码）
 */
@Setter
@Getter
public class ValidatCodeProperties {

    /**
     * 图片验证码配置
     */
    private ImageCodeProperties image = new ImageCodeProperties();

    /**
     * 短信验证码配置
     */
    private SmsCodeProperties sms = new SmsCodeProperties();

}
