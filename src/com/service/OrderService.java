package com.service;

import com.config.ExceptionConfig;
import com.dao.*;
import com.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *  @author linxiaobai
 * @date 2020
 */
@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    CartDao cartDao;
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ItemsDao itemsDao;
    @Autowired
    CartsService cartsService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    UserService userService;

    /**
     * 我的订单信息
     *
     * @return
     */
    public List<Order> getMyOrder(int id, int page, int end) {
        return orderDao.queryAllMyOrder(id, (page - 1) * end, end);
    }
    private List<Order> pack(List<Order> list) {
        if (Objects.nonNull(list) && !list.isEmpty()) {
            for (Order orders : list) {
                orders = pack(orders);
            }
        }
        return list;
    }
    private Order pack(Order orders) {
        if (Objects.nonNull(orders)) {
            List<Items> itemsList = itemsDao.queryItemsAndGoods(orders.getId());
            for (Items items : itemsList) {
                items.setGoods(goodsService.get(items.getGoodId()));
            }
            orders.setItemList(itemsList);
            orders.setUser(userService.get(orders.getUserId()));
        }
        return orders;
    }

    /**
     * 记录总数
     */
    public int getCountOrder(int id) {
        return orderDao.getCountMyOrder(id);
    }

    /**
     * 将购物车的信息添加到order表
     */
    @Transactional
    public int CartsToOrder(int userId) throws ExceptionConfig.MyException {
        //要先判断购物车中购买的商品数量是否大于库存数量
        List<Carts> carts = cartDao.getAll(userId);
        if (Objects.isNull(carts) || carts.isEmpty()) {
            throw new ExceptionConfig.MyException("购物车没有商品");
        }
        int total = 0;//1
        for (Carts carts1 : carts) {
            int goodId = carts1.getGoodId();
            Goods goods = goodsDao.getGoodsById(goodId);
            if (carts1.getAmount() > goods.getStock()) {
                throw new ExceptionConfig.MyException("商品[" + goods.getName() + "]库存不足");
            }
            //计算订单金额
            total += goods.getPrice() * carts1.getAmount();//1
            //加销量
            goodsDao.updateSales(carts1.getAmount(), goodId);
            //减库存
            goodsDao.updateStock(carts1.getAmount(), goodId);
        }
        User user = userDao.queryPassword(userId);//获取到这个用户
        String name = user.getName();//1
        String phone = user.getPhone();//1
        String address = user.getAddress();//1
        int status = Order.STATUS_UNPAY;//1   需要改善  这表示没有付款  (1 未付款/2 已付款/3 已发货/4 已完成)
//        int payType = 1;
        //将购物车的信息添加到订单表order
        Order order = new Order();
        order.setTotal(total);
        order.setAmount(carts.size());//获取购买的商品总数
        order.setStatus(status);
//        order.setPaytype(payType);//需完善 这里默认先设置为1.微信  2.支付宝  3. 余额支付
        order.setName(name);
        order.setPhone(phone);
        order.setSystime(new Date());
        order.setAddress(address);
        order.setUserId(userId);
        //将购物车信息添加到orders表中
        orderDao.addCartsToOrder(order);
        int orderId = order.getId();
        //将购物车的信息添加到items表
        for (Carts carts2 : carts) {
            int goodId = carts2.getGoodId();
            Goods goods = goodsDao.getGoodsById(goodId);
            //购买时价格
            int price = goods.getPrice();
            //购买数量
            int amount = carts2.getAmount();
            itemsDao.addCartsToItems(price, amount, orderId, goodId);
        }
        //清空购物车
        cartsService.deleteByUserId(userId);
        return orderId;
    }

    /**
     * 通过id获取order
     */
    public Order getOrder(int id) {
        return packOrder(orderDao.getOrder(id));
    }

    /**
     * 封装订单
     *
     * @param order
     * @return
     */
    public Order packOrder(Order order) {
        List<Items> itemsList = itemsDao.queryItemsAndGoods(order.getId());
        order.setItemList(itemsList);
        return order;
    }

    /**
     * 查看所有订单（后台管理）
     */
    public List<Order> getAllOrder(int page, int size) {
        return orderDao.queryAllOrder((page - 1) * size, size);
    }

    /**
     * status带参数
     *
     * @param status
     * @param page
     * @param size
     * @return
     */
    public List<Order> getAllOrder1(int status, int page, int size) {
        return orderDao.queryAllOrder1(status, (page - 1) * size, size);
    }

    /**
     * 查看所有的订单总数
     */
    public int getOrderCount() {
        return orderDao.getOrderCount();
    }

    /**
     * 按条件查看所有的订单总数
     */
    public int getOrderCount(int status) {
        return orderDao.getOrderCount1(status);
    }

    /**
     * 修改订单状态  发货  完成
     */
    public boolean updateStatus(int status, int id) {
        return orderDao.updateStatus(status, id);
    }

    /**
     * 删除订单
     */
    public boolean deleteOrder(int id, int status) {
        return orderDao.deleteOrder(id, status);
    }

    /**
     * 订单支付
     *
     * @param order
     */
    public void pay(Order order) {
        Order old = orderDao.getOrder(order.getId());
        old.setStatus(Order.STATUS_PAYED);
        old.setPaytype(order.getPaytype());
        old.setName(order.getName());
        old.setPhone(order.getPhone());
        old.setAddress(order.getAddress());
        orderDao.update(old);
    }
}
