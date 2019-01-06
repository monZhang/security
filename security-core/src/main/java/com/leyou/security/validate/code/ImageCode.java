package com.leyou.security.validate.code;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

@Setter
@Getter
public class ImageCode extends SmsCode {

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expirTimeInt) {
        super(code, expirTimeInt);
        this.image = image;
    }
}
