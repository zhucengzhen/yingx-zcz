package com.baizhi.serviceimpl;

import com.baizhi.dao.FeedbackMapper;
import com.baizhi.entity.Feedback;
import com.baizhi.entity.FeedbackExample;
import com.baizhi.service.FeedbackService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackMapper feedbackMapper;


    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer rows) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("page",page);
        //创建查询对象
        FeedbackExample example =new FeedbackExample();
        //查询数据总条数
        int count = feedbackMapper.selectCountByExample(example);
        map.put("count",count);
        //总页数=总条数、每页展示的条数  除不尽加一
        int total=count%rows==0?count/rows:count/rows+1;
        map.put("total",total);
        List<Feedback> list = feedbackMapper.selectByExampleAndRowBounds(example, new RowBounds((page - 1) * rows, rows));
        map.put("rows",list);
        return map;
    }
}


