package com.controller;


import com.Exception.GoodsException;
import com.Utils.SearchCondition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dao.GoodsDao;
import com.entity.GoodsDto;
import com.service.IGoodsService;
import com.service.impl.GoodsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bbg
 * @since 2022-11-01
 */

@Api("商品管理")
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IGoodsService goodsService;

    @ApiOperation("添加商品")
    @PostMapping("")
    public ResponseEntity<Void> addGoods(@RequestBody GoodsDto goodsDto) throws GoodsException {
        goodsService.addGoods(goodsDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("根据id删除商品")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable String id) throws GoodsException {
        goodsService.deleteGoods(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("根据id修改商品")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGoods(@PathVariable String id,@RequestBody GoodsDto goodsDto) throws GoodsException {
        goodsService.updateGoods(id,goodsDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("根据id查找商品")
    @GetMapping("/{id}")
    public ResponseEntity<GoodsDto> getGoods(@PathVariable String id)
    {
        return ResponseEntity.ok(goodsService.getGoodsDto(id));
    }

    @ApiOperation("根据条件查找商品，条件被封装在实体类中")
    @GetMapping("")
    public ResponseEntity<IPage<GoodsDto>> findGoods(SearchCondition searchCondition)
    {
        return ResponseEntity.ok(goodsService.findGoods(searchCondition));
    }
}

