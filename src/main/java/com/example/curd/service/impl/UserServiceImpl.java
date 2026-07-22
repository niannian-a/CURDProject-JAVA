package com.example.curd.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.curd.entity.User;
import com.example.curd.mapper.UserMapper;
import com.example.curd.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
