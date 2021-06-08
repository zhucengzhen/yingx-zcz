package com.baizhi.service;

import com.baizhi.entity.Category;

import java.util.HashMap;


public interface CategoryService {
    HashMap<String,Object> queryAllPage(Integer page, Integer rows);
    void add(Category category);
    void update(Category category);
    HashMap<String,Object>delete(Category category);
    HashMap<String,Object>queryByTwoCategory(Integer page,Integer rows,String parentId);
}
