package com.leyou.security.validate.code;

import com.leyou.security.validate.code.image.ImageCode;
import com.leyou.security.validate.code.sms.SmsCode;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码生成器
 */
public interface ValidateCodeGenerator {

    ValidCode generator(ServletWebRequest servletWebRequest);

}
