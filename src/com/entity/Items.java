package com.entity;

import java.util.List;

/**
 *  @author linxiaobai
 * @date 2020
 */
public class Items {
    private Integer  id ;
    private Integer price ;
    private Integer amount;
    private Integer orderId;
    private Integer goodId;
    private Goods goods;
    public Items() {
        super();
    }

    public Items(Integer id, Integer price, Integer amount, Integer orderId, Integer goodId, Goods goods) {
        this.id = id;
        this.price = price;
        this.amount = amount;
        this.orderId = orderId;
        this.goodId = goodId;
        this.goods = goods;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", price=" + price +
                ", amount=" + amount +
                ", orderId=" + orderId +
                ", goodId=" + goodId +
                ", goods=" + goods +
                '}';
    }
}
