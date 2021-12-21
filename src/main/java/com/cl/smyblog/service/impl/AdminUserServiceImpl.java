package com.cl.smyblog.service.impl;

import com.cl.smyblog.dao.AdminUserMapper;
import com.cl.smyblog.entity.AdminUser;
import com.cl.smyblog.service.AdminUserService;
import com.cl.smyblog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser login(String userName, String password) {
        AdminUser adminUser = adminUserMapper.login(userName, MD5Util.MD5Encode(password, "UTF-8"));
        return adminUser;
    }

    @Override
    public void registry(String userName, String password, String nickname) {//注册函数
        adminUserMapper.registry(userName, MD5Util.MD5Encode(password, "UTF-8"), nickname);
    }

    @Override
    public AdminUser getUserById(Integer loginUserId) {
        return adminUserMapper.getUserById(loginUserId);
    }

    @Override//更新名称
    public boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
        AdminUser adminUser = adminUserMapper.getUserById(loginUserId);
        if (adminUser != null) {
            adminUser.setNickName(nickName);
            adminUser.setLoginUserName(loginUserName);
            if (adminUserMapper.updateUser(adminUser) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override//更新密码
    public boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.getUserById(loginUserId);
        if (adminUser != null) {
            String now = MD5Util.MD5Encode(originalPassword, "UTF-8");
            if (now.equals(adminUser.getLoginPassword())) {//原密码正确,可以修改
                adminUser.setLoginPassword(MD5Util.MD5Encode(newPassword, "UTF-8"));
                if (adminUserMapper.updateUser(adminUser) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

}