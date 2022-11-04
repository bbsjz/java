package com.service;

import com.Exception.GoodsException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.entity.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bbg
 * @since 2022-11-01
 */
public interface ISupplierService extends IService<Supplier> {

    void addSupplier(Supplier supplier) throws GoodsException;//添加供应商

    void deleteSupplier(String id) throws GoodsException;//根据id删除供应商

    public void updateSupplier(String id,Supplier supplier) throws GoodsException;//根据id更新供应商

    public Supplier getSupplier(String id);//根据id查找供应商

    public IPage<Supplier> findSupplier(String key, int quantity, boolean ifAscQuantity, int current, int size);//根据关键词，供应量大于等于quantity查找，分页及排序
}
