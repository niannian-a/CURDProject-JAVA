package com.example.curd.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.curd.entity.User;
import com.example.curd.mapper.UserMapper;
import com.example.curd.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(String username, String name, String password) {
        // 1. 查询用户名是否已经存在
        User existingUser = getUserByUsername(username);

        if (existingUser != null) {
            return false;
        }

        // 2. 创建用户对象
        User user = new User();
        user.setUsername(username);

        // 3. 加密密码
        user.setUserPassword(passwordEncoder.encode(password));
        //补充：添加名字
        user.setName(name);
        // 4. 保存到数据库
        return save(user);
    }

    @Override
    public User login(String username, String password) {
        // 1. 根据用户名查询用户
        User user = getUserByUsername(username);

        if (user == null) {
            return null;
        }

        // 2. 比较用户输入的密码和数据库中的加密密码
        boolean passwordCorrect = passwordEncoder.matches(password, user.getUserPassword());

        if (!passwordCorrect) {
            return null;
        }

        return user;
    }

    /**
     * 根据用户名查询用户。
     */
    private User getUserByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getUsername, username);

        return getOne(queryWrapper, false);
    }
}

