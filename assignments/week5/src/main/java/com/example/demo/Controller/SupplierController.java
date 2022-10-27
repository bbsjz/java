package com.example.demo.Controller;

import com.example.demo.Entity.Supplier;
import com.example.demo.Service.SupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("供应商管理")
@RequestMapping("/suppliers")
@RestController
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @ApiModelProperty("添加供应商")
    @PostMapping("")
    public ResponseEntity<Supplier> addSupplier(@RequestBody Supplier supplier)
    {
        supplierService.addSupplier(supplier);
        return ResponseEntity.ok(supplier);
    }

    @ApiModelProperty("删除供应商")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable String id)
    {
        try{
            supplierService.deleteSupplier(id);
        }
        catch(IllegalArgumentException ex)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @ApiModelProperty("修改供应商")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSupplier(@PathVariable String id,@RequestBody Supplier supplier)
    {
        try{
            supplierService.updateSupplier(id,supplier);
        }
        catch(IllegalArgumentException ex)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @ApiModelProperty("根据id查找供应商")
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable String id)
    {
        Supplier supplier= supplierService.getSupplier(id);
        if(supplier==null)
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(supplier);
    }

    @ApiModelProperty("根据供应商姓名关键字，供应数量，供应数量排序查找供应商")
    @GetMapping("")
    public ResponseEntity<List<Supplier>> findSupplier(String key, int quantity, boolean asc)
    {
        List<Supplier> list=supplierService.findSupplier(key,quantity,asc);
        if(list.size()==0)
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

}
