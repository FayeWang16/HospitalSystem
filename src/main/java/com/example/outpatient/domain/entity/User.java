package com.example.outpatient.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@TableName("user")
@Schema(description = "User Entity")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Password")
    private String password;

    @Schema(description = "Sex")
    private String sex;

    @Schema(description = "Date of Birth")
    private LocalDate birth;

    @Schema(description = "City")
    private String city;

    @Schema(description = "State")
    private String state;

    @Schema(description = "ZIP Code")
    private String zip;

    @Schema(description = "First Name")
    private String name;

    @Schema(description = "Last Name")
    private String firstName;

    @Schema(description = "Address")
    private String address;

    @Schema(description = "Role")
    @TableField(exist = false)
    private String role;

}