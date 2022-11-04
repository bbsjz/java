package com.service.impl;

import com.Exception.GoodsException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Supplier;
import com.dao.SupplierDao;
import com.service.ISupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bbg
 * @since 2022-11-01
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierDao, Supplier> implements ISupplierService {

    @Autowired
    SupplierDao supplierDao;

    //添加供应商
    @Override
    public void addSupplier(Supplier supplier) throws GoodsException {
        if(supplierDao.selectById(supplier.getSupplierId())!=null)
        {
            throw new GoodsException("供应商已存在，不可重复添加");
        }
        supplierDao.insert(supplier);
    }

    //根据id删除供应商
    public void deleteSupplier(String id) throws GoodsException {
        if(supplierDao.selectById(id)==null)
        {
            throw new GoodsException("供应商已不存在，不可重复删除");
        }
        supplierDao.deleteSupplier(id);
    }

    //根据id更新供应商
    public void updateSupplier(String id,Supplier supplier) throws GoodsException {
        deleteSupplier(id);
        addSupplier(supplier);
    }

    //根据id查找供应商
    public Supplier getSupplier(String id)
    {
        return supplierDao.selectById(id);
    }

    //根据关键词，供应量大于等于quantity查找，分页及排序
    public IPage<Supplier> findSupplier(String key,int quantity,boolean ifAscQuantity,int current,int size)
    {
        IPage<Supplier> page=new Page<>(current,size);
        QueryWrapper<Supplier> queryWrapper=new QueryWrapper<>();
        if(key!=null)
        {
            queryWrapper.like("supplier_name",key);
        }
        if(quantity!=0)
        {
            queryWrapper.ge("supplier_quantity",quantity);
        }
        if(ifAscQuantity)
        {
            queryWrapper.orderByAsc("supplier_quantity");
        }
        else
        {
            queryWrapper.orderByDesc("supplier_quantity");
        }
        return supplierDao.selectPage(page,queryWrapper);
    }
}
