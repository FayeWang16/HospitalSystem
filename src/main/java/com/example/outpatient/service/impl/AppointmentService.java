package com.example.outpatient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.outpatient.domain.entity.Appointment;
import com.example.outpatient.mapper.AppointmentMapper;
import com.example.outpatient.service.IAppointmentService;
import org.springframework.stereotype.Service;


@Service
public class AppointmentService extends ServiceImpl<AppointmentMapper, Appointment> implements IAppointmentService {
}
