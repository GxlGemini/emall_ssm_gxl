package com.dao;

import com.entity.Carts;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CartDao {
    /**
     * 用户id查购物车信息
     *
     * @param userId
     * @return
     */
    @Select("select * from carts where user_id=#{userId}")
    List<Carts> getAll(int userId);

    @Select("select count(*) from carts where user_id=#{userId}")
    Integer getCartsCount(int userId);

    /**
     * 求和amount   购物车右上角的数字
     */
    @Select("select  IFNULL(SUM(amount),0) from carts where user_id = #{userId}")
    Integer getSumCarts(int userId);

    /**
     * 将商品添加到购物车
     */
    @Insert("insert into carts(amount,good_id,user_id) values(#{amount},#{goodId},#{userId})")
    boolean insertCarts(@Param("amount") int mount, @Param("goodId") int goodId, @Param("userId") int userId);

    //购物车是否存在同一个用户同一件商品
    @Select("select * from carts where user_id=#{userId} AND good_id=#{goodId} limit 0,1")
    Carts selectUserIdAndGoodId(@Param("userId") int userId, @Param("goodId") int goodId);

    //更新购物车
    @Update("update carts set amount=(amount+1) where user_id = #{userId} and good_id=#{goodId}")
    boolean updateCarts(@Param("userId") int userId, @Param("goodId") int goodId);

    //    通过购物车cartsId在购物车添加商品
    @Update("update carts  set amount =(amount+1) where id = #{id}")
    boolean addCarts(@Param("id") int id);

    //    通过购物车cartsId在购物车减少商品
    @Update("update carts  set amount =(amount-1) where id = #{id}")
    Integer cartLess(@Param("id") int id);

    // 点击删除图标删除购物车的商品信息(通过购物车的id)
    @Delete("delete from carts where id =#{id}")
    boolean cartDelete(@Param("id") int id);

    //通过购物车id查找购物车信息
    @Select("select * from carts where id=#{id}")
    Carts getCartsById(int id);

    //根据用户名来删除购物车中的信息
    @Delete("delete from carts where  user_id = #{userId}")
    boolean deleteByUserId(int userId);
    //    //获取用户名购物车的内容
//    @Select("select * from carts where user_id=#{userId}")
////    @Results({
////            @Result(id = true,property = "id",column = "id"),
////            @Result(property = "amount",column = "amount"),
////            @Result(property = "goodId",column = "good_id"),
////            @Result(property = "userId",column = "user_id")
////    })
//    List<Carts> queryCartsToUserId(@Param("userId") int userId);

}
