package Context;
import java.nio.charset.StandardCharsets;

public class Util {
    public static Class[] basic=
            {byte.class,short.class,int.class,long.class,char.class,
                    String.class,float.class,double.class,boolean.class};

    public static byte toByte(String s) throws ConvertFailedException
    {
        byte[] b=s.getBytes(StandardCharsets.UTF_8);
        if (b.length==1)
        {
            return b[0];
        }
        throw new ConvertFailedException();
    }

    public static short toShort(String s) throws ConvertFailedException {
        try{
            short sh=Short.parseShort(s);
            return sh;
        }
        catch (NumberFormatException e)
        {
            throw new ConvertFailedException();
        }
    }

    public static int toInt(String s) throws ConvertFailedException{
        try{
            int i=Integer.parseInt(s);
            return i;
        }
        catch(NumberFormatException e)
        {
            throw new ConvertFailedException();
        }
    }

    public static long toLong(String s) throws ConvertFailedException{
        try{
            long l=Long.parseLong(s);
            return l;
        }
        catch(NumberFormatException e)
        {
            throw new ConvertFailedException();
        }
    }

    public static char toChar(String s) throws ConvertFailedException {
        char[] c=s.toCharArray();
        if(c.length==1)
        {
            return c[0];
        }
        else
        {
            throw new ConvertFailedException();
        }
    }

    public static float toFloat(String s) throws ConvertFailedException {
        try{
            float f=Float.parseFloat(s);
            return f;
        }
        catch(NumberFormatException e)
        {
            throw new ConvertFailedException();
        }
    }

    public static double toDouble(String s)throws ConvertFailedException{
        try{
            double d=Double.parseDouble(s);
            return d;
        }
        catch (NumberFormatException e)
        {
            throw new ConvertFailedException();
        }
    }

    public static boolean toBoolean(String s) throws ConvertFailedException {
        if(s.equals("true")||s.equals("1"))
        {
            return true;
        }
        else if(s.equals("false")||s.equals("0"))
        {
            return false;
        }
        throw new ConvertFailedException();
    }
}
