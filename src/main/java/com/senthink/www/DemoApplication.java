package com.senthink.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import com.senthink.www.config.ColumbiaProperties;
import com.senthink.www.config.WechatConfig;

@MapperScan("com.senthink.www.dao")
@SpringBootApplication
@EnableConfigurationProperties({ColumbiaProperties.class, WechatConfig.class})
@EnableDiscoveryClient
@RefreshScope
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
