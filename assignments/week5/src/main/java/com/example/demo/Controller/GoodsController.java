package com.example.demo.Controller;

import com.example.demo.Entity.Goods;
import com.example.demo.Service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("商品管理")
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @ApiOperation("添加商品")
    @PostMapping("")
    public ResponseEntity<Goods> addGoods(@RequestBody Goods goods)
    {
        goodsService.addGoods(goods);
        return ResponseEntity.ok(goods);
    }

    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable String id)
    {
        try{
            goodsService.deleteGoods(id);
        }
        catch (IllegalArgumentException ex)//要删除的商品不存在
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("修改商品")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGoods(@PathVariable String id,@RequestBody Goods goods)
    {
        try{
            goodsService.updateGoods(id,goods);
        }
        catch(IllegalArgumentException ex)//要更改的商品不存在
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation("根据id查找商品")
    @GetMapping("/{id}")
    public ResponseEntity<Goods> getGoods(@PathVariable String id)
    {
        Goods goods=goodsService.getGoods(id);
        if(goods==null)
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(goods);
    }

    @ApiOperation("根据关键词，供应商，销量排序查找商品")
    @GetMapping("")
    public ResponseEntity<List<Goods>> findGoods(String key,String supplier,boolean asc)
    {
        List<Goods> list=goodsService.findGoods(key,supplier,asc);
        return ResponseEntity.ok(list);
    }
}
