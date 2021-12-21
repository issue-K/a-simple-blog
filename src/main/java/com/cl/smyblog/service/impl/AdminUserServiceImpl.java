package com.cl.smyblog.service.impl;

import com.cl.smyblog.dao.AdminUserMapper;
import com.cl.smyblog.entity.AdminUser;
import com.cl.smyblog.service.AdminUserService;
import com.cl.smyblog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser login(String userName, String password) {
        AdminUser adminUser = adminUserMapper.login(userName,MD5Util.MD5Encode(password,"UTF-8") );
        return adminUser;
    }

    @Override
    public void registry(String userName, String password,String nickname) {//注册函数
        adminUserMapper.registry( userName, MD5Util.MD5Encode(password,"UTF-8"),nickname );
    }
}
