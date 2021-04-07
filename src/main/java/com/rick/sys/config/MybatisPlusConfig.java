package com.rick.sys.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * @Ahthro Rick
 * MybatisPlus 分页插件配置类
 */
@Configurable
@ConditionalOnClass(value = PaginationInterceptor.class)
public class MybatisPlusConfig {


    @Bean
    public PaginationInterceptor paginationInterceptor () {
        return new PaginationInterceptor();
    }
}
