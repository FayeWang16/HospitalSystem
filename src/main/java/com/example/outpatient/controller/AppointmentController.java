package com.example.outpatient.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.outpatient.domain.ApiResult;
import com.example.outpatient.domain.entity.Appointment;
import com.example.outpatient.domain.entity.Doctor;
import com.example.outpatient.domain.entity.User;
import com.example.outpatient.service.IAppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@Tag(name = "Appointment API")
@RequestMapping("/appointment")
@RestController
public class AppointmentController {

    @Autowired
    IAppointmentService appointmentService;

    @Operation(description = "Get paginated appointments")
    @GetMapping("/page")
    public ApiResult<Page<Appointment>> page(Integer page, Integer limit, Integer patientId, Integer doctorId, HttpSession session) {
        Object attribute = session.getAttribute("user");
        LambdaQueryWrapper<Appointment> wrapper = Wrappers.<Appointment>lambdaQuery();
        wrapper.eq(patientId != null, Appointment::getPatientId, patientId);
        wrapper.eq(doctorId != null, Appointment::getDoctorId, doctorId);
        if (attribute instanceof User) {
            wrapper.eq(Appointment::getPatientId, ((User) attribute).getId());
        } else {
            wrapper.eq(Appointment::getDoctorId, ((Doctor) attribute).getId());
        }
        Page<Appointment> e = Page.of(page, limit);
        Page<Appointment> paged = appointmentService.page(e, wrapper);
        return ApiResult.ok(paged);
    }

    @Operation(description = "Get appointment by ID")
    @GetMapping("/{id}")
    public ApiResult<Appointment> one(@PathVariable("id") Integer id){
        Appointment result = appointmentService.getById(id);
        return ApiResult.ok(result);
    }

    @Operation(description = "Add new appointment")
    @PostMapping
    public ApiResult<Boolean> add(@RequestBody Appointment param){
        boolean save = appointmentService.save(param);
        return ApiResult.result(save);
    }

    @Operation(description = "Update appointment")
    @PostMapping("/edit")
    public ApiResult<Boolean> update(@RequestBody Appointment param){
        boolean update = appointmentService.updateById(param);
        return ApiResult.result(update);
    }

    @Operation(description = "Delete appointment(s)")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") Integer[] id){
        boolean batch = appointmentService.removeBatchByIds(Arrays.asList(id));
        return ApiResult.result(batch);
    }

    @Operation(description = "Make an appointment")
    @PostMapping("/operation")
    public ApiResult<Boolean> addAppointment(@RequestBody Appointment appointment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer userId = user.getId();
        appointment.setPatientId(userId);
        boolean save = appointmentService.save(appointment);
        return ApiResult.result(save);
    }
}