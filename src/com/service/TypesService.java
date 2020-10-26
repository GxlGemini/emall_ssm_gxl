package com.service;

import com.dao.TypesDao;
import com.entity.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  @author linxiaobai
 * @date 2020
 */
@Service
public class TypesService {
    @Autowired
    TypesDao typesDao;
    /**
     * 商品分类
     */
    public List<Types> getAllTypes() {
        return typesDao.getType();
    }

    /**
     * 根据id查分类
     */
    public Types getByIdTypes(int id){
        return typesDao.getByIdType(id);
    }

    /**
     * 添加类目
     */
    public boolean insertTypes(Types types){
        return typesDao.insertType(types);
    }
    /**
     * 修改类目
     */
    public boolean updateType(Types types){
        return typesDao.updateType(types);
    }
    /**
     * 删除类目
     */
    public boolean deleteType(int id){
        return typesDao.deleteType(id);
    }

    /**
     * 通过类目名称找id
     */
    public Types getByName(String name){
        return typesDao.getByName(name);
    }
}
