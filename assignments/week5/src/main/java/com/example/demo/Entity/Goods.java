package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@ApiModel("商品实体")
public class Goods {
    public Goods(){}

    @Id
    @ApiModelProperty("商品id")
    String id;

    @ApiModelProperty("商品名")
    @NonNull
    String name;

    @ApiModelProperty("价格")
    @NonNull
    double price;

    @ApiModelProperty("剩余数量")
    int quantity;

    @ApiModelProperty("销售额")
    int saleroom;

    @ManyToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)//同样的商品有多个供应商
    List<Supplier> list=new ArrayList<>();
}
