package week4.demo.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("商品实体")
public class Goods {
    //无参构造函数，用于Gson序列化
    Goods(){}

    @ApiModelProperty("商品id")
    String id;

    @ApiModelProperty("商品名")
    String name;

    @ApiModelProperty("价格")
    double price;

    @ApiModelProperty("剩余数量")
    int quantity;

    @ApiModelProperty("销售额")
    int saleroom;
}
