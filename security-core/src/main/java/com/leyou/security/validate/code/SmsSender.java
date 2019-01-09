package com.leyou.security.validate.code;

/**
 * 短信发送接口
 */
public interface SmsSender {

    void sender(String mobile, String code);

}
