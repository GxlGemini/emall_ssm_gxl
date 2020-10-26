package com.entity;

import java.util.List;

/**
 *  @author linxiaobai
 * @date 2020
 */
public class Types {
    private Integer id;
    private String name;
    private Integer num;

    public Types(Integer id, String name, Integer num) {
        this.id = id;
        this.name = name;
        this.num = num;
    }

    public Types() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Types{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
