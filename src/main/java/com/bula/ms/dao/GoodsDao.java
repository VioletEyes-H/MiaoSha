package com.bula.ms.dao;

import com.bula.ms.entity.Goods;
import com.bula.ms.entity.MsGoods;
import com.bula.ms.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * (Goods)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-16 23:13:12
 */
@Mapper
public interface GoodsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Goods queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Goods> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param goods 实例对象
     * @return 对象列表
     */
    List<Goods> queryAll(Goods goods);

    /**
     * 新增数据
     *
     * @param goods 实例对象
     * @return 影响行数
     */
    int insert(Goods goods);

    /**
     * 修改数据
     *
     * @param goods 实例对象
     * @return 影响行数
     */
    int update(Goods goods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 多表查询秒杀商品表的数据放入到商品表的数据里返回给GoodsVo
     *
     * @return
     */
    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.ms_price from ms_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> getGoodsVoList();

    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.ms_price from ms_goods mg left join goods g on mg.goods_id = g.id where g.id=#{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update ms_goods set stock_count = stock_count -1 where goods_id = #{id} and stock_count>0")
    int reduceStock(long id);
}