package com.example.demo.Dao;

import com.example.demo.Entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsJPARepository extends JpaRepository<Goods,String>, JpaSpecificationExecutor<Goods> {
}
