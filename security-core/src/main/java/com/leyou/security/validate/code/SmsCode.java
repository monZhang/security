package com.leyou.security.validate.code;

import java.time.LocalDateTime;

public class SmsCode {

    private String code;

    private LocalDateTime expirTime;

    public SmsCode(String code, int expirTimeInt) {
        this.code = code;
        this.expirTime = LocalDateTime.now().plusSeconds(expirTimeInt);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirTime);
    }

}
