package com.cl.smyblog.service;

import com.cl.smyblog.entity.AdminUser;

public interface AdminUserService {

    AdminUser login(String userName, String password);

    void registry(String username, String password,String nickname);

    AdminUser getUserById(Integer loginUserId);

    boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);

    boolean updateName(Integer loginUserId, String loginUserName, String nickName);
}
