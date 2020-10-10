package com.goldencis.flyme.mq.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.goldencis.flyme.common.constant.Constants;
import com.goldencis.flyme.common.core.controller.BaseController;
import com.goldencis.flyme.mq.mapper.UserMapper;
import com.goldencis.flyme.mq.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: ruoyi
 * @description:
 * @Author: chengl
 * @create: 2020-10-09 15:32
 **/
@Slf4j
@RestController
@RequestMapping("/common/mq")
public class TestMqController extends BaseController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test1")
    public String Test(){
        String captchaCodeKey = Constants.CAPTCHA_CODE_KEY;
        return "test1";
    }
    @GetMapping("/test2")
    public String Test2(){
        System.out.println(("----- selectAll method test ------"));
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        System.out.println(11111);
        List<User> userList = userMapper.selectList(null);
        List<User> userList1 = userMapper.selectList1();
        userList.forEach(System.out::println);
        return userList.toString();
    }
    @GetMapping("/test3")
    public String Test3(Integer id){
        System.out.println(("----- selectAll method test ------"));
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        System.out.println(11111);
        User user = userMapper.selectById(id);
        return user.toString();
    }
}
