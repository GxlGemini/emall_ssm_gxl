package com.dao;

import com.entity.Order;
import com.entity.User;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.scene.chart.PieChart;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Id;
import java.util.Date;
import java.sql.Time;
import java.util.List;

/**
 * 订单持久层
 */

public interface OrderDao {
//    @Select("SELECT * from orders where user_id =#{userId}")
//    List<Order> getOrder(int id);

    /**
     * 我的订单
     *
     * @param id
     * @return
     */
    @Select("select * from orders where user_id=#{id} limit #{begin},#{end}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "total", column = "total"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "status", column = "status"),
            @Result(property = "paytype", column = "paytype"),
            @Result(property = "name", column = "name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "address", column = "address"),
            @Result(property = "systime", column = "systime"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "itemList", column = "id", many = @Many(select = "com.dao.ItemsDao.queryItemsAndGoods"))
    })
    List<Order> queryAllMyOrder(@Param("id") int id, @Param("begin") int begin, @Param("end") int end);

    /**
     * 订单总记录数
     */
    @Select("select count(*) from orders where user_id=#{id}")
    int getCountMyOrder(@Param("id") int id);

    /**
     * 将购物车内容添加到订单表
     */
    @Insert("insert orders(total,amount,status,name,phone,address,systime,user_id)" +
            "values (#{total},#{amount},#{status},#{name},#{phone},#{address},now(),#{userId})")
    @SelectKey(keyProperty = "id", statement = "SELECT LAST_INSERT_ID()", before = false, resultType = Integer.class)
    Integer addCartsToOrder(Order order);


    /**
     * 通过id获取订单信息
     */
    @Select("select * from orders where id =#{id}")
    Order getOrder(@Param("id") int id);


    /**
     * 查看所有的订单（后台管理）
     *
     * @param begin
     * @param end
     * @return
     */
    @Select("select * from orders limit #{begin},#{end}")
//    @SelectProvider(type = Provider.class,method = "selectOrder")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "total", column = "total"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "status", column = "status"),
            @Result(property = "paytype", column = "paytype"),
            @Result(property = "name", column = "name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "address", column = "address"),
            @Result(property = "systime", column = "systime"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "itemList", column = "id", many = @Many(select = "com.dao.ItemsDao.queryItemsAndGoods")),
            @Result(property = "user", column = "user_id", one = @One(select = "com.dao.UserDao.queryPassword"))
    })
    List<Order> queryAllOrder(@Param("begin") int begin, @Param("end") int end);

    /**
     * 查看所有的订单（后台管理）
     *
     * @param begin
     * @param end
     * @return
     */
    @Select("select * from orders where status=#{status} limit #{begin},#{end} ")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "total", column = "total"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "status", column = "status"),
            @Result(property = "paytype", column = "paytype"),
            @Result(property = "name", column = "name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "address", column = "address"),
            @Result(property = "systime", column = "systime"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "itemList", column = "id", many = @Many(select = "com.dao.ItemsDao.queryItemsAndGoods")),
            @Result(property = "user", column = "user_id", one = @One(select = "com.dao.UserDao.queryPassword"))
    })
    List<Order> queryAllOrder1(@Param("status") int status, @Param("begin") int begin, @Param("end") int end);


    /**
     * 所有订单count数
     */
    @Select("select count(*) from orders")
    Integer getOrderCount();

    /**
     * 订单条件查询count数
     */
    @Select("select count(*) from orders where status =#{status}")
    Integer getOrderCount1(@Param("status") int status);

    /**
     * 订单修改（真正支付完成）
     * @param order
     * @return
     */
    @Update("update orders set status=#{status},paytype=#{paytype} ,name=#{name} , phone=#{phone},address=#{address} where id=#{id}")
    public boolean update(Order order);
    /**
     * 修改状态   发货  完成
     */
    @Update("update orders set status=#{status}+1 where id = #{id}")
    boolean updateStatus(@Param("status") int status, @Param("id") int id);

    /**
     * 订单删除
     */
    @Delete("delete from orders where id = #{id}  and  status =#{status} ")
    boolean deleteOrder(@Param("id") int id, @Param("status") int status);
}
