package com.example.yingx_zcz;

import com.baizhi.YingxZczApplication;
import com.baizhi.dao.FeedbackMapper;
import com.baizhi.dao.UserMapper;
import com.baizhi.entity.Feedback;
import com.baizhi.entity.User;
import com.baizhi.service.FeedbackService;
import com.baizhi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = YingxZczApplication.class)
public class tesFeddback {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Resource
    private FeedbackMapper feedbackMapper;
    @Autowired
    private FeedbackService feedbackService;

    @Test
    public  void testUserMapper(){
        List<User> list = userMapper.selectAll();
        for (User user : list) {
            System.out.println(user);
        }

    }
    @Test
    public void testFeedbackqueryall(){
        final List<Feedback> list = feedbackMapper.selectAll();
        for (Feedback feedback : list) {
            System.out.println(feedback);
        }
    }
}
