package com.example.outpatient.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("appointment")
@Schema(description = "Appointment Entity")
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "Appointment Date")
    private String date;

    @Schema(description = "Patient ID")
    private Integer patientId;

    @Schema(description = "Doctor ID")
    private Integer doctorId;

    @Schema(description = "Symptoms")
    private String symptoms;

    @Schema(description = "Prescriptions")
    private String prescriptions;

}