package com.leyou.security.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public abstract class AbstractValidateCodeProcessor<T> implements ValidateCodeProcessor {

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerator;

    @Override
    public void create(ServletWebRequest request) {
        T validateCode = this.generator(request);
        this.save(request, validateCode);
        this.sender(request, validateCode);
    }

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    T generator(ServletWebRequest request) {

        return null;
    }

    /**
     * 保存验证码
     *
     * @param request
     * @param validateCode
     */
    void save(ServletWebRequest request, T validateCode) {


    }

    /**
     * 发送验证码
     *
     * @param request
     * @param validateCode
     */
    protected abstract void sender(ServletWebRequest request, T validateCode);


}
