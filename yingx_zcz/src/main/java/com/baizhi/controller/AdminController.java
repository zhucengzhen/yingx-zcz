package com.baizhi.controller;
import com.baizhi.entity.Admin;
import com.baizhi.entity.User;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    @RequestMapping("login")
    @ResponseBody
    public Map<String, Object> login(String username, String code, HttpSession session, String password, HttpServletResponse response ) {

        Admin admin = adminService.login(username);
        String rancomCode = (String) session.getAttribute("rancomCode");
        HashMap<String, Object> map = new HashMap<>();
        if(code.equals(rancomCode)==false){
            map.put("message","验证码错误");
            map.put("status", 104);
        }
        else if (admin == null) {
                map.put("message", "用户名不存在");
                map.put("status", 104);
        } else if(password.equals(admin.getPassword())==false){
            //登录失败
            map.put("message", "密码错误");
            map.put("status", 104);

        } else{
            map.put("message","登录成功");
            map.put("status",100);
            session.setAttribute("admin",admin);
        }
        return map;

}

//查所有
    @ResponseBody
    @RequestMapping("queryAll")
    public List<Admin> queryAll(){
        List<Admin> admins = adminService.queryAll();
        return admins;
    }


    @ResponseBody
    @RequestMapping("edit")
    public void edit(Admin admin, String oper){

        if(oper.equals("add")){
            adminService.add(admin);
        }

        if(oper.equals("edit")){
            adminService.update(admin);
        }

        if(oper.equals("del")){
            adminService.delete(admin.getId());
        }
    }

    @RequestMapping("yzm")
    public void getImageCode(HttpServletResponse response, HttpSession session){
        //1.获取验证码随机字符

        String rancomCode = ImageCodeUtil.getSecurityCode();
        session.setAttribute("rancomCode",rancomCode);
        System.out.println("验证码随机数: "+rancomCode);

        //2.将验证码随机字符做成验证码图片
        BufferedImage image = ImageCodeUtil.createImage(rancomCode);
        response.setCharacterEncoding("utf-8");
        //3.将验证码以以流的形式响应到前台
        try {
            //参数:验证码图片,图片的格式,响应流
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
