package com.baizhi.aspect;

import com.baizhi.entity.Admin;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import java.util.Date;

@org.aspectj.lang.annotation.Aspect
@Configuration//交给spring工厂管理
public class Aspect {
    @Autowired
    HttpSession session;
   // @Around("@(com.baizhi.controller.AdminController)")
    public Object addlog(ProceedingJoinPoint proceedingJoinPoint){
       Admin admin= (Admin) session.getAttribute("admin");
       //获取管理员名
        String username = admin.getUsername();
        //获取当前时间
        Date date = new Date();
        //获取操作的方法名
         String methodname = proceedingJoinPoint.getSignature().getName();
    return null;
    }
}
