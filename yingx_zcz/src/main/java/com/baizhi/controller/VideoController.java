package com.baizhi.controller;

import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("video")
public class VideoController {

    private static final Logger log = LoggerFactory.getLogger(VideoController.class);

    @Resource
    VideoService videoService;

    @ResponseBody
    @RequestMapping("queryAllPage")
    public HashMap<String, Object> queryAllPage(Integer page, Integer rows){
        return videoService.queryAllPage(page, rows);
    }

    @ResponseBody
    @RequestMapping("updateStatus")
    public void updateStatus(Video video){
        videoService.update(video);
    }

    @ResponseBody
    @RequestMapping("edit")
    public HashMap<Object, Object> edit(Video video, String oper){
        HashMap<Object, Object> map=null;

        if(oper.equals("add")){
             videoService.add(video);
        }

        if(oper.equals("edit")){
            videoService.update(video);
        }

        if(oper.equals("del")){
             map=videoService.delete(video);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("fileUpload")
    public HashMap<String, Object> fileUpload(MultipartFile videoPath, String videoId){
        System.out.println("文件名:"+videoPath.getOriginalFilename());
        System.out.println("文件大小: "+videoPath.getSize());

        HashMap<String, Object> map = videoService.fileUploadAliyuns(videoPath, videoId);
        return map;
    }

}
