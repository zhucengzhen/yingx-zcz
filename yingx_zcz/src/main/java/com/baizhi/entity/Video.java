package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "video")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private String id;

    private String title;

    private String description;
    @Column(name = "video_path")
    private String videoPath;
    @Column(name = "cover_path")
    private String coverPath;
    @Column(name = "upload_time")
    private Date uploadTime;
    @Column(name = "like_count")
    private String likeCount;
    @Column(name = "play_count")
    private String playCount;
    @Column(name = "cate_id")
    private String cateId;
    @Column(name = "group_id")
    private String groupId;
    @Column(name = "user_id")
    private String userId;

}