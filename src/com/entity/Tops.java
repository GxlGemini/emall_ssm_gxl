package com.entity;

/**
 *  @author linxiaobai
 * @date 2020
 */
public class Tops {
    public static final Integer TYPE_TODAY = 1;
    private  Integer id;
    private  Integer  type ;
    private  Integer goodId;
    private  Goods goods;
    private  Types types;
    public Tops() {
        super();
    }
    public Tops(Integer id, Integer type, Integer goodId, Goods goods, Types types) {
        this.id = id;
        this.type = type;
        this.goodId = goodId;
        this.goods = goods;
        this.types = types;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Types getTypes() {
        return types;
    }

    public void setTypes(Types types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "Tops{" +
                "id=" + id +
                ", type=" + type +
                ", goodId=" + goodId +
                ", goods=" + goods +
                ", types=" + types +
                '}';
    }
}
