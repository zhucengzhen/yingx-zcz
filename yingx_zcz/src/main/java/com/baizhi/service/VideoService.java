package com.baizhi.service;

import com.baizhi.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface VideoService {
    HashMap<String,Object> queryAllPage(Integer page, Integer rows);

    void update(Video video);

    HashMap<String, Object> add(Video video);

    HashMap<Object, Object> delete(Video video);
    HashMap<String, Object> fileUploadAliyuns(MultipartFile videoPath, String videoId);
}
