package com.why.transportsecurity.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName：SocketConfig
 * @Description：todo Socket监听端口和线程池配置
 * @Author: why
 * @DateTime：2021/12/20 16:18
 */
@Setter
@Getter
@ToString
@Component
@Configuration
@PropertySource("classpath:application.yaml")
@ConfigurationProperties(prefix = "socket")
public class SocketConfig {
    private Integer port;
    private Integer poolKeep;
    private Integer poolCore;
    private Integer poolMax;
    private Integer poolQueueInit;
}
