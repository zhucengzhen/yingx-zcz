package com.baizhi.service;



import com.baizhi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;


public interface UserService {
    HashMap<String,Object> queryAllPage(Integer page, Integer rows);
    void update(User user);

    HashMap<String, Object> add(User user);

    HashMap<String, Object> delete(User user);

    HashMap<String, Object> fileUpload(MultipartFile headImg, String userId);

}
