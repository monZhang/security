package com.leyou.security.validate.code;

import com.leyou.security.properties.SecurityProperties;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图片验证码生成器
 */
public class ImageCodeGenerator implements ValidCodeGenerator {

    private SecurityProperties securityProperties;

    @Override
    public ImageCode generator(HttpServletRequest request) {
        //1 高和宽(默认从请求中获取，如果没有则从配置文件中读取【自定义会覆盖默认】)
        int height = ServletRequestUtils.getIntParameter(request, "height", securityProperties.getCode().getImage().getHeight());
        int width = ServletRequestUtils.getIntParameter(request, "width", securityProperties.getCode().getImage().getWidth());
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
        for (int i = 0; i < securityProperties.getCode().getImage().getLength(); i++) {
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

        return new ImageCode(image, sb.toString(), securityProperties.getCode().getImage().getExpire());
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
