package com.goldencis.flyme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * @program: Flyme-Vue
 * @description:
 * @Author: chengl
 * @create: 2020-10-13 10:41
 **/
@Configuration
public class FireWallConfig {

    @Bean
    public HttpFirewall allowUrlSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        firewall.setUnsafeAllowAnyHttpMethod(true);
        firewall.setAllowUrlEncodedPeriod(true);
        return firewall;
    }
}
