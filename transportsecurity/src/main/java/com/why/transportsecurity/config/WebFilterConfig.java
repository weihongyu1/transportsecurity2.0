package com.why.transportsecurity.config;

import com.why.transportsecurity.filter.LogFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName：WebFilterConfig
 * @Description：todo web filter 配置
 * @Author: why
 * @DateTime：2021/12/20 15:29
 */
@Configuration
public class WebFilterConfig implements WebMvcConfigurer {
    /**
     * 登陆检查filter配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogFilter()).addPathPatterns("/**");
    }
}
