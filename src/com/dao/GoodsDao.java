package com.dao;

import com.entity.Goods;
import com.entity.Tops;
import com.entity.Types;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;


public interface GoodsDao {

    /**
     * 用于封装商品
     */
    @Select("select * from types where id=#{id}")
    Types getTypeById(int typeId);

    //通过id查询商品信息
    @Select("select * from goods where id =#{id}")
    Goods getGoodsById(int id);

    @Select("select * from goods where id=#{id}")
    public Goods select(int id);

    /**
     * 今日推荐
     */
    @Select("select * from goods,tops where tops.good_id=goods.id and type=#{type} order by tops.id desc  limit #{begin},#{end} ")
    List<Goods> getTypeIdToTops(@Param("type") int type, @Param("begin") int begin, @Param("end") int end);

    @Select("select count(*) from tops,goods where tops.good_id=goods.id and type=#{type}")
    int getTypeToTopCount(int type);


    /**
     * 热销排行带分页
     */
    @Select("select * from goods order by sales desc limit #{begin} , #{size}")
    List<Goods> getAllHot(@Param("begin") int begin, @Param("size") int size);

    @Select("select count(*) from goods order by sales desc")
    Integer getHotCount();


    /**
     * 根据类别查询商品
     */
    @Select("select * from goods WHERE type_id= #{typeId} order by id desc limit #{begin},#{size}")
    List<Goods> getTypesGoods(@Param("typeId") int id, @Param("begin") int begin, @Param("size") int size);

    //新品上市
    @Select("select * from goods order by id desc  limit #{begin},#{end}")
    List<Goods> getList(@Param("begin") int begin, @Param("end") int end);

    @Select("select count(*) from goods")
    Integer getGoodsCount();


    /**
     * 通过typeId 查结果总数
     */
    @Select("select count(*) from goods where  type_id = #{typeId} ")
    Integer getTypeIdToGoodsCount(@Param("typeId") int typeId);

    /**
     * 增加销量
     */
    @Update("update goods set sales = sales+#{sales} where id=#{id}")
    boolean updateSales(@Param("sales") int sales, @Param("id") int id);

    /**
     * 减少库存
     */
    @Update("update goods set stock  = stock-#{stock} where id =#{id}")
    boolean updateStock(@Param("stock") int stock, @Param("id") int id);

    /**
     * 查询所有商品（带分页）
     */
    @Select("select * from goods order by id desc limit #{begin},#{size}")
    List<Goods> getAllGoods(@Param("begin") int begin, @Param("size") int size);


    /**
     * 添加商品
     */
    @Insert("insert  goods(cover,name,intro,spec,price,stock,sales,content,type_id) " +
            "values(#{cover},#{name},#{intro},#{spec},#{price},#{stock},0,#{content},#{typeId})")
    @SelectKey(keyProperty = "id", statement = "SELECT LAST_INSERT_ID()", before = false, resultType = Integer.class)
    boolean addGoods(Goods goods);

    /**
     * 通过id修改商品信息
     */
    @Update("update goods set cover=#{cover},name=#{name},intro=#{intro},spec=#{spec},price=#{price},stock=#{stock}," +
            "content=#{content},type_id=#{typeId} where id= #{id}")
    boolean updateGood(Goods goods);

    /**
     * 删除商品
     */
    @Delete("delete from goods where id =#{id}")
    boolean deleteGood(@Param("id") int id);
}
