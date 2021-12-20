package com.why.transportsecurity.service;

import com.why.transportsecurity.po.AdminInfo;
import com.why.transportsecurity.po.AdminLogin;
import com.why.transportsecurity.exception.AdminException;
import com.why.transportsecurity.vo.PersonalCenter;

/**
 * @ClassName：AdminService
 * @Description：todo 登录、注册逻辑
 * @Author: why
 * @DateTime：2021/12/11 14:22
 */
public interface AdminService {
    /**
     * 注册
     * @param adminLogin
     */
    void register(AdminLogin adminLogin,String uPhone,String auths) throws AdminException;

    /**
     * 个人中心信息
     * @param username
     * @return
     */
    PersonalCenter personalCenter(String username) throws AdminException;

    /**
     * 更新个人中心
     * @param adminLogin
     * @param adminInfo
     */
    void updatePersonal(AdminLogin adminLogin, AdminInfo adminInfo,String usernameOld) throws AdminException;

    /**
     * 跟新密码
     * @param adminLogin
     */
    void updatePersonalPassword(AdminLogin adminLogin) throws AdminException;
}
