package com.leyou.security.validate.code.sms;

import com.leyou.security.validate.code.SmsSender;
import com.leyou.security.validate.code.ValidCode;
import com.leyou.security.validate.code.impl.AbstractValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 默认验证码发送器（打印到控制台）
 */
@Slf4j
@Component
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor {

    @Autowired
    private SmsSender smsSender;

    @Override
    protected void sender(ServletWebRequest servletWebRequest, ValidCode validCode) {

        try {
            SmsCode smsCode = (SmsCode) validCode;
            String mobile = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "mobile");
            smsSender.sender(mobile, smsCode.getCode());
        } catch (ServletRequestBindingException e) {
            log.error("发送验证码失败！");
            e.printStackTrace();
        }
    }

}
