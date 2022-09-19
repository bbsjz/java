import static org.junit.jupiter.api.Assertions.*;
import edu.whu.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MainTest {
    @AfterEach
    void clear()//测试完后清空Main中的变量
    {
        Main.clear();
    }

    @Test
    void createClassTest()//测试文件读入和类创建
    {
        try {
            Main.readProperty("/myapp.properties");
            Main.createClass();//创建成功
            Main.readProperty("/pathNotExist.properties");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        assertThrows(ClassNotFoundException.class,()->{
            Main.createClass();//类不存在
        });
        assertThrows(NullPointerException.class,()->{
            Main.readProperty("path not exist");//文件不存在
        });
    }
    @Test
    void invokeTest()//测试方法调用
    {
        //正常运行
        try {
            Main.readProperty("/myapp.properties");
            Main.createClass();
            String[] test={"hello world"};
            Main.reflection(test);
            assertEquals(true,Main.getRet());
        } catch (IOException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();

        String[] test1={};
        //缺少参数
        assertThrows(IllegalArgumentException.class,()->{
            Main.reflection(test1);
        });
        String[] test2={"1","2"};
        //参数多余
        assertThrows(IllegalArgumentException.class,()->{
            Main.reflection(test2);
        });
    }
}
}
