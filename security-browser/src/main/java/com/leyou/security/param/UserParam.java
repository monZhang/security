package com.leyou.security.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class UserParam {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
