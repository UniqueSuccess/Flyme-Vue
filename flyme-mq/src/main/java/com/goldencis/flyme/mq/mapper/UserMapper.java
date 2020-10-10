package com.goldencis.flyme.mq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goldencis.flyme.mq.domain.User;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author flyme
 * @since 2020-10-10
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> selectList1();

}
