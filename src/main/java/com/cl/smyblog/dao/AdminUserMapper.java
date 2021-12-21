package com.cl.smyblog.dao;

import com.cl.smyblog.entity.AdminUser;
import org.springframework.stereotype.Component;


public interface AdminUserMapper {

    AdminUser login(String userName, String password);

    void registry(String userName, String password,String nickname);

    AdminUser getUserById(Integer loginUserId);

    int updateUser(AdminUser adminUser);
}