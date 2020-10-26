package com.service;

import com.dao.UserDao;
import com.entity.User;
import com.utils.SafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  @author linxiaobai
 * @date 2020
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    /**
     * 注册用户
     */
    public boolean add(User user) {
        user.setPassword(SafeUtil.encode(user.getPassword()));
        return userDao.add(user);
    }

    /**
     * 查询此用户名是否已经被注册
     */
    public User queryUserByName(String username) {
        return userDao.queryUserByName(username);
    }


    /**
     * 登录验证
     */
    public boolean queryLogin(User user) {
        boolean type = false;
        List<User> list = userDao.queryLogin();
        for (int i = 0; i < list.size(); i++) {
            //先查找用户名是否存在
            if (list.get(i).getUsername().trim().equals(user.getUsername().trim())) {
                //如果用户名存在再判断密码是否正确
                if (list.get(i).getPassword().trim().equals(SafeUtil.encode(user.getPassword().trim()))) {
                    type = true;
                }
            }
        }
        return type;
    }

    /**
     * 修改收货地址
     */
    public boolean updateAddress(User user) {
        return userDao.updateAddress(user);
    }

    /**
     * 查询当前用户的密码
     */
    public User queryPassword(int id) {
        return userDao.queryPassword(id);
    }

    /**
     * 修改用户密码
     */
    public boolean updatePassword(User user) {
        return userDao.updatePassword(user);
    }


    //后台

    /**
     * 查询所有用户
     */
    public List<User> queryAllUser(int page, int size) {
        return userDao.queryAllUser((page - 1) * size, size);
    }
    /**
     * 查询所有用户数
     */
    public Integer queryAllCount(){
        return userDao.queryAllCount();
    }

    /**
     * 删除用户
     */
    public boolean  deleteUser(int id){
        return userDao.deleteUser(id);
    }

    public User get(int id) {
        return userDao.select(id);
    }
}
