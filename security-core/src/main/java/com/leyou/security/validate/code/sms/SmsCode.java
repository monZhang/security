package com.leyou.security.validate.code.sms;

import com.leyou.security.validate.code.ValidCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class SmsCode implements ValidCode {

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
