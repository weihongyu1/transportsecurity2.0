package com.why.transportsecurity.config;

import com.why.transportsecurity.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @ClassName：SecurityConfig
 * @Description：todo Spring Security配置类
 * @Author: why
 * @DateTime：2021/12/11 14:23
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminServiceImpl adminService;

    /**
     * Spring Security配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置表单登录
        http.formLogin()
                //登录页面
                .loginPage("/ts/admin/showLogin")
                //登录逻辑
                .loginProcessingUrl("/ts/admin/login")
                //登录成功后跳转
                .successForwardUrl("/ts/accident/warning")
                //登录失败后跳转
                .failureForwardUrl("/ts/admin/toError");

        //设置退出登录
        http.logout()
                //退出登录的接口
                .logoutUrl("/ts/admin/logout")
                //退出登录成功的接口
                .logoutSuccessUrl("/ts/admin/showLogin");

        http.headers().frameOptions().sameOrigin();

        //授权配置
        http.authorizeRequests()
                //定义放行路径
                .antMatchers("/ts/admin/showLogin","/ts/admin/toError","/ts/admin/showRegister","/ts/admin/register"
                            ,"/ts/admin/showFindPassword","/test/user")
                .permitAll()
                //设置认证
                .anyRequest().authenticated();

        //配置remember-me
        http.rememberMe()
                //自定义登录逻辑
                .userDetailsService(adminService)
                //设置数据存储位置
                .tokenRepository(tokenRepository())
                //设置失效时间
                .tokenValiditySeconds(60*60*24*30);

        //关闭csrf防护
        http.csrf().disable();
    }

    /**
     * 密码编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository tokenRepository(){
        //获取jdbc实现类
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
