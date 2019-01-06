package com.leyou.security.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcessor {

    String SESSION_KEY_PREFIX = "session_key_prefix";

    void create(ServletWebRequest request);

}
