package com.goldencis.flyme;

import com.goldencis.flyme.mq.mapper.UserMapper;
import com.goldencis.flyme.mq.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: Flyme-Vue
 * @description:
 * @Author: chengl
 * @create: 2020-10-10 14:22
 **/
public class Test1 {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}