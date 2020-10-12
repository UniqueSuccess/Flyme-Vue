package com.goldencis.flyme.mq.controller;


import com.goldencis.flyme.common.core.domain.AjaxResult;
import com.goldencis.flyme.common.domain.Message;
import com.goldencis.flyme.common.mq.RedisSubPub;
import com.goldencis.flyme.common.utils.StringUtils;
import com.goldencis.flyme.mq.domain.User;
import com.goldencis.flyme.mq.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  测试自定义用户
 * </p>
 *
 * @author flyme
 * @since 2020-10-10
 */
@Api("用户信息管理")
@RestController
@RequestMapping("/mq/user")
public class UserController {

    @Autowired
    private RedisSubPub redisSubPub;

    @Autowired
    private IUserService userService;

    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @ApiOperation("新增用户")
    @ApiImplicitParam(name = "User", value = "新增用户信息", dataType = "User")
    @PostMapping("/save")
    public AjaxResult save(User user)
    {
        if (StringUtils.isNull(user))
        {
            return AjaxResult.error("用户不能为空");
        }
        if(userService.save(user)){
            return AjaxResult.success("保存成功");
        }else {
            return AjaxResult.error("保存失败");
        }
    }

    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @ApiOperation("获取用户详细")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int")
    @GetMapping("/{userId}")
    public AjaxResult getUser(@PathVariable Integer userId)
    {
        User user = userService.getById(userId);
        return AjaxResult.success(user);
    }

    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @ApiOperation("测试redis发送")
    @GetMapping("/test_redis")
    public AjaxResult TestRedis(String messages)
    {
        Message message = Message.builder().build();
        message.setChannel("zhangsan");
        message.setContent(messages);
        redisSubPub.doPublish(message);
        return AjaxResult.success(null);
    }

}
