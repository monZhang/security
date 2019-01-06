package com.leyou.security.validate.code;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码生成器
 */
public interface ValidCodeGenerator {

    ImageCode generator(HttpServletRequest request);

}
