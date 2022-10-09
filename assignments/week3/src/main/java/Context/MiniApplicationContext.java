package Context;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class MiniApplicationContext {
    Map<String, BeanDefinition> map=new HashMap<>();// 保存bean信息
    Map<String, Object> objectMap=new HashMap<>();// 保存创建的对象
    List<BeanDefinition> hasRef = new ArrayList<>();// 保存有bean依赖的bean的列表信息

    public MiniApplicationContext(String path) throws NoBasicFunctionException, MalformedURLException, DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        SAXReader reader = new SAXReader();
        ClassLoader classLoader = getClass().getClassLoader();
        URL url=classLoader.getResource(path);
        Document document = reader.read(new File(url.getFile()));
        Element rootElement = document.getRootElement();
        List beanMem = rootElement.elements("bean");
        // 先生成所有的bean，进行基本属性注入
        for(Iterator it = beanMem.iterator();it.hasNext();)
        {
            Element elm = (Element) it.next();
            String name = elm.attributeValue("id"); // bean的名字
            String beanClass=elm.attributeValue("class"); // bean所属class
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setName(name);
            beanDefinition.setBeanClass(beanClass);
            List<Element> ref = elm.elements("property");
            Class myClass = Class.forName(beanClass);
            Object obj =myClass.newInstance();
            for(Element refElm:ref)
            {
                String setName = refElm.attributeValue("name");
                String setValue = refElm.attributeValue("value");
                String setRef = refElm.attributeValue("ref");
                if(!(setValue==null))
                {
                    String mName="set"+(char)(setName.charAt(0)-32)+setName.substring(1);
                    injectBasic(myClass,mName,obj,setValue);
                }
                else if(!(setRef==null))
                {
                    beanDefinition.addMap(setName,setRef); // 添加依赖项
                    hasRef.add(beanDefinition); // 把对象添加到有依赖的列表内
                }
            }
            objectMap.put(name,obj);
            map.put(name,beanDefinition);
        }
        // 再遍历有依赖的列表，处理和bean依赖有关的注入
        for(BeanDefinition b: hasRef){
            Class myClass = Class.forName(b.getBeanClass());
            Object obj=objectMap.get(b.getName());
            for(Map.Entry<String,String> entry:b.getMap().entrySet())
            {
                String methodName=entry.getKey();
                String beanName=entry.getValue();
                Object refObj=objectMap.get(beanName);
                String mName="set"+(char)(methodName.charAt(0)-32)+methodName.substring(1);
                BeanDefinition beanDefinition=map.get(beanName);
                Class refClass=Class.forName(beanDefinition.getBeanClass());
                injectRef(myClass,mName,obj,refObj,refClass);
            }
        }
    }
    public Object getBean(String beanName)
    {
        return objectMap.get(beanName);
    }

    public void injectRef(Class myClass,String mName,Object obj,Object refObj,Class refClass)
    {
        Class[] implement=refClass.getInterfaces();
        for(Class i:implement)
        {
            try {
                Method m = myClass.getMethod(mName,i);
                m.setAccessible(true);
                m.invoke(obj,refObj);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                continue;
            }
        }
    }

    public void injectBasic(Class myClass,String mName,Object obj,String setValue)throws NoBasicFunctionException // 注入8种基本类型，依次遍历
    {
        boolean injectSuccess=false;
       for(Class it:Util.basic)
       {
           try {
               Method m=myClass.getMethod(mName,it);
               m.setAccessible(true);
                switch (it.getName()){
                    case "byte":
                        byte b=Util.toByte(setValue);
                        m.invoke(obj,b);
                        break;
                    case "short":
                        short sh=Util.toShort(setValue);
                        m.invoke(obj,sh);
                        break;
                    case "int":
                        int i=Util.toInt(setValue);
                        m.invoke(obj,i);
                        break;
                    case "long":
                        long l=Util.toLong(setValue);
                        m.invoke(obj,l);
                        break;
                    case "char":
                        char c=Util.toChar(setValue);
                        m.invoke(obj,c);
                        break;
                    case "java.lang.String":
                        m.invoke(obj,setValue);
                        break;
                    case "float":
                        float f=Util.toFloat(setValue);
                        m.invoke(obj,f);
                        break;
                    case "double":
                        double d=Util.toDouble(setValue);
                        m.invoke(obj,d);
                        break;
                    case "boolean":
                        boolean bo=Util.toBoolean(setValue);
                        m.invoke(obj,bo);
                        break;
                }
                injectSuccess=true;
           } catch (NoSuchMethodException | ConvertFailedException |InvocationTargetException | IllegalAccessException e) {
               continue;
           }
       }
       if(!injectSuccess) // 循环全部基本类型，还没有注入成功的话，则抛出异常
       {
           throw new NoBasicFunctionException();
       }
    }


}
