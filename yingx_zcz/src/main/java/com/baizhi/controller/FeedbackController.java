package com.baizhi.controller;
import com.baizhi.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;

@Controller
@RequestMapping("feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    @ResponseBody
    @RequestMapping("queryAllPage")
    public HashMap<String,Object>queryAllPage(Integer page,Integer rows){
        HashMap<String, Object> map = feedbackService.queryAllPage(page, rows);
        return map;

    }
}