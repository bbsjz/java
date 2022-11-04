package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
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
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty("商品id")
    private String id;

    @ApiModelProperty("商品名")
    private String name;

    @ApiModelProperty("商品价格")
    private Double price;

    @ApiModelProperty("商品库存")
    private Integer quantity;

    @ApiModelProperty("商品销量")
    private Integer saleroom;
}
