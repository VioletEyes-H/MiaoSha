package com.bula.ms.controller;

import com.bula.ms.entity.OrderInfo;
import com.bula.ms.entity.User;
import com.bula.ms.result.CodeMsg;
import com.bula.ms.result.Result;
import com.bula.ms.service.GoodsService;
import com.bula.ms.service.OrderService;
import com.bula.ms.vo.GoodsVo;
import com.bula.ms.vo.OrderInfoVo;
import com.sun.tools.corba.se.idl.constExpr.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @GetMapping(value = "/detail/{orderId}")
    @ResponseBody
    public Result<OrderInfoVo> detail(@PathVariable("orderId") long orderId, User user) {
        OrderInfo orderInfo = orderService.getOrderByUserIdGoodsId(user.getId(), orderId);
        if (orderInfo == null) {
            return Result.error(CodeMsg.ORDER_NOT_FOUND);
        }
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(orderInfo.getGoodsId());
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setGoodsVo(goodsVo);
        orderInfoVo.setOrderInfo(orderInfo);
        return Result.success(orderInfoVo);
    }
}
