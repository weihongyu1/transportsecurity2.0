package com.why.transportsecurity.service.impl;

import com.why.transportsecurity.utils.commonutils.EmptyUtils;
import com.why.transportsecurity.dao.AdminMapper;
import com.why.transportsecurity.po.AdminInfo;
import com.why.transportsecurity.po.AdminLogin;
import com.why.transportsecurity.exception.AdminException;
import com.why.transportsecurity.vo.PersonalCenter;
import com.why.transportsecurity.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @ClassName：AdminServiceImpl
 * @Description：todo AdminService实现类
 * @Author: why
 * @DateTime：2021/12/11 14:27
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService, UserDetailsService {

    //注入密码解析对象
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 自定义登录逻辑
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("parameter?username = " + username);
        if (username == null || username.equals("")){
            throw new UsernameNotFoundException("Parameter verification failed");
        }
        //1. 根据用户名查询数据库
        log.info("find user in database");
        AdminLogin adminLogin = adminMapper.getAdminLogin(username);
        if (!adminLogin.getUsername().equals(username)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //2.根据查询对象比较密码
        log.info("Compare user passwords");
        String encode = adminLogin.getPassword();
        //3.根据查询的对象设置权限
        log.info("Setting auths");
        AdminLogin admin = new AdminLogin(adminLogin.getId(), username, encode,"admin");
        log.info(admin.toString());
        return admin;
    }

    /**
     * 注册功能
     * @param adminLogin
     */
    @Override
    public void register(AdminLogin adminLogin,String uPhone,String auths) throws AdminException {
        log.info("parameter?adminLogin = " + adminLogin.toString() + "&uPhone = "+ uPhone + "&auths = " + auths);
        log.info("Validating parameters");
        if (adminLogin == null || adminLogin.getUsername() == null || adminLogin.getPassword() == null){
            throw new AdminException(1001,"Parameter verification failed");
        }
        //查询用户是否存在
        log.info("Query whether the user already exists");
        AdminLogin admin = adminMapper.getAdminLogin(adminLogin.getUsername());
        if (admin != null){
            throw new AdminException(1002,"用户已存在");
        }
        //注册
        log.info("Writing to the tbl_admin_login");
        //写入管理员登录信息
        String encode = passwordEncoder.encode(adminLogin.getPassword());
        AdminLogin adminNew = new AdminLogin(null, adminLogin.getUsername(), encode, auths);
        adminMapper.insertAdminLogin(adminNew);
        Integer uId = adminNew.getId();
        log.info("writing to the tbl_admin_info");
        //写入管理员信息
        adminMapper.insertAdminInfo(new AdminInfo(uPhone,uId));
    }

    @Override
    public PersonalCenter personalCenter(String username) throws AdminException {
        log.info("parameter?username = " + username);
        if (username == null || username.equals("")){
            throw new AdminException(1001,"Parameter verification failed");
        }
        log.info("find admin in database");
        PersonalCenter personalCenter = adminMapper.queryAdmin(username);
        if (personalCenter == null){
            throw new AdminException(1003,"未查询到该用户的相关信息");
        }
        return personalCenter;
    }

    @Override
    public void updatePersonal(AdminLogin adminLogin, AdminInfo adminInfo,String usernameOld) throws AdminException {
        log.info("parameter?username = " + adminLogin + "&adminInfo = " + adminInfo + "&usernameOld = " + usernameOld);
        boolean empty = EmptyUtils.isEmpty(adminLogin, adminInfo, usernameOld);
        if (empty){
            throw new AdminException(1001,"Parameter verification failed");
        }
        log.info("Updating database");
        adminMapper.updateAdminLogin(adminLogin,usernameOld);
        adminMapper.updateAdminInfo(adminInfo);
        AdminLogin adminLoginNew = adminMapper.getAdminLogin(adminLogin.getUsername());
        //将新的用户名密码加入当前登录状态,并保持登录状态
        log.info("Add the new username and password to the current login state");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdminLogin userDetails = (AdminLogin) authentication.getPrincipal();
        userDetails.setUsername(adminLogin.getUsername());
//        UsernamePasswordAuthenticationToken authentication =
//                new UsernamePasswordAuthenticationToken(adminLoginNew.getUsername(), adminLoginNew.getPassword());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void updatePersonalPassword(AdminLogin adminLogin) throws AdminException {
        log.info("parameter?adminLogin = " + adminLogin);
        boolean empty = EmptyUtils.isEmpty(adminLogin);
        if (empty){
            throw new AdminException(1001,"Parameter verification failed");
        }
        String encode = passwordEncoder.encode(adminLogin.getPassword());
        adminLogin.setPassword(encode);
        adminMapper.updateAdminLogin(adminLogin,adminLogin.getUsername());
    }
}
