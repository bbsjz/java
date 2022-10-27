package com.example.demo.Service;

import com.example.demo.Dao.SupplierJPARepository;
import com.example.demo.Entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService {

    @Autowired
    SupplierJPARepository supplierJPARepository;

    public Supplier addSupplier(Supplier supplier)//增
    {
        supplierJPARepository.save(supplier);
        return supplier;
    }

    public void deleteSupplier(String id)//删
    {
        if(!supplierJPARepository.existsById(id))
        {
            throw new IllegalArgumentException();
        }
        supplierJPARepository.deleteById(id);
    }

    public void updateSupplier(String id,Supplier supplier)//改
    {
        if(!supplierJPARepository.existsById(id))
        {
            throw new IllegalArgumentException();
        }
        supplierJPARepository.deleteById(id);
        supplierJPARepository.save(supplier);
    }

    public Supplier getSupplier(String id)//根据id查询
    {
        if(!supplierJPARepository.existsById(id))
        {
            return null;
        }
        return supplierJPARepository.getReferenceById(id);
    }

    public List<Supplier> findSupplier(String name,int quantity,boolean asc)//根据供应商名字，供应量大于或者等于quantity，以及升序降序查找供应商
    {
        Specification<Supplier> specification=((root, query, criteriaBuilder) -> {
            List<Predicate> list=new ArrayList<>();
            if(name!=null)
            {
                list.add(criteriaBuilder.like(root.get("supplierName"),"%"+name+"%"));
            }
            if(quantity!=0)
            {
                list.add(criteriaBuilder.ge(root.get("supplierQuantity"),quantity));
            }
            Predicate[] p=new Predicate[list.size()];
            if(!asc)//供应量降序排列
            {
                return query.orderBy(criteriaBuilder.desc(root.get("supplierQuantity")))
                        .where(criteriaBuilder.and(list.toArray(p)))
                        .getRestriction();
            }
            else
            {
                return query.orderBy(criteriaBuilder.desc(root.get("supplierQuantity")))
                        .where(criteriaBuilder.and(list.toArray(p)))
                        .getRestriction();
            }
        });
        List<Supplier> list=supplierJPARepository.findAll(specification);
        return list;
    }
}
