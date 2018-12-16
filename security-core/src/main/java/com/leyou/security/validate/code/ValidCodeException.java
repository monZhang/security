package com.leyou.security.validate.code;

import org.springframework.security.core.AuthenticationException;

public class ValidCodeException extends AuthenticationException {

    public ValidCodeException(String msg) {
        super(msg);
    }

}
