package com.baizhi.serviceimpl;

import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


@Service(value = "adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDAO adminDAO;
    @Override
    public Admin login(String username) {
         Admin admin = adminDAO.login(username);
        return admin;
    }

    @Override
    public List<Admin> queryAll() {
        List<Admin> admins = adminDAO.queryAll();
        return admins;
    }

    @Override
    public Admin queryOne(String id) {
        Admin queryOne = adminDAO.queryOne(id);
        return queryOne;
    }

    @Override
    public void delete(String id) {
    adminDAO.delete(id);
    }

    @Override
    public void update(Admin admin) {
       adminDAO.update(admin);


    }

    @Override
    public void add(Admin admin) {
         admin.setId(UUID.randomUUID().toString());
         admin.setLevel("1");
         admin.setSalt("保密");

        adminDAO.add(admin);
    }
}
