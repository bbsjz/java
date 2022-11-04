package com.dao;

import com.entity.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
public interface SupplierDao extends BaseMapper<Supplier> {

    //删
    @Delete("INSERT INTO supplier WHERE supplier_id=#{id}")
    void deleteSupplier(String id);

    //查
    @Select("SELECT * FROM supplier WHERE supplier_id=#{id}")
    Supplier getSupplier(String id);

    //根据商品id查找供应商
    @Select("SELECT * FROM supplier "+
    "INNER JOIN goods_list ON supplier.supplier_id=goods_list.list_supplier_id "+
    "WHERE goods_list.goods_id =#{id}")
    List<Supplier> findSuppliers(String id);
}
