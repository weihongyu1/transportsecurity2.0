package com.why.transportsecurity.dao;

import com.why.transportsecurity.po.AdminInfo;
import com.why.transportsecurity.po.AdminLogin;
import com.why.transportsecurity.vo.PersonalCenter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

@SpringBootTest
class AdminMapperTest {

    @Autowired
    private AdminMapper adminMapper;

    @Test
    void getAdmin() {
        System.out.println(adminMapper.getAdminLogin("why"));
    }


    @Test
    void insertAdminLogin() {
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        AdminLogin admin = new AdminLogin(null, "yyx", "123", "ordinary");
        adminMapper.insertAdminLogin(admin);
        System.out.println(admin.getId());
    }

    @Test
    void insertAdminInfo() {
        AdminInfo adminInfo = new AdminInfo(1, "15337086013", "488009667@qq.com", "江苏大学", "2021-03-05", "2021-04-06", 2);
        adminMapper.insertAdminInfo(adminInfo);
    }

    @Test
    void queryAdmin() {
        PersonalCenter why = adminMapper.queryAdmin("zlm");
        System.out.println(why);
    }
}