package com.dao;

import com.entity.Types;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TypesDao {
    /**
     * 商品分类DAO
     * getType：通过num排序查找所有的商品类型
     * getByIdType：通过typeId查找商品所属类型
     * @return
     */
    @Select("select * from types order by num ")
    List<Types> getType();

    @Select("select * from types where id =#{id}")
    Types getByIdType(int id);

    //添加类目
    @Insert("insert types(name,num) values(#{name},#{num})")
    boolean insertType(Types types);

    //修改类目
    @Update("update types set name =#{name},num =#{num} where id =#{id}")
    boolean updateType(Types types);

    //删除类目
    @Delete("delete from  types where id =#{id}")
    boolean deleteType(@Param("id")int id);

    //通过类型找ID
    @Select("select * from types where name=#{name}")
    Types getByName(String name);
}
