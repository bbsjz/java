package Context;

public class NoBasicFunctionException extends Exception{
    @Override
    public String toString()
    {
        return "八种基本类型及String的set函数均不符合，漏写set函数或者提供的值不合规";
    }
}
