package com.controller;


import com.Exception.GoodsException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.entity.Supplier;
import com.service.ISupplierService;
import com.service.impl.SupplierServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
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

@Api("供应商管理")
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    ISupplierService supplierService;

    @ApiOperation("添加供应商")
    @PostMapping("")
    public ResponseEntity<Void> addSupplier(@RequestBody Supplier supplier) throws GoodsException {
        supplierService.addSupplier(supplier);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("根据id删除供应商")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable String id) throws GoodsException {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("根据id修改供应商")
    @PutMapping("")
    public ResponseEntity<Void> updateSupplier(@PathVariable String id,@RequestBody Supplier supplier) throws GoodsException {
        supplierService.updateSupplier(id,supplier);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("根据id查找供应商")
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable String id)
    {
        return ResponseEntity.ok(supplierService.getSupplier(id));
    }

    @ApiOperation("根据条件查找供应商")
    @GetMapping("")
    public ResponseEntity<IPage<Supplier>> findSupplier(@ApiParam("供应商关键词") String key,
                                                        @ApiParam("供应数量最小值") int quantity,
                                                        @ApiParam("是否按照供应量升序排序") boolean ifAscQuantity,
                                                        @ApiParam("当前页") @RequestParam(defaultValue = "0") int current,
                                                        @ApiParam("每页记录数") @RequestParam(defaultValue = "10") int size)
    {
        return ResponseEntity.ok(supplierService.findSupplier(key,quantity,ifAscQuantity,current,size));
    }
}

