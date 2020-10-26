package com.dao;

import com.entity.Items;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ItemsDao {
    /**
     * 我的订单
     * @param orderId
     * @return
     */
    @Select("select * from  items where order_id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "price",column = "price"),
            @Result(property = "amount",column = "amount"),
            @Result(property = "orderId",column = "order_id"),
            @Result(property = "goodId",column = "good_id"),
            @Result(property = "goods",column = "good_id", one = @One(select = "com.dao.GoodsDao.getGoodsById"))
    })
    List<Items> queryItemsAndGoods(@Param("id") int orderId);

    /**
     * 添加
     */
    @Insert("INSERT items(price,amount,order_id,good_id) VALUES(#{price},#{amount},#{orderId},#{goodId})")
    @SelectKey(keyProperty = "id",statement = "SELECT LAST_INSERT_ID()",before = false,resultType = Integer.class)
    boolean addCartsToItems(@Param("price") int price,@Param("amount") int amount,@Param("orderId") int orderId,@Param("goodId") int goodId);
}
