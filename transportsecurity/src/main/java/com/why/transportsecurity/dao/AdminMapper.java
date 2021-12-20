package com.why.transportsecurity.dao;

import com.why.transportsecurity.po.AdminInfo;
import com.why.transportsecurity.po.AdminLogin;
import com.why.transportsecurity.vo.PersonalCenter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @InterfaceName：AdminDao
 * @Description： 用户登录数据库接口
 * @Author: why
 * @Date：2021/12/11
 **/
@Mapper
public interface AdminMapper {
    /**
     * 根据用户名查询admin
     * @param username
     */
    AdminLogin getAdminLogin(String username);

    /**
     * 插入用户登录信息
     * @param adminLogin
     * @return
     */
    int insertAdminLogin(AdminLogin adminLogin);

    /**
     * 插入用户基础信息
     * @param adminInfo
     */
    void insertAdminInfo(AdminInfo adminInfo);

    /**
     * 根据用户名查询用户个人中信息
     * @param uName
     * @return
     */
    PersonalCenter queryAdmin(String uName);

    /**
     * 更新tbl_admin_login的信息
     * @param adminLogin
     * @param usernameOld
     */
    void updateAdminLogin(@Param("AdminLogin") AdminLogin adminLogin, @Param("usernameOld") String usernameOld);

    /**
     * 更新tbl_admin_info的信息
     * @param adminInfo
     */
    void updateAdminInfo(AdminInfo adminInfo);
}
