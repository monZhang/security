package com.leyou.security.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 图片验证码配置
 */
@Setter
@Getter
public class ImageCodeProperties {

    /**
     * 图片高度
     */
    private int height = 30;
    /**
     * 图片宽度
     */
    private int width = 100;
    /**
     * 图片过期时间
     */
    private int expire = 60;
    /**
     * 验证码位数
     */
    private int length = 4;
    /**
     * 需要使用验证码的uri
     */
    private String urls;

}
