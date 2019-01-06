package com.leyou.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "custom-security")
public class SecurityProperties {

    /**
     * 浏览器相关配置
     */
    private BrowserProperties browser = new BrowserProperties();

    /**
     * 验证码相关配置
     */
    private ValidatCodeProperties code = new ValidatCodeProperties();





}
