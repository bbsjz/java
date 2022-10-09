import Context.MiniApplicationContext;
import Context.NoBasicFunctionException;
import com.service.impl.BookServiceImpl;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.net.MalformedURLException;

public class MainTest {
    @Test
    public void Normal()
    {
        try {
            MiniApplicationContext miniApplicationContext = new MiniApplicationContext("applicationContext.xml");
            BookServiceImpl bookService=(BookServiceImpl) miniApplicationContext.getBean("bookService");
            bookService.save();
        } catch (NoBasicFunctionException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void NoBasicFunctionTest()
    {
        assertThrows(NoBasicFunctionException.class,()->{
            MiniApplicationContext miniApplicationContext=new MiniApplicationContext("NoBasicFunction.xml");
        });
        assertThrows(NoBasicFunctionException.class,()->{
            MiniApplicationContext miniApplicationContext=new MiniApplicationContext("NoBasicFunction2.xml");
        });
    }

    @Test
    public void DocumentExceptionTest()
    {
        assertThrows(DocumentException.class,()->{
            MiniApplicationContext miniApplicationContext=new MiniApplicationContext("MalformedURL.xml");
        });
    }

    @Test
    public void ClassNotFoundTest()
    {
        assertThrows(ClassNotFoundException.class,()->{
            MiniApplicationContext miniApplicationContext=new MiniApplicationContext("ClassNotFound.xml");
        });
    }

    @Test
    public void IllegalAccessTest()
    {
        assertThrows(IllegalAccessException.class,()->{
            MiniApplicationContext miniApplicationContext=new MiniApplicationContext("IllegalAccess.xml");
        });
    }
}
