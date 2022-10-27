package com.example.demo.Service;
import com.example.demo.Dao.GoodsJPARepository;
import com.example.demo.Entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService {
    @Autowired
    GoodsJPARepository goodsJPARepository;

    public Goods addGoods(Goods goods)//增
    {
        return goodsJPARepository.save(goods);
    }

    public void deleteGoods(String id)//删
    {
        if(!goodsJPARepository.existsById(id))
        {
            throw new IllegalArgumentException();
        }
        goodsJPARepository.deleteById(id);
    }

    public void updateGoods(String id,Goods goods)//改
    {
        if(!goodsJPARepository.existsById(id))
        {
            throw new IllegalArgumentException();
        }
        goodsJPARepository.deleteById(id);
        goodsJPARepository.saveAndFlush(goods);
    }

    public Goods getGoods(String id)//根据id查询
    {
        if(!goodsJPARepository.existsById(id))
        {
            return null;
        }
        return goodsJPARepository.getReferenceById(id);
    }

    //根据关键词+供应商筛选+销量升序降序查询
    public List<Goods> findGoods(String key,String supplier,boolean asc)
    {
        Specification<Goods> specification=((root, query, criteriaBuilder) -> {
           List<Predicate> list=new ArrayList<>();
           if(key!=null)
           {
                list.add(criteriaBuilder.like(root.get("name"),"%"+key+"%"));
           }
           if(supplier!=null)
           {
               list.add(criteriaBuilder.like(root.join("list", JoinType.LEFT).get("supplierName"),"%"+supplier+"%"));
           }
           Predicate[] p=new Predicate[list.size()];
           if(!asc)//销量降序排列
           {
               return query.orderBy(criteriaBuilder.desc(root.get("saleroom")))
                       .where(criteriaBuilder.and(list.toArray(p)))
                       .getRestriction();
           }
           else {
               return query.orderBy(criteriaBuilder.asc(root.get("saleroom")))
                       .where(criteriaBuilder.and(list.toArray(p)))
                       .getRestriction();
           }
        });
        List<Goods> list=goodsJPARepository.findAll(specification);
        return list;
    }
}
