package edu.whu;

/**
 * 反射类
 */
public class MyClass {
    @initMethod
    public boolean init(String test){
        System.out.println("edu.whu.MyClass is init! test String:"+test);
        return true;
    }
}
