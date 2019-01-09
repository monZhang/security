package com.leyou.security.validate.code.sms;

import com.leyou.security.properties.SecurityProperties;
import com.leyou.security.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码生成器
 */
public class SmsCodeGenerator implements ValidateCodeGenerator {

    private SecurityProperties securityProperties;

    @Override
    public SmsCode generator(ServletWebRequest servletWebRequest) {

        String randomNum = RandomStringUtils.random(securityProperties.getCode().getSms().getLength());
        return new SmsCode(randomNum, securityProperties.getCode().getSms().getExpire());

    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
