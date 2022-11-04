package com.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author bbg
 * @since 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goods_list implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("关系表中商品id")
    private String goodsId;

    @ApiModelProperty("关系表中供应商id")
    private String listSupplierId;


}
