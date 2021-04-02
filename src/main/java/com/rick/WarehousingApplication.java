package com.rick;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.rick.sys.mapper"})
public class WarehousingApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehousingApplication.class, args);
    }

}
