package com.service;

import com.Exception.GoodsException;
import com.Utils.SearchCondition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.GoodsDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bbg
 * @since 2022-11-01
 */
public interface IGoodsService extends IService<Goods> {

    void deleteGoods(String id) throws GoodsException;//根据id删除商品，同时删除关系表中的关系

    void updateGoods(String id,GoodsDto goodsDto) throws GoodsException;//根据id更新商品

    void addGoodsAndRelation(GoodsDto goodsDto) throws GoodsException;//添加商品及商品关系

    void addGoods(Goods goods) throws GoodsException;//直接添加单个商品

    void updateNumber(int quantity,int saleroom,String id) throws GoodsException;//更新商品库存及销售量

    GoodsDto getGoodsDto(String id);//根据id查询商品，级联查询

    IPage<GoodsDto> findGoods(SearchCondition searchCondition);//根据条件查找商品
}
