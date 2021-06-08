package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    UserService userService;
    @ResponseBody
    @RequestMapping("queryAllPage")
    public HashMap<String, Object> queryAllPage(Integer page, Integer rows){
        return userService.queryAllPage(page, rows);
    }
    @ResponseBody
    @RequestMapping("updateStatus")
    public void updateStatus(User user){
        userService.update(user);
    }
    @ResponseBody
    @RequestMapping("edit")
    public HashMap<String, Object>  edit(User user, String oper){
        HashMap<String, Object> map=null;

        if(oper.equals("add")){
           map = userService.add(user);


        }

        if(oper.equals("update")){
            userService.update(user);
        }

        if(oper.equals("del")){
           map= userService.delete(user);
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("fileUpload")
    public HashMap<String, Object> fileUpload(MultipartFile headImg, String userId){
        System.out.println("文件名:"+headImg.getOriginalFilename());
        System.out.println("文件大小: "+headImg.getSize());

        HashMap<String, Object> map = userService.fileUpload(headImg, userId);
        return map;
    }
}
