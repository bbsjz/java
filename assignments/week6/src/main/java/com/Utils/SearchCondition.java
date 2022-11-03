package com.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SearchCondition implements Serializable {
    //商品关键词
    String key;

    //价格区间
    Boolean ifAscPrice;//是否按照价格升序排序
    Double lowPrice;//最低价格
    Double highPrice;//最高价格

    //库存区间
    Boolean ifAscQuantity;//是否按照库存升序排序
    Integer lowQuantity;//最低库存量
    Integer highQuantity;//最高库存量

    //销量区间
    Boolean ifAscSaleroom;//是否按照销量升序排序
    Integer lowSaleroom;//最低销量
    Integer highSaleroom;//最高销量

    //供应商关键字
    String supplier;

    //当前页
    Integer current;

    //一页大小
    Integer size;
}
