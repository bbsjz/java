package edu.whu;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * io操作读取配置文件，通过反射和自定义注解调用方法
 */
public class Main {
    static String className;
    static Class myClass;
    static boolean ret;

    public static void clear()//重置各变量
    {
        className=null;
        myClass=null;
        ret=false;
    }
    public static void readProperty(String path) throws IOException//读取属性文件
    {
        Properties properties = new Properties();
        try(InputStream in = Main.class.getResourceAsStream(path))
        {
            properties.load(in);
        }
        className = properties.getProperty("bootstrapClass");
    }

    public static void createClass() throws ClassNotFoundException//创建类
    {
        myClass=Class.forName(className);
    }

    public static void reflection(String[] testString) throws InstantiationException,IllegalAccessException//创建方法
    {
        Object obj = myClass.newInstance();
        Method[] methods = myClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(initMethod.class))//寻找是否有initMethod注解在该方法上
            {
                try {
                    ret = (boolean) method.invoke(obj, testString);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean getRet()
    {
        return ret;
    }
    public static void main(String[] args){
        try {
            readProperty("/myapp.properties");
            createClass();
            String[] test={"hello world"};
            reflection(test);
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
