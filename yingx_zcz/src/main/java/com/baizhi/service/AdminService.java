package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.List;

public interface AdminService {
    Admin login(String username);
    List<Admin> queryAll();
    Admin queryOne(String id);
    void delete(String id);
    void update(Admin admin);
    void add(Admin admin);
}
