package com.example.outpatient.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/doctor")
@Controller
public class DoctorRouteController {

    @GetMapping({ "/", "/index" })
    public String layout() {
        return "/doctor/layout";
    }

    @GetMapping("/appointment")
    public String appointment() {
        return "/doctor/appointment";
    }

    @GetMapping("/patient")
    public String patient() {
        return "/doctor/patient";
    }
}
