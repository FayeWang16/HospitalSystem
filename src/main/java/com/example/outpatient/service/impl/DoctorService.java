package com.example.outpatient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.outpatient.domain.entity.Doctor;
import com.example.outpatient.mapper.DoctorMapper;
import com.example.outpatient.service.IDoctorService;
import org.springframework.stereotype.Service;


@Service
public class DoctorService extends ServiceImpl<DoctorMapper, Doctor> implements IDoctorService {
}
