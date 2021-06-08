package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    private String id;

    private String phone;
    @Column(name="head_img")
    private String headImg;

    private String name;

    private String sign;

    private String wechat;

    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="regist_time")
    private Date registTime;


}