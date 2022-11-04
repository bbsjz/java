package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "supplier_id", type = IdType.INPUT)
    @ApiModelProperty("供应商id")
    private String supplierId;

    @ApiModelProperty("供应商姓名")
    private String supplierName;

    @ApiModelProperty("供应商供应量")
    private Integer supplierQuantity;
}
