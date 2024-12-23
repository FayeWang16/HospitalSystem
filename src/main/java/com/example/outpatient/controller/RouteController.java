package com.example.outpatient.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.outpatient.domain.entity.Appointment;
import com.example.outpatient.service.IAppointmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RouteController {

    @Autowired
    IAppointmentService appointmentService;

    @GetMapping({ "/", "/index" })
    public String layout() {
        return "/index";
    }

    @GetMapping("/appointment/dialog")
    public ModelAndView appointmentDialog(Integer id) {
        ModelAndView view = new ModelAndView();
        if (ObjectUtil.isNotNull(id)) {
            Appointment appointment = appointmentService.getById(id);
            view.addObject("appointment", appointment);
        }
        view.setViewName("/doctor/appointmentDialog");
        return view;
    }

    @GetMapping("/appointment")
    public String appointment(Integer doctorId) {
        return "/appointment";
    }

    @GetMapping("/doctors")
    public String doctors() {
        return "/doctors";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }
}
