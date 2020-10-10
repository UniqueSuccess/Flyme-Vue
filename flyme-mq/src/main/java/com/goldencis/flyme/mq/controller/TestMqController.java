package com.goldencis.flyme.mq.controller;

import com.goldencis.flyme.common.constant.Constants;
import com.goldencis.flyme.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ruoyi
 * @description:
 * @Author: chengl
 * @create: 2020-10-09 15:32
 **/

@RestController
@RequestMapping("/common/mq")
public class TestMqController extends BaseController {
    @GetMapping("/test1")
    public String Test(){
        String captchaCodeKey = Constants.CAPTCHA_CODE_KEY;
        return "test1";
    }
}
