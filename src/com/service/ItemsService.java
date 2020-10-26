package com.service;

import com.dao.ItemsDao;
import com.entity.Items;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  @author linxiaobai
 * @date 2020
 */
@Service
public class ItemsService {
    @Autowired
    ItemsDao itemsDao;
    public List<Items> queryItemsAndGoods(int orderId){
        return itemsDao.queryItemsAndGoods(orderId);
    };
}
