package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;

    private String title;

    private String content;
    @Column(name = "feed_time")
    private Date feedTime;

}