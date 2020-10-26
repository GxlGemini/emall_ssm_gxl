package com.dao;

import com.entity.MyDept;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 部门信息
 */
public interface MyDeptDao {
//    @Select("SELECT * from mydept")
    List<MyDept> queryAll();
}
