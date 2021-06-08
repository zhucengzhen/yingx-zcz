package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Resource
    CategoryService categoryService;
    @ResponseBody
    @RequestMapping("queryAllPage")
    public HashMap<String,Object>queryAllPage(Integer page,Integer rows){
         HashMap<String, Object> map = categoryService.queryAllPage(page, rows);
         return map;
    }
    @ResponseBody
    @RequestMapping("edit")
    public  HashMap<String,Object>edit(Category category,String oper){
        HashMap<String,Object> map=null;
        if(oper.equals("add")){
            categoryService.add(category);
        }if(oper.equals("edit")){
            categoryService.update(category);
        }if(oper.equals("del")){
           map= categoryService.delete(category);
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("queryByTwoCategory")
    public HashMap<String,Object>queryByTwoCategory(Integer page, Integer rows,String parentId){
        return categoryService.queryByTwoCategory(page,rows,parentId);
    }


}
