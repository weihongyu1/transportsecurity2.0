package com.why.transportsecurity.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName：DruidConfig
 * @Description：todo Druid连接池配置
 * @Author: why
 * @DateTime：2021/12/12 17:52
 */
@Configuration
public class DruidConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }
    /**
     * 配置druid监控
     */
    //1.配置管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new
                StatViewServlet(), "/druid/*");
        //配置初始化参数
        //配置的参数可在StatViewServlet()和StatViewServlet()的ResourceServlet父类中查
        Map<String,String> initParams = new HashMap<>();
        //登录后台系统用户名
        initParams.put("loginUsername","admin");
        //登录后台系统密码
        initParams.put("loginPassword","123456");
        //允许谁访问
        initParams.put("allow","");//第二个参数不写或者为null时默认允许所有访问
        //配置拒绝谁访问
        initParams.put("deny","");
        bean.setInitParameters(initParams);
        return bean;
    }
    //2.配置一个监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        //设置初始化参数
        //配置的参数可在WebStatFilter()中查看
        Map<String,String> initParams = new HashMap<>();
        //排除拦截的请求
        initParams.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        //设置拦截的请求
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
