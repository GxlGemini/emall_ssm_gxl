package com.dao;

import com.entity.User;
import org.apache.ibatis.annotations.*;

import javax.swing.*;
import java.util.List;

public interface UserDao {
    /**
     * 添加用户
     */
    @Insert("insert users(username,password,name,phone,address) values(#{username},#{password},#{name},#{phone},#{address});")
    @SelectKey(keyProperty = "id", statement = "SELECT LAST_INSERT_ID()", before = false, resultType = Integer.class)
    boolean add(User user);

    /**
     * 登录验证
     */
    @Select("select * from users")
    List<User> queryLogin();

    /**
     * 验证是否存在相同的用户名
     */
    @Select("select * from users where username =#{username}")
    User queryUserByName(String username);

    /**
     * 修改收货地址
     */
    @Update("update users set name=#{name},phone=#{phone},address=#{address} where id = #{id}")
    boolean updateAddress(User user);


    /**
     * 查询当前用户的密码
     */
    @Select("select * from users where id =#{id}")
    User queryPassword(int id);

    /**
     * 修改密码
     */
    @Update("update users set password = #{password} where id =#{id}")
    boolean updatePassword(User user);

    /**
     * 查询所有用户 （分页）
     */
    @Select("select * from  users limit #{begin},#{end}")
    List<User> queryAllUser(@Param("begin") int begin, @Param("end") int end);

    /**
     * 查询所有用户数
     */
    @Select("select count(*) from users")
    Integer queryAllCount();

    /**
     * 删除用户
     */
    @Delete("delete from users where id =#{id}")
    boolean deleteUser(int id);

    @Select("select * from users where id=#{id}")
    public User select(int id);
}
