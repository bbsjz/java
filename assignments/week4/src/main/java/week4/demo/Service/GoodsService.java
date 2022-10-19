package week4.demo.Service;

import week4.demo.Entity.Goods;
import java.util.List;

public interface GoodsService {
    public Goods addGood(Goods goods);//通过id添加商品
    public void deleteGood(String id);//通过id删除商品
    public Goods getGood(String id);//通过id查找商品
    public List<Goods> findGood(String name);//通过商品名关键字查找商品
    public void updateGood(String id,Goods goods);//通过id查找商品且改变商品信息
}
