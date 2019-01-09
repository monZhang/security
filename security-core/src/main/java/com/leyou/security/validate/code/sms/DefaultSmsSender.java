package com.leyou.security.validate.code.sms;

import com.leyou.security.validate.code.SmsSender;

public class DefaultSmsSender implements SmsSender {

    @Override
    public void sender(String mobile, String code) {
        System.out.println("发送验证码：" + code + " 到手机：" + mobile);
    }

}
