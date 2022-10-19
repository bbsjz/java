package week4.demo.Service.Impl;

import org.springframework.stereotype.Service;
import week4.demo.Entity.Goods;
import week4.demo.Service.GoodsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    Map<String,Goods> map=new HashMap<>();

    @Override
    public Goods addGood(Goods goods) {
        map.put(goods.getId(),goods);
        return goods;
    }

    @Override
    public void deleteGood(String id) {
        map.remove(id);
    }

    @Override
    public Goods getGood(String id) {
        return map.get(id);
    }

    @Override
    public List<Goods> findGood(String key) {
        List<Goods> resList=new ArrayList<>();
        for(Goods goods:map.values())
        {
            if(goods.getName().contains(key))
            {
                resList.add(goods);
            }
        }
        return resList;
    }

    @Override
    public void updateGood(String id, Goods goods) {
        map.replace(id,goods);
    }
}
