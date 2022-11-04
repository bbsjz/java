package com.dao;

import com.entity.Goods;
import com.entity.Goods_list;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bbg
 * @since 2022-11-01
 */
@Mapper
public interface Goods_listDao extends BaseMapper<Goods_list> {

    //根据商品id删除关系
    @Delete("DELETE FROM goods_list WHERE goods_id = #{id}")
    void deleteRelationById(String id);

    //根据商品id查询所有和该商品关联的供应商id
    @Select("SELECT goods_list.list_supplier_id FROM goods_list WHERE goods_id=#{id}")
    List<String> findRelateSupplierId(String id);
}
