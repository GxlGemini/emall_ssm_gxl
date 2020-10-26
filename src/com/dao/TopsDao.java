package com.dao;

import com.entity.Tops;
import org.apache.ibatis.annotations.*;


public interface TopsDao {
    /**
     * 通过商品id查询是否是今日推荐
     *
     * @param goodId
     * @return
     */
    @Select("select * from  tops where good_id =#{goodId}")
    Tops getGoodIdToTop(int goodId);

    /**
     * 将商品添加到今日推荐
     */
    @Insert("insert tops(type,good_id) values(#{type},#{goodId})")
    @SelectKey(keyProperty = "id", statement = "SELECT LAST_INSERT_ID()", before = false, resultType = Integer.class)
    boolean addTops(@Param("type") int type, @Param("goodId") int goodId);

    /**
     * 移除今日推荐
     */
    @Delete("delete from tops  where type=#{type} and good_id=#{goodId}")
    boolean deleteTops(@Param("type") int type, @Param("goodId") int goodId);

    @Delete("delete from tops  where  good_id=#{goodId}")
    boolean deleteTop(@Param("goodId") int goodId);

    @Select("select * from tops where good_id=#{goodId} and type=#{type}")
    public Tops selectByGoodIdAndType(@Param("goodId") int goodId, @Param("type") int type);
}
