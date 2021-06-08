package com.example.yingx_zcz;

import com.baizhi.YingxZczApplication;
import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = YingxZczApplication.class)
public class YingxZczApplicationTests {
    @Resource
    private AdminDAO adminDAO;
    @Autowired
    private AdminService adminService;
    @Test
    public  void contextLoads() {
        Admin admin = adminService.login("zcz");
        System.out.println(admin);
    }
@Test
    public void testQueryAll(){
    final List<Admin> list = adminService.queryAll();
    for (Admin admin : list) {
        System.out.println(admin);
    }
}
    @Test
    public void testQueryOne(){
        final Admin one = adminService.queryOne("1");
        System.out.println(one);
    }
    @Test
    public void testupdata(){
        adminService.update(new Admin("1","zcz","123456","无","1","保密"));
    }
    @Test
    public void testadd(){
        adminService.add(new Admin(null,"www","123456","无","1","保密"));
    }
    @Test
    public void testdelete(){
        adminService.delete("3");
    }


}
