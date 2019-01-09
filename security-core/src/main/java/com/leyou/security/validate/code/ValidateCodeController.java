package com.leyou.security.validate.code;

import com.leyou.security.validate.code.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@RestController
public class ValidateCodeController {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessor;

    /**
     * 图片验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/code/{type}")
    public void generatValidCode(@PathVariable(value = "type") String type, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ValidateCodeProcessor processor = this.validateCodeProcessor.get(type + "ValidateCodeProcessor");
        if (Objects.isNull(processor)) {
            throw new ValidateCodeException("获取验证码处理器失败,type: " + type);
        }
        processor.create(new ServletWebRequest(request, response));
    }


}
