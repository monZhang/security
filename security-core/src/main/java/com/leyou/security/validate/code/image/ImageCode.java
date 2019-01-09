package com.leyou.security.validate.code.image;

import com.leyou.security.validate.code.ValidCode;
import com.leyou.security.validate.code.sms.SmsCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;

@Setter
@Getter
@NoArgsConstructor
public class ImageCode extends SmsCode implements ValidCode {

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expirTimeInt) {
        super(code, expirTimeInt);
        this.image = image;
    }
}
