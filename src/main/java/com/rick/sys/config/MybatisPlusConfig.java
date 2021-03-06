package com.rick.sys.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Ahthro Rick
 * MybatisPlus 分页插件配置类
 */
@Configuration
@ConditionalOnClass(value = PaginationInterceptor.class)
public class MybatisPlusConfig {


    @Bean
    public PaginationInterceptor paginationInterceptor () {
        return new PaginationInterceptor();
    }
}
