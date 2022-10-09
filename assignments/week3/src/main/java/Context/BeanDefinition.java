package Context;

import java.util.HashMap;
import java.util.Map;

public class BeanDefinition {
    private String name; // id
    private String beanClass; // 所属类别
    private Map<String,String> ref=new HashMap<>(); // 所依赖的bean

    public void setName(String name)
    {
        this.name=name;
    }

    public void setBeanClass(String beanClass)
    {
        this.beanClass=beanClass;
    }

    public String getBeanClass()
    {
        return beanClass;
    }

    public Map<String,String> getMap()
    {
        return ref;
    }

    public String getName(){
        return name;
    }

    public void addMap(String setName,String refBean)
    {
        ref.put(setName,refBean);
    }
}
