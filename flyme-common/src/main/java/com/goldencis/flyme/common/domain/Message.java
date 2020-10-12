package com.goldencis.flyme.common.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @program: Flyme-Vue
 * @description:
 * @Author: chengl
 * @create: 2020-10-12 14:59
 **/
@Data
@Builder
public class Message {
    private String channel;
    private String content;
}
