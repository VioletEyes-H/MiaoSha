package com.bula.ms.service;

import com.bula.ms.dao.GoodsDao;
import com.bula.ms.entity.Goods;
import com.bula.ms.entity.MsGoods;
import com.bula.ms.redis.GoodsKey;
import com.bula.ms.redis.RedisService;
import com.bula.ms.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService {

    @Resource
    private GoodsDao goodsDao;

    @Autowired
    private RedisService redisService;

    /**
     * 获取商品列表数据
     *
     * @return
     */
    public List<GoodsVo> getGoodsVoList() {
        List<GoodsVo> goodsVoList = new ArrayList<>();
        goodsVoList = (List<GoodsVo>) redisService.get(GoodsKey.getGoodsList, "", GoodsVo.class);
        if (goodsVoList == null) {
            goodsVoList = goodsDao.getGoodsVoList();
            redisService.set(GoodsKey.getGoodsList, "", goodsVoList);
        }
        return goodsVoList;
    }

    /**
     * 通过商品id获取商品数据
     *
     * @param goodsId
     * @return
     */
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    /**
     * 减库存
     *
     * @param goodsVo
     */
    public boolean reduceStock(GoodsVo goodsVo) {
        return goodsDao.reduceStock(goodsVo.getId()) > 0;
    }
}
