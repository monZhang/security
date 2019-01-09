package com.leyou.security.validate.code.impl;

import com.leyou.security.properties.SecurityProperties;
import com.leyou.security.validate.code.ValidCode;
import com.leyou.security.validate.code.ValidateCodeGenerator;
import com.leyou.security.validate.code.ValidateCodeProcessor;
import com.leyou.security.validate.code.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class AbstractValidateCodeProcessor implements ValidateCodeProcessor {

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerator;

    @Autowired
    private SecurityProperties securityProperties;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void create(ServletWebRequest servletWebRequest) {
        //生成验证码
        ValidCode validateCode = this.generator(servletWebRequest);
        //保存验证码（保存到session）
        this.save(servletWebRequest, validateCode);
        //发送验证码
        this.sender(servletWebRequest, validateCode);
    }

    /**
     * 生成验证码
     *
     * @param servletWebRequest
     * @return
     */
    private ValidCode generator(ServletWebRequest servletWebRequest) {
        String type = null;
        try {
            type = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "type");
        } catch (Exception e) {
            log.error("生成验证码时出现异常");
            e.printStackTrace();
        }
        if (Objects.isNull(type)) {
            log.error("生成验证码时传入类型为空");
            throw new ValidateCodeException("生成验证码时传入类型为空" + type);
        }
        //获取生成器
        ValidateCodeGenerator generator = this.validateCodeGenerator.get(type + "CodeGenerator");
        if (Objects.isNull(generator)) {
            log.error("生成验证码时获取生成器失败，type：" + type);
            throw new ValidateCodeException("生成验证码时获取生成器失败，type：" + type);
        }
        //生成并返回
        return generator.generator(servletWebRequest);
    }

    /**
     * 保存验证码
     *
     * @param servletWebRequest
     * @param validCode
     */
    private void save(ServletWebRequest servletWebRequest, ValidCode validCode) {
        sessionStrategy.setAttribute(new ServletRequestAttributes(servletWebRequest.getRequest()),
                securityProperties.getCode().getSESSION_KEY(), validCode);
    }

    /**
     * 发送验证码
     *
     * @param servletWebRequest
     * @param validCode
     */
    protected abstract void sender(ServletWebRequest servletWebRequest, ValidCode validCode);


}
