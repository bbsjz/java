package com.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GoodsDto extends Goods{

    @ApiModelProperty("供应商列表")
    private List<Supplier> list;
}
