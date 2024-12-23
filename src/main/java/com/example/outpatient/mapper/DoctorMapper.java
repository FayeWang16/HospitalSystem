package com.example.outpatient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.outpatient.domain.entity.Doctor;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface DoctorMapper extends BaseMapper<Doctor> {
}
