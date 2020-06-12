package com.bula.ms.service;

import com.bula.ms.dao.OrderInfoDao;
import com.bula.ms.entity.MsOrder;
import com.bula.ms.entity.OrderInfo;
import com.bula.ms.entity.User;
import com.bula.ms.redis.OrderKey;
import com.bula.ms.redis.RedisService;
import com.bula.ms.vo.GoodsVo;
import com.bula.ms.vo.OrderInfoVo;
import com.sun.tools.corba.se.idl.constExpr.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderService {
    @Resource
    OrderInfoDao orderDao;
    @Autowired
    RedisService redisService;

    /**
     * 通过用户id和商品id获取订单
     *
     * @param userId
     * @param orderId
     * @return
     */
    public OrderInfo getOrderByUserIdGoodsId(long userId, long orderId) {
        OrderInfo orderInfo = redisService.get(OrderKey.getOrderInfo, userId + "_" + orderId, OrderInfo.class);
        if (orderInfo == null) {
            orderInfo = orderDao.getOrderByUserIdGoodsId(userId, orderId);
            redisService.set(OrderKey.getOrderInfo, userId + "_" + orderId, orderInfo);
        }
        return orderInfo;
    }


    //判断用户有没有秒杀到商品
    public MsOrder getMiaoshaOrderByUserIdGoodsId(Long userId, long goodsId) {
        return redisService.get(OrderKey.getMsOrderByUserId, userId + "_" + goodsId, MsOrder.class);
    }

    public OrderInfo createOrder(User user, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId((long) 0);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getMsPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderDao.insert(orderInfo);
        MsOrder msOrder = new MsOrder();
        msOrder.setGoodsId(goodsVo.getId());
        msOrder.setOrderId(orderInfo.getId());
        msOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(msOrder);
        redisService.set(OrderKey.getMsOrderByUserId, user.getId() + "_" + orderInfo.getGoodsId(), msOrder);
        return orderInfo;
    }
}
