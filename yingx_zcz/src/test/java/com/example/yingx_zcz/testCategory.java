package com.example.yingx_zcz;

import com.baizhi.YingxZczApplication;
import com.baizhi.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = YingxZczApplication.class)
public class testCategory {
    @Resource
    CategoryService categoryService;
    @Test
    public void testqueryPage() {
        HashMap<String, Object> map = categoryService.queryAllPage(1, 4);
        for (Object value : map.values()) {
            System.out.println(value);

        }

    }
}
