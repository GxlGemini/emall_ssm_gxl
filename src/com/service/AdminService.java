package com.service;

import com.dao.AdminDao;
import com.entity.Admin;
import com.entity.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  @author linxiaobai
 * @date 2020
 */
@Service
public class AdminService {
    @Autowired
    AdminDao adminDao;

    /**
     * 查找所有管理员信息
     */
    public List<Admin> queryAll() {
        return adminDao.queryAll();
    }

    /**
     * 查找所有管理员信息（分页）
     */
    public List<Admin> queryAllAdmin(int page, int size) {
        return adminDao.queryAllAdmin((page - 1) * size, size);
    }

    /**
     * 查找所有管理员的总数
     */
    public int getAllAdminCount() {
        return adminDao.getAllAdminCount();
    }

    /**
     * 添加管理员
     */
    public boolean addAdmin(Admin admin) {
        return adminDao.addAdmin(admin);
    }

    /**
     * 通过id来查询管理员信息
     */
    public Admin getAdminById(int id) {
        return adminDao.getAdminById(id);
    }

    /**
     * 管理员的重置密码
     */
    public boolean updatePwd(Admin admin) {
        return adminDao.updatePwd(admin);
    }
    /**
     * 删除管理员
     */
    public boolean deleteAdmin(int id){
        return adminDao.deleteAdmin(id);
    }
}
