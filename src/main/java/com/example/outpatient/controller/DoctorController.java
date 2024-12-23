package com.example.outpatient.controller;

import com.example.outpatient.domain.ApiResult;
import com.example.outpatient.domain.entity.Doctor;
import com.example.outpatient.service.IDoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Doctor API")
@RequestMapping("/doctor")
@RestController
public class DoctorController {

    @Autowired
    IDoctorService doctorService;

    @Operation(description = "Get the total number of doctors")
    @GetMapping("/count")
    public ApiResult<Long> getDoctorCount() {
        long count = doctorService.count();
        return ApiResult.ok(count);
    }

    @Operation(description = "Get the list of all doctors")
    @GetMapping("/list")
    public ApiResult<List<Doctor>> getDoctorList() {
        List<Doctor> list = doctorService.list();
        return ApiResult.ok(list);
    }
}