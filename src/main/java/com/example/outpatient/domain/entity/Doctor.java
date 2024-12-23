package com.example.outpatient.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("doctor")
@Schema(description = "Doctor Entity")
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Password")
    private String password;

    @Schema(description = "Doctor Name")
    @TableField("doctor_name")
    private String name;

    @Schema(description = "Technical Title")
    private String technicalTitle;

    @Schema(description = "Rating")
    private String rate;

    @Schema(description = "Location")
    private String location;

    @Schema(description = "Avatar")
    private String avatar;

    @TableField(exist = false)
    private String role;

}