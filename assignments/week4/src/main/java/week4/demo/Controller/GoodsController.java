package week4.demo.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import week4.demo.Entity.Goods;
import week4.demo.Service.GoodsService;

import java.util.List;

@Api("商品管理")
@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @ApiOperation("根据id查找商品")
    @GetMapping("/getGoods/{id}")
    public ResponseEntity<Goods> getGoods(@PathVariable("id") String id)
    {
        return ResponseEntity.ok(goodsService.getGood(id));
    }

    @ApiOperation("根据关键字查找商品")
    @GetMapping("/findGoods/{name}")
    public ResponseEntity<List<Goods>> findGoods(@PathVariable("name") String name)
    {
        return ResponseEntity.ok(goodsService.findGood(name));
    }

    @ApiOperation("添加商品")
    @PostMapping("/addGoods")
    public ResponseEntity<Void> addGoods(@RequestBody Goods goods)
    {
        goodsService.addGood(goods);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("根据id更新商品")
    @PutMapping("/updateGoods/{id}")
    public  ResponseEntity<Void> updateGoods(@PathVariable("id") String id,@RequestBody Goods goods)
    {
        goodsService.updateGood(id,goods);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("根据id删除商品")
    @DeleteMapping("/deleteGoods/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") String id)
    {
        goodsService.deleteGood(id);
        return ResponseEntity.noContent().build();
    }
}