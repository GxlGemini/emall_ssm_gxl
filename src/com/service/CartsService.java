package com.service;

import com.dao.CartDao;
import com.dao.GoodsDao;
import com.entity.Carts;
import com.entity.Goods;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *  @author linxiaobai
 * @date 2020
 */
@Service
public class CartsService {
    @Autowired
    CartDao cartDao;
    @Autowired
    GoodsService goodsService;


    /**
     * 购物车详情
     */
    public List<Carts> getAllCarts(int userId) {
        return packGoods(cartDao.getAll(userId));
    }

    /**
     * 购物车商品种类数
     */
    public int getCartsCount(int userId) {
//        return cartDao.getCartsCount(userId);
        return cartDao.getSumCarts(userId);
    }

    /**
     * 将商品添加到购物车
     */
    public boolean saveGoodsToCarts(int userId, int goodId) {
        Carts carts = cartDao.selectUserIdAndGoodId(userId, goodId);
        if (Objects.nonNull(carts)) {
            //当存在同一件商品的时候把amount+1
            return cartDao.updateCarts(userId, goodId);
        }
        carts = new Carts();
//        carts.setUserId(userId);
//        carts.setGoodId(goodId);
//        carts.setAmount(1);
        return cartDao.insertCarts(1, goodId, userId);
    }

    /**
     * 在购物车点击添加按钮
     */
    public boolean AddCarts(int id) {
        return cartDao.addCarts(id);
    }

    /**
     * 在购物车点击减少按钮
     */
    public int cartLess(int id) {
        return cartDao.cartLess(id);
    }

    /**
     * 通过id查询购物车的信息
     */
    public Carts getCartsById(int id) {
        return cartDao.getCartsById(id);
    }

    /**
     * 点击删除图标
     */
    public boolean cartDelete(int id) {
        return cartDao.cartDelete(id);
    }

    /**
     * 封装商品
     */
    public Carts packGoods(Carts carts) {
        if (carts != null) {
            carts.setGood(goodsService.getGoodsById(carts.getGoodId()));
            carts.setTotal(carts.getAmount() * carts.getGood().getPrice());
        }
        return carts;
    }

    /**
     * 封装商品
     */
    public List<Carts> packGoods(List<Carts> list) {
        for (Carts carts :
                list) {
            carts = packGoods(carts);
        }
        return list;
    }

    /**
     * 根据用户ID删除购物车中的内容
     */
    public boolean deleteByUserId(int userId) {
        return cartDao.deleteByUserId(userId);
    }

//    /**
//     * 通过用户名id查询购物车的信息
//     */
//    public List<Carts> queryById(int userId) {
//        return packGoods(cartDao.queryCartsToUserId(userId));
//    }
}
