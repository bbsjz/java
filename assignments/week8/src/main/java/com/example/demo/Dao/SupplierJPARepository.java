package com.example.demo.Dao;

import com.example.demo.Entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupplierJPARepository extends JpaRepository<Supplier,String>, JpaSpecificationExecutor<Supplier> {
}
