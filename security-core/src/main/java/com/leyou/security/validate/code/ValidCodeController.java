package com.leyou.security.validate.code;

import com.leyou.security.validate.code.ImageCode;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@RestController
public class ValidCodeController {

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    static final String SESSION_KEY = "sessionKey";

    /**
     * 图片验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/code/image")
    public void generatValidCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ImageCode imageCode = this.createImageCode();
        sessionStrategy.setAttribute(new ServletRequestAttributes(request), SESSION_KEY, imageCode);

        //end 将图片响应给浏览器
        ImageIO.write(imageCode.getImage(), "jpg", response.getOutputStream());
    }

    /**
     * 生成图片验证码
     * @return
     */
    private ImageCode createImageCode() {
        //1 高和宽
        int height = 30;
        int width = 100;
        String data = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();


        //2 创建一个图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //3 获得画板
        Graphics g = image.getGraphics();
        //4 填充一个矩形
        // * 设置颜色
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);
        g.fillRect(1, 1, width - 2, height - 2);
        // * 设置字体
        g.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 25));

        StringBuffer sb = new StringBuffer();

        //5 写随机字
        for (int i = 0; i < 4; i++) {
            // 设置颜色--随机数
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));

            // 获得随机字
            int index = random.nextInt(data.length());
            String str = data.substring(index, index + 1);

            // 写入
            g.drawString(str, width / 6 * (i + 1), 20);

            sb.append(str);
        }

        //6 干扰线
        for (int i = 0; i < 3; i++) {
            // 设置颜色--随机数
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            // 随机绘制先
            g.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
            // 随机点
            g.drawOval(random.nextInt(width), random.nextInt(height), 2, 2);
        }

        return new ImageCode(image, sb.toString(), 60);
    }


}
