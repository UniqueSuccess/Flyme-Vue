package com.goldencis.flyme.mq.service.impl;

import com.goldencis.flyme.mq.domain.User;
import com.goldencis.flyme.mq.mapper.UserMapper;
import com.goldencis.flyme.mq.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author flyme
 * @since 2020-10-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
