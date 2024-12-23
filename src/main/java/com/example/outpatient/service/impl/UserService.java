package com.example.outpatient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.outpatient.domain.entity.User;
import com.example.outpatient.mapper.UserMapper;
import com.example.outpatient.service.IUserService;
import org.springframework.stereotype.Service;


@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

}
