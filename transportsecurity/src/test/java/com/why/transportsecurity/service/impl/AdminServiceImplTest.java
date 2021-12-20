package com.why.transportsecurity.service.impl;

import com.why.transportsecurity.po.AdminLogin;
import com.why.transportsecurity.exception.AdminException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    private AdminServiceImpl adminService;

    @Test
    void loadUserByUsername() {
    }

    @Test
    void register() {
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        AdminLogin wyy = new AdminLogin(1, "wlin", "123", "ordinary");
        try {
            adminService.register(wyy,"15337086013","admin");
        } catch (AdminException e) {
            e.printStackTrace();
        }
    }
}