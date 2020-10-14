package com.goldencis.flyme.common.domain;

import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @program: Flyme-Vue
 * @description:
 * @Author: chengl
 * @create: 2020-10-13 14:39
 **/
@Data
@Builder
public class MessageHandlerMethod {
    private Object target;
    private Method method;
}
