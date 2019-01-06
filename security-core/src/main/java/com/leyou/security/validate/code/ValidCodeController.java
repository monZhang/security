package com.leyou.security.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ValidCodeController {

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    static final String SESSION_KEY = "image-code";

    @Autowired
    private ValidCodeGenerator imageCodeGenerator;

    /**
     * 图片验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/code/image")
    public void generatValidCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ImageCode imageCode = imageCodeGenerator.generator(request);
        sessionStrategy.setAttribute(new ServletRequestAttributes(request), SESSION_KEY, imageCode);
        //将图片响应给浏览器
        ImageIO.write(imageCode.getImage(), "jpg", response.getOutputStream());
    }


}
