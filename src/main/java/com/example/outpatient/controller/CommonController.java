package com.example.outpatient.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.outpatient.domain.ApiResult;
import com.example.outpatient.domain.entity.Doctor;
import com.example.outpatient.domain.entity.User;
import com.example.outpatient.service.IDoctorService;
import com.example.outpatient.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Common API")
@RestController
public class CommonController {

    @Autowired
    IUserService userService;

    @Autowired
    IDoctorService doctorService;

    @Operation(summary = "Get User Role")
    @GetMapping("/getRole")
    public ApiResult getRole(HttpSession session) {
        Object attribute = session.getAttribute("user");
        if (attribute instanceof User) {
            return ApiResult.ok("patient");
        } else {
            return ApiResult.ok("doctor");
        }
    }

    @Operation(summary = "User Login")
    @PostMapping("/doLogin")
    public ApiResult login(@RequestBody User user, HttpSession session) {
        String role = user.getRole();
        if ("patient".equals(role)) {
            // Patient login
            LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery()
                    .eq(User::getEmail, user.getEmail())
                    .eq(User::getPassword, user.getPassword());
            User dbUser = userService.getOne(wrapper);
            if (dbUser == null) {
                return ApiResult.fail("Account does not exist or incorrect password");
            }
            session.setAttribute("user", dbUser);
        } else {
            // Doctor login
            LambdaQueryWrapper<Doctor> wrapper = Wrappers.<Doctor>lambdaQuery()
                    .eq(Doctor::getEmail, user.getEmail())
                    .eq(Doctor::getPassword, user.getPassword());
            Doctor dbDoctor = doctorService.getOne(wrapper);
            if (dbDoctor == null) {
                return ApiResult.fail("Account does not exist or incorrect password");
            }
            session.setAttribute("user", dbDoctor);
        }
        return ApiResult.ok();
    }

    @Operation(summary = "User Registration")
    @PostMapping("/doRegister")
    public ApiResult register(@RequestBody User user) {
        // First, check if the email already exists in the database
        User dbUser = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, user.getEmail()));
        if (dbUser != null) {
            // If the email already exists
            return ApiResult.fail("Email already exists");
        }
        // If the email does not exist, proceed with user registration
        boolean save = userService.save(user);
        return ApiResult.result(save);
    }
}