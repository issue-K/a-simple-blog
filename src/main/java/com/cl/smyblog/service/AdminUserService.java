package com.cl.smyblog.service;

import com.cl.smyblog.entity.AdminUser;

public interface AdminUserService {

    AdminUser login(String userName, String password);

    void registry(String username, String password,String nickname);
}
