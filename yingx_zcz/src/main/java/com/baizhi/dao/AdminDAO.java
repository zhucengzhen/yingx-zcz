package com.baizhi.dao;

import com.baizhi.entity.Admin;

import java.util.List;

public interface AdminDAO {
    Admin login(String username);
    List<Admin> queryAll();
    Admin queryOne(String id);
    void delete(String id);
    void update(Admin admin);
    void add(Admin admin);
}
