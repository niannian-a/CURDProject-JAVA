package com.example.curd.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.curd.entity.User;

public interface UserService extends IService<User> {
    /**
     * 注册用户。
     *
     * @return true 表示注册成功，false 表示用户名已存在
     */
    boolean register(String username, String name, String password);

    /**
     * 登录。
     *
     * @return 登录成功返回用户，登录失败返回 null
     */
    User login(String username, String password);

    boolean deleteUserById(int userid);
    boolean updateUser(int userid, String username, String name, String password);
    User findUserById(int userid);

    boolean addUser( String username, String name, String password);
}