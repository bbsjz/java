package com.service.impl;

import com.Exception.GoodsException;
import com.Utils.SearchCondition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dao.Goods_listDao;
import com.dao.SupplierDao;
import com.entity.Goods;
import com.dao.GoodsDao;
import com.entity.GoodsDto;
import com.entity.Goods_list;
import com.entity.Supplier;
import com.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bbg
 * @since 2022-11-01
 */

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, Goods> implements IGoodsService {

    @Autowired
    GoodsDao goodsDao;

    @Autowired
    Goods_listDao goods_listDao;

    @Autowired
    SupplierDao supplierDao;

    //只添加商品
    @Override
    public void addGoods(Goods goods) throws GoodsException {
        if(goodsDao.selectById(goods.getId())!=null)
        {
            throw new GoodsException("商品id已经存在，不可重复添加");
        }
        goodsDao.insert(goods);
    }

    //添加商品及关系，不添加供应商
    @Transactional
    @Override
    public void addGoodsAndRelation(GoodsDto goodsDto) throws GoodsException
    {
        if(goodsDao.selectById(goodsDto.getId())!=null)
        {
            throw new GoodsException("商品id已经存在，不可重复添加");
        }

        Goods goods=new Goods();
        goods.setId(goodsDto.getId());
        goods.setName(goodsDto.getName());
        goods.setPrice(goodsDto.getPrice());
        goods.setQuantity(goodsDto.getQuantity());
        goods.setSaleroom(goodsDto.getSaleroom());

        goodsDao.insert(goods);

        for(Supplier supplier:goodsDto.getList())
        {
            Supplier supplier1=supplierDao.getSupplier(supplier.getSupplierId());
            if(supplier1==null)
            {
                throw new GoodsException("商品中存在不存在的供应商，无法添加");
            }
            Goods_list goods_list=new Goods_list();
            goods_list.setGoodsId(goodsDto.getId());
            goods_list.setListSupplierId(supplier.getSupplierId());
            goods_listDao.insert(goods_list);
        }
    }

    //删除商品，同时删除关系表中与该商品有关的关系
    @Transactional
    @Override
    public void deleteGoods(String id) throws GoodsException {
        if(goodsDao.selectById(id)==null)
        {
            throw new GoodsException("该商品已不存在，不可重复删除");
        }
        goods_listDao.deleteRelationById(id);
        goodsDao.deleteById(id);
    }

    //根据id修改商品
    @Transactional
    @Override
    public void updateGoods(String id,GoodsDto goodsDto) throws GoodsException
    {
        deleteGoods(id);
        addGoodsAndRelation(goodsDto);
    }

    //更新商品库存及数量
    @Override
    public void updateNumber(int quantity,int saleroom,String id) throws GoodsException {
        if(goodsDao.selectById(id)==null)
        {
            throw new GoodsException("该商品已不存在，不可更改数量");
        }
        goodsDao.updateGoodsNumber(quantity,saleroom,id);
    }

    //根据id查询商品，级联查询
    @Override
    public GoodsDto getGoodsDto(String id)
    {
        Goods goods=goodsDao.selectById(id);
        if(goods==null)
        {
            return null;
        }
        List<String> list=goods_listDao.findRelateSupplierId(id);
        GoodsDto goodsDto=new GoodsDto();
        goodsDto.setId(goods.getId());
        goodsDto.setName(goods.getName());
        goodsDto.setQuantity(goods.getQuantity());
        goodsDto.setPrice(goods.getPrice());
        goodsDto.setSaleroom(goods.getSaleroom());
        if(list.size()==0)
        {
            goodsDto.setList(null);
            return goodsDto;
        }
        List<Supplier> list1=new ArrayList<>();
        for(String supplier_id:list)
        {
            Supplier supplier=supplierDao.getSupplier(supplier_id);
            list1.add(supplier);
        }
        goodsDto.setList(list1);
        return goodsDto;
    }

    //根据关键字，价格，库存，销量和供应商查找商品，排序，并分页
    public IPage<GoodsDto> findGoods(SearchCondition searchCondition)
    {
        if(searchCondition.getSize()==null)
        {
            searchCondition.setSize(10);
        }
        if(searchCondition.getCurrent()==null)
        {
            searchCondition.setCurrent(0);
        }
        IPage page=new Page(searchCondition.getCurrent(),searchCondition.getSize());
        QueryWrapper<GoodsDto> queryWrapper=new QueryWrapper<>();
        //关键字
        if(searchCondition.getKey()!=null)
        {
            queryWrapper.like("goods.name",searchCondition.getKey());
        }
        //价格区间
        if(searchCondition.getLowPrice()!=null)
        {
            queryWrapper.ge("goods.price",searchCondition.getLowPrice());
        }
        if(searchCondition.getHighPrice()!=null)
        {
            queryWrapper.le("goods.price",searchCondition.getHighPrice());
        }
        //是否按照价格升序排序
        if(searchCondition.getIfAscPrice()!=null)
        {
            if(searchCondition.getIfAscPrice())
            {
                queryWrapper.orderByAsc("goods.price");
            }
            else
            {
                queryWrapper.orderByDesc("goods.price");
            }
        }
        //库存区间
        if(searchCondition.getLowQuantity()!=null)
        {
            queryWrapper.ge("goods.quantity",searchCondition.getLowQuantity());
        }
        if(searchCondition.getHighQuantity()!=null)
        {
            queryWrapper.le("goods.quantity", searchCondition.getHighQuantity());
        }
        //是否按照库存数量升序排序
        if(searchCondition.getIfAscQuantity()!=null)
        {
            if(searchCondition.getIfAscQuantity())
            {
                queryWrapper.orderByAsc("goods.quantity");
            }
            else
            {
                queryWrapper.orderByDesc("goods.quantity");
            }
        }
        //销量区间
        if(searchCondition.getLowSaleroom()!=null)
        {
            queryWrapper.ge("goods.saleroom",searchCondition.getLowSaleroom());
        }
        if(searchCondition.getHighSaleroom()!=null)
        {
            queryWrapper.le("goods.saleroom", searchCondition.getHighSaleroom());
        }
        //是否按照销量升序排序
        if(searchCondition.getIfAscSaleroom()!=null)
        {
            if(searchCondition.getIfAscSaleroom())
            {
                queryWrapper.orderByAsc("goods.saleroom");
            }
            else
            {
                queryWrapper.orderByDesc("goods.saleroom");
            }
        }
        //供应商关键字
        if(searchCondition.getSupplier()!=null)
        {
            queryWrapper.like("supplier.supplier_name",searchCondition.getSupplier());
        }
        goodsDao.findGoods(page,queryWrapper);
        return page;
    }
}
