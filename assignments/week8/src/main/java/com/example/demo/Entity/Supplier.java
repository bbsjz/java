package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@ApiModel("商品供应商")
public class Supplier {

    public Supplier(){}

    @Id
    @ApiModelProperty("供应商id")
    @JsonProperty(value="supplierId")
    String supplierId;

    @ApiModelProperty("供应商名字")
    @JsonProperty(value="supplierName")
    @NonNull
    String supplierName;

    @ApiModelProperty("供应商供应量")
    @JsonProperty(value = "supplierQuantity")
    int supplierQuantity;
}
