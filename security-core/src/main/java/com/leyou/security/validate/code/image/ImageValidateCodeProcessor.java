package com.leyou.security.validate.code.image;

import com.leyou.security.validate.code.ValidCode;
import com.leyou.security.validate.code.impl.AbstractValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

@Slf4j
@Component
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor {

    @Override
    protected void sender(ServletWebRequest servletWebRequest, ValidCode validateCode) {
        try {
            //将图片响应给浏览器
            ImageCode imageCode = (ImageCode) validateCode;
            ImageIO.write(imageCode.getImage(), "jpg", servletWebRequest.getResponse().getOutputStream());
        } catch (IOException e) {
            log.error("响应图片到浏览器发生错误");
            e.printStackTrace();
        }
    }


}
