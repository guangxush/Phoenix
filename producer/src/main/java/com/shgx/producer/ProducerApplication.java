package com.shgx.producer;

import org.springframework.boot.SpringApplication;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 开启dubbo的自动配置
@EnableDubboConfiguration
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

}
