package com.dao;

import com.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 管理员持久层
 */

public interface AdminDao {
    /**
     * 查找管理员的信息
     */
    @Select("select * from  admins")
    List<Admin> queryAll();


    /**
     * 查找所有管理员的信息（分页）
     *
     * @param begin
     * @param end
     * @return
     */
    @Select("select * from  admins limit #{begin},#{end}")
    List<Admin> queryAllAdmin(@Param("begin") int begin, @Param("end") int end);

    /**
     * 查找所有管理员总数
     */
    @Select("select count(*) from admins")
    Integer getAllAdminCount();

    /**
     * 添加管理员
     */
    @Insert("insert admins(username,password) values(#{username},#{password}) ")
    @SelectKey(keyProperty = "id", statement = "SELECT LAST_INSERT_ID()", before = false, resultType = Integer.class)
    boolean addAdmin(Admin admin);

    /**
     * 通过id查询 管理员信息
     */
    @Select("select * from admins where id = #{id}")
    Admin getAdminById(int id);

    /**
     * 管理员重置密码
     */
    @Update("update admins set password =#{password} where id =#{id}")
    boolean updatePwd(Admin admin);

    /**
     * 删除管理员
     */
    @Delete("delete from  admins where id =#{id}")
    boolean deleteAdmin(int id);
}
