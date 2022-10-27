package com.example.demo;

import com.example.demo.Dao.GoodsJPARepository;
import com.example.demo.Entity.Goods;
import com.example.demo.Entity.Supplier;
import com.example.demo.Service.GoodsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GoodsTest {

	@Autowired
	GoodsService goodsService;

	@Autowired
	GoodsJPARepository goodsJPARepository;

	@BeforeEach
	public void init()
	{
		//第一个商品
		List<Supplier> list1=new ArrayList<>();

		Supplier supplier1=new Supplier();
		supplier1.setSupplierId("1");
		supplier1.setSupplierName("百事");
		supplier1.setSupplierQuantity(300);

		Supplier supplier2=new Supplier();
		supplier2.setSupplierId("2");
		supplier2.setSupplierName("可口");
		supplier2.setSupplierQuantity(500);

		list1.add(supplier1);
		list1.add(supplier2);

		Goods goods1=new Goods();
		goods1.setId("1");
		goods1.setName("可乐");
		goods1.setPrice(5);
		goods1.setQuantity(800);
		goods1.setSaleroom(30);
		goods1.setList(list1);

		//第二个商品
		List<Supplier> list2=new ArrayList<>();

		Supplier supplier3=new Supplier();
		supplier3.setSupplierId("3");
		supplier3.setSupplierName("可比克");
		supplier3.setSupplierQuantity(500);

		Supplier supplier4=new Supplier();
		supplier4.setSupplierId("4");
		supplier4.setSupplierName("乐事");
		supplier4.setSupplierQuantity(300);

		list2.add(supplier3);
		list2.add(supplier4);

		Goods goods2=new Goods();
		goods2.setId("2");
		goods2.setName("薯片");
		goods2.setPrice(5);
		goods2.setQuantity(800);
		goods2.setSaleroom(50);
		goods2.setList(list2);

		goodsJPARepository.save(goods1);
		goodsJPARepository.save(goods2);

	}

	@Test
	void TestAdd() {
		List<Supplier> list=new ArrayList<>();

		//第三个商品
		Supplier supplier=new Supplier();
		supplier.setSupplierId("5");
		supplier.setSupplierName("天润");
		supplier.setSupplierQuantity(500);

		list.add(supplier);

		Goods goods=new Goods();
		goods.setId("3");
		goods.setName("奶啤");
		goods.setPrice(6);
		goods.setSaleroom(100);
		goods.setQuantity(500);
		goods.setList(list);

		goodsService.addGoods(goods);

		String id= goods.getId();
		Goods goods1=goodsService.getGoods(id);
		assertEquals(goods.getId(),goods1.getId());
		assertEquals(goods.getName(),goods1.getName());
		assertEquals(goods.getPrice(),goods1.getPrice());
		assertEquals(goods.getQuantity(),goods1.getQuantity());
		assertEquals(goods.getSaleroom(),goods1.getSaleroom());
		assertEquals(goods.getList(),goods1.getList());
	}

	@Test
	public void TestDelete()
	{
		//删除存在的商品后，查询不存在的商品会报错
		goodsService.deleteGoods("1");
		assertEquals(null,goodsService.getGoods("1"));

		//删除不存在的商品会报错
		assertThrows(IllegalArgumentException.class,()->{
			goodsService.deleteGoods("1");
		});
	}

	@Test
	public void TestUpdate()
	{
		List<Supplier> list1=new ArrayList<>();

		Supplier supplier1=new Supplier();
		supplier1.setSupplierId("1");
		supplier1.setSupplierName("嘿嘿嘿");
		supplier1.setSupplierQuantity(300);

		Supplier supplier2=new Supplier();
		supplier2.setSupplierId("5");
		supplier2.setSupplierName("哈哈哈");
		supplier2.setSupplierQuantity(500);

		list1.add(supplier1);
		list1.add(supplier2);

		Goods goods1=new Goods();
		goods1.setId("1");
		goods1.setName("可乐");
		goods1.setPrice(5);
		goods1.setQuantity(1000);
		goods1.setSaleroom(30);
		goods1.setList(list1);

		goodsService.updateGoods("1",goods1);

		Goods goods=goodsService.getGoods("1");

		assertEquals(goods1.getId(),goods.getId());
		assertEquals(goods1.getName(),goods.getName());
		assertEquals(goods1.getPrice(),goods.getPrice());
		assertEquals(goods1.getQuantity(),goods.getQuantity());
		assertEquals(goods1.getSaleroom(),goods.getSaleroom());
		assertEquals(goods1.getList(),goods.getList());
	}

	@Test
	public void TestGet()
	{
		Goods goods1=goodsService.getGoods("1");
		Goods goods2=goodsService.getGoods("2");

		assertEquals("1",goods1.getId());
		assertEquals("可乐",goods1.getName());
		assertEquals(5,goods1.getPrice());
		assertEquals(800,goods1.getQuantity());
		assertEquals(30,goods1.getSaleroom());

		assertEquals("2",goods2.getId());
		assertEquals("薯片",goods2.getName());
		assertEquals(5,goods2.getPrice());
		assertEquals(800,goods2.getQuantity());
		assertEquals(50,goods2.getSaleroom());
	}

	@Test
	public void TestFind()
	{
		//根据关键字查找
		List<Goods> list=goodsService.findGoods("片",null,false);
		assertEquals(1,list.size());
		assertEquals("薯片",list.get(0).getName());

		//根据供应商查找
		List<Goods> list1=goodsService.findGoods(null,"事",false);
		assertEquals(2,list1.size());

		//根据降序查找
		List<Goods> list2=goodsService.findGoods(null,null,false);
		assertEquals(50,list2.get(0).getSaleroom());
		assertEquals(30,list2.get(1).getSaleroom());

		//根据升序查找
		List<Goods> list3=goodsService.findGoods(null,null,true);
		assertEquals(30,list3.get(0).getSaleroom());
		assertEquals(50,list3.get(1).getSaleroom());

		//组合查找
		List<Goods> list4=goodsService.findGoods("薯","可比克",false);
		assertEquals(1,list4.size());
		assertEquals("薯片",list4.get(0).getName());
	}

}
