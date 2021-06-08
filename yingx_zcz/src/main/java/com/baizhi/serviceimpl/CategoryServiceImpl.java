package com.baizhi.serviceimpl;

import com.baizhi.dao.CategoryMapper;
import com.baizhi.entity.Category;
import com.baizhi.entity.CategoryExample;
import com.baizhi.service.CategoryService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Resource
    CategoryMapper categoryMapper;
    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer rows) {
        HashMap<String,Object>map=new HashMap<>();
        map.put("page",page);
        //创建查询条件
         CategoryExample example = new CategoryExample();
         example.createCriteria().andLevelsEqualTo("1");
         //查询数据总条数
         int records = categoryMapper.selectCountByExample(example);
         map.put("records",records);
         //
        int total= records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total",total);
        //分页查询数据  参数：条件，分页对象
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, new RowBounds((page - 1) * rows, rows));
        map.put("rows",categories);
        return map;
    }

    @Override
    public void add(Category category) {
        category.setId(UUID.randomUUID().toString());
        if(category.getParentId()!=null){
            category.setLevels("2");
        }else {
            category.setLevels("1");
            category.setParentId(null);
        }
        categoryMapper.insert(category);

    }

    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);

    }

    @Override
    public HashMap<String, Object> delete(Category category) {
        HashMap<String,Object> map=new HashMap<>();
        //判断删除的是一级类别下是否有二级类别
        //设置查询条件
         CategoryExample example = new CategoryExample();
         example.createCriteria().andParentIdEqualTo(category.getId());
         int count = categoryMapper.selectCountByExample(example);
         if(count==0){
             categoryMapper.delete(category);
             map.put("message","删除一级类别成功");
             map.put("status",100);
         }else {
             //一级类别下有二级类别不能删除
             map.put("message","该类别下有二级类别不能删除");
             map.put("status",104);
         }
        return map;

    }

    @Override
    public HashMap<String, Object> queryByTwoCategory(Integer page, Integer rows, String parentId) {
        HashMap<String,Object>map=new HashMap<>();
        map.put("page",page);
        //创建查询条件
         CategoryExample example = new CategoryExample();
         example.createCriteria().andParentIdEqualTo(parentId);
         //查询数据总条数
         int records = categoryMapper.selectCountByExample(example);
         map.put("records",records);
         int total = records / rows == 0 ? records % rows : records / rows + 1;
         map.put("total",total);
         //分页查询数据  参数：条件 分页对象
         List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, new RowBounds((page - 1) * rows, rows));
        map.put("rows",categories);
        return map;
    }
}
