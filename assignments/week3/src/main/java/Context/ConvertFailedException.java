package Context;

public class ConvertFailedException extends Exception{
    @Override
    public String toString()
    {
        return "类型转换失败";
    }
}
