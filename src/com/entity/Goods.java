package com.entity;


/**
 *  @author linxiaobai
 * @date 2020
 */
public class Goods {
    private Integer id;
    private String cover;
    private String name;
    private String intro;
    private String spec;
    private Integer price;
    private Integer stock;
    private Integer sales;
    private String content;
    private Integer typeId;
    private Types  types;
    private boolean top;

    public Goods(Integer id, String cover, String name, String intro, String spec, Integer price, Integer stock, Integer sales, String content, Integer typeId, Types types, boolean top) {
        this.id = id;
        this.cover = cover;
        this.name = name;
        this.intro = intro;
        this.spec = spec;
        this.price = price;
        this.stock = stock;
        this.sales = sales;
        this.content = content;
        this.typeId = typeId;
        this.types = types;
        this.top = top;
    }

    public Goods() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Types getTypes() {
        return types;
    }

    public void setTypes(Types types) {
        this.types = types;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", cover='" + cover + '\'' +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", spec='" + spec + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", sales=" + sales +
                ", content='" + content + '\'' +
                ", typeId=" + typeId +
                ", types=" + types +
                ", top=" + top +
                '}';
    }
}
