package com.leyou.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "custom-security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();


}
