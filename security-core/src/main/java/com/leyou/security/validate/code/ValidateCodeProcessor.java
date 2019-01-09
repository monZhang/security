package com.leyou.security.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码处理
 */
public interface ValidateCodeProcessor {

    String SESSION_KEY_PREFIX = "session_key_prefix";

    void create(ServletWebRequest request);

}
