package com.entity;

/**
 *  @author linxiaobai
 * @date 2020
 */
public class Carts {
    private Integer id;
    private Integer amount;
    private Integer goodId;
    private Integer userId;
    private Integer total;
    private Goods good;

    public Carts(Integer id, Integer amount, Integer goodId, Integer userId, Integer total, Goods good) {
        this.id = id;
        this.amount = amount;
        this.goodId = goodId;
        this.userId = userId;
        this.total = total;
        this.good = good;
    }

    public Carts() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Goods getGood() {
        return good;
    }

    public void setGood(Goods good) {
        this.good = good;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Carts{" +
                "id=" + id +
                ", amount=" + amount +
                ", goodId=" + goodId +
                ", userId=" + userId +
                ", total=" + total +
                ", good=" + good +
                '}';
    }
}
