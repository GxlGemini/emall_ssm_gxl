package com.service;

import com.dao.TopsDao;
import com.entity.Tops;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  @author linxiaobai
 * @date 2020
 */
@Service
public class TopsService {
    @Autowired
    TopsDao topsDao;

    /**
     * 根据商品id查询是否为今日推荐   如果为type=1就是今日推荐
     */
    public boolean getGoodsIdToTops(int goodId) {
        Tops tops = topsDao.getGoodIdToTop(goodId);
        if (tops != null && tops.getType() == Tops.TYPE_TODAY) {
            return true;
        }
        return false;
    }

    /**
     * 将商品添加到每日推荐
     */
    public boolean addTops(int type, int goodId) {
        return topsDao.addTops(type, goodId);
    }

    /**
     * 将商品移除每日推荐
     */
    public boolean deleteTops(int type, int goodId) {
        return topsDao.deleteTops(type, goodId);
    }

    public Tops getByGoodIdAndType(int goodId,int type){
        return topsDao.selectByGoodIdAndType(goodId, type);
    }
}
