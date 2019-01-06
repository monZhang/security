package com.leyou.security.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BrowserProperties {

    private String loginPage = "login.html";

    private String registPage = "regist.html";

    private LoginType loginType = LoginType.JSON;

}
