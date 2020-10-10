package com.goldencis.flyme.config;

import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @program: Flyme-Vue
 * @description:
 * @Author: chengl
 * @create: 2020-10-10 14:59
 **/
@Configuration
public class MybatisPlusConfig {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private MybatisProperties properties;

    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Autowired(required = false)
    private Interceptor[] interceptors;

    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    /**
     * mybatis-plus 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {

        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();

        mybatisPlus.setDataSource(dataSource);

        mybatisPlus.setVfs(SpringBootVFS.class);

        /*if (StringUtils.hasText(this.properties.getConfigLocation())) {

            mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));

        }*/

        if (!ObjectUtils.isEmpty(this.interceptors)) {

            mybatisPlus.setPlugins(this.interceptors);

        }

        MybatisConfiguration mc = new MybatisConfiguration();

        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);

        //数据库字段设计为驼峰命名，默认开启的驼峰转下划线会报错字段找不到

        mc.setMapUnderscoreToCamelCase(true);

        mybatisPlus.setConfiguration(mc);

        if (this.databaseIdProvider != null) {

            mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);

        }

        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {

            mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());

        }

        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {

            mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());

        }

        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {

            mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());

        }

        return mybatisPlus;

    }

}
