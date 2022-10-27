package com.example.demo;

import com.example.demo.Dao.SupplierJPARepository;
import com.example.demo.Entity.Supplier;
import com.example.demo.Service.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class SupplierTest {
    @Autowired
    SupplierService supplierService;

    @Autowired
    SupplierJPARepository supplierJPARepository;

    @BeforeEach
    public void init()
    {
        Supplier supplier1=new Supplier();
        supplier1.setSupplierId("1");
        supplier1.setSupplierName("A");
        supplier1.setSupplierQuantity(500);

        Supplier supplier2=new Supplier();
        supplier2.setSupplierId("2");
        supplier2.setSupplierName("B");
        supplier2.setSupplierQuantity(300);

        supplierJPARepository.save(supplier1);
        supplierJPARepository.save(supplier2);
    }

    @Test
    public void TestAdd()
    {
        Supplier supplier1=new Supplier();
        supplier1.setSupplierId("3");
        supplier1.setSupplierName("C");
        supplier1.setSupplierQuantity(400);

        supplierService.addSupplier(supplier1);
        Supplier supplier=supplierService.getSupplier("3");

        assertEquals(supplier1.getSupplierId(),supplier.getSupplierId());
        assertEquals(supplier1.getSupplierName(),supplier.getSupplierName());
        assertEquals(supplier1.getSupplierQuantity(),supplier.getSupplierQuantity());
    }

    @Test
    public void TestGet()
    {
        Supplier supplier=supplierService.getSupplier("1");

        assertEquals("1",supplier.getSupplierId());
        assertEquals("A",supplier.getSupplierName());
        assertEquals(500,supplier.getSupplierQuantity());
    }

    @Test
    public void TestFind()
    {
        //根据公司名查找
        List<Supplier> list=supplierService.findSupplier("A",0,false);
        assertEquals(1,list.size());
        assertEquals("A",list.get(0).getSupplierName());

        //根据供货量大于等于查找
        List<Supplier> list1=supplierService.findSupplier(null,300,false);
        assertEquals(2,list1.size());

        List<Supplier> list2=supplierService.findSupplier(null,301,false);
        assertEquals(1,list2.size());

        //根据升序降序查找
        List<Supplier> list3=supplierService.findSupplier(null,0,false);
        assertEquals(500,list3.get(0).getSupplierQuantity());
        assertEquals(300,list3.get(1).getSupplierQuantity());

        List<Supplier> list4 =supplierService.findSupplier(null,0,true);
        assertEquals(300,list4.get(0).getSupplierQuantity());
        assertEquals(500,list4.get(1).getSupplierQuantity());
    }
}
