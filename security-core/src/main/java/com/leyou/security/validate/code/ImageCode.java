package com.leyou.security.validate.code;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Setter
@Getter
public class ImageCode {

    private BufferedImage image;

    private String code;

    private LocalDateTime expirTime;

    public ImageCode(BufferedImage image, String code, int expirTimeInt) {
        this.image = image;
        this.code = code;
        this.expirTime = LocalDateTime.now().plusSeconds(expirTimeInt);
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expirTime) {
        this.image = image;
        this.code = code;
        this.expirTime = expirTime;
    }

    public boolean isExpired() {
        //todo:
        return false;
    }
}