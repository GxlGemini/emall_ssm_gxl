package com.service;

import com.dao.GoodsDao;
import com.dao.TopsDao;
import com.entity.Goods;
import com.entity.Tops;
import com.entity.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 *  @author linxiaobai
 * @date 2020
 */
@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    TopsService topsService;
    @Autowired
    TopsDao topsDao;
    @Autowired
    TypesService typesService;
    /**
     * 新品上市
     *
     * @param page
     * @param size
     * @return
     */

    public List<Goods> getList(int page, int size) {
        return goodsDao.getList((page - 1) * size, size);
    }

    public int getListCount() {
        return goodsDao.getGoodsCount();
    }

    /**
     * 根据产品分类查商品
     */
    public List<Goods> getTypesGoods(int typeId, int page, int size) {
        return typeId > 0 ? packGoods(goodsDao.getTypesGoods(typeId, (page - 1) * size, size)) : this.getList(page, size);
    }

    /**
     * 根据今日推荐查商品
     */
    public List<Goods> getTypesToTopsGoods(int type, int page, int size) {
        return packGoods(goodsDao.getTypeIdToTops(type, (page - 1) * size, size));
    }

    public int getCountTops(int type) {
        return goodsDao.getTypeToTopCount(type);
    }

    /***
     * 热销排行
     */
    public List<Goods> getAllHot(int page, int size) {
        return packGoods(goodsDao.getAllHot((page - 1) * size, size));
    }

    public int getAllHotCount() {
        return goodsDao.getHotCount();
    }

    /**
     * 封装商品
     */
    public Goods packGoods(Goods goods) {
        if (goods != null) {
            goods.setTypes(goodsDao.getTypeById(goods.getTypeId()));
            goods.setTop(topsService.getGoodsIdToTops(goods.getId()));
        }
        return goods;
    }

    /**
     * 封装商品
     */
    public List<Goods> packGoods(List<Goods> list) {
        for (Goods goods :
                list) {
            goods = packGoods(goods);
//            System.out.println("GoodsService层76行："+goods);
        }
        return list;
    }

    /**
     * 通过typeId 查结果总数
     */
    public Integer getTypeIdToGoodsCount(int typeId) {
        return typeId > 0 ? goodsDao.getTypeIdToGoodsCount(typeId) : 0;
    }

    /**
     * 通过商品id查询商品信息
     */
    public Goods getGoodsById(int id) {
        return packGoods(goodsDao.getGoodsById(id));
    }

    /**
     * 查询所有的商品带分页
     */
    public List<Goods> getAllGoods(int page, int size) {
        return packGoods(goodsDao.getAllGoods((page - 1) * size, size));
    }

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    public boolean addGood(Goods goods) {
        return goodsDao.addGoods(goods);
    }

    /**
     * 删除商品
     */
    @Transactional
    public boolean deleteGood(int id){
        //先删除今日推荐中的这个商品信息
        topsDao.deleteTop(id);
        return goodsDao.deleteGood(id);
    }
    /**
     * 通过id修改商品
     */
    public boolean updateGood(Goods goods){
        return goodsDao.updateGood(goods);
    }

    public Goods get(int id) {
        return packGood(goodsDao.select(id));
    }
    private Goods packGood(Goods good) {
        if (good != null) {
            good.setTypes(typesService.getByIdTypes(good.getTypeId()));
            good.setTop(Objects.nonNull(topsService.getByGoodIdAndType(good.getId(), Tops.TYPE_TODAY)));
        }
        return good;
    }
}
