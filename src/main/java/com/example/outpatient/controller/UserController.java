package com.example.outpatient.controller;

import com.example.outpatient.domain.ApiResult;
import com.example.outpatient.domain.entity.User;
import com.example.outpatient.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "User API")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    IUserService userService;

    @Operation(description = "Get User List")
    @GetMapping("/list")
    public ApiResult<List<User>> getUserList() {
        List<User> list = userService.list();
        return ApiResult.ok(list);
    }
}