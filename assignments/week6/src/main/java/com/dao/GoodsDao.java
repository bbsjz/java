package com.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.GoodsDto;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bbg
 * @since 2022-11-01
 */
@Mapper
public interface GoodsDao extends BaseMapper<Goods> {

    //修改销量和库存
    @Update("UPDATE goods SET quantity=#{quantity}, saleroom=#{saleroom} WHERE id=#{id}")
    void updateGoodsNumber(int quantity,int saleroom,String id);


    //条件查询
    @Select("SELECT DISTINCT goods.*" +
            "FROM goods " +
            " LEFT JOIN goods_list ON goods.id=goods_list.goods_id" +
            " LEFT JOIN supplier ON goods_list.list_supplier_id=supplier.supplier_id" +
            " ${ew.customSqlSegment}")
    @Results({@Result(id=true ,property = "id", column = "id"),
            @Result(property = "list",column = "id",
            many = @Many(select = "com.dao.SupplierDao.findSuppliers"))})
    IPage<GoodsDto> findGoods(IPage<GoodsDto> page,@Param(Constants.WRAPPER) QueryWrapper<GoodsDto> queryWrapper);
}
