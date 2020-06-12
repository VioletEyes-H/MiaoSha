package com.bula.ms.dao;

import com.bula.ms.entity.MsOrder;
import com.bula.ms.entity.OrderInfo;
import com.bula.ms.vo.OrderInfoVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (OrderInfo)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-16 23:52:53
 */
@Mapper
public interface OrderInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OrderInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param orderInfo 实例对象
     * @return 对象列表
     */
    List<OrderInfo> queryAll(OrderInfo orderInfo);

    /**
     * 新增数据
     *
     * @param orderInfo 实例对象
     * @return 影响行数
     */
    @Insert("insert into order_info(user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status,create_date)values" +
            "(#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    int insert(OrderInfo orderInfo);

    /**
     * 修改数据
     *
     * @param orderInfo 实例对象
     * @return 影响行数
     */
    int update(OrderInfo orderInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    @Insert("insert into ms_order(user_id,order_id,goods_id)values(#{userId},#{orderId},#{goodsId})")
    void insertMiaoshaOrder(MsOrder msOrder);

    @Select("select * from ms_order where user_id=#{userId} and goods_id=#{goodsId}")
    MsOrder getMiaoshaOrderByUserIdGoodsId(Long userId, long goodsId);

    @Select("select * from order_info where user_id=#{userId} and id=#{orderId}")
    OrderInfo getOrderByUserIdGoodsId(long userId, long orderId);

}