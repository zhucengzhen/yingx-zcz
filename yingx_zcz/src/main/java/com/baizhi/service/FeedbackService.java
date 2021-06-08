package com.baizhi.service;


import java.util.HashMap;

public interface FeedbackService {
    HashMap<String,Object> queryAllPage(Integer page, Integer rows);

}
