package com.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    Logger logger= LoggerFactory.getLogger(getClass());

    @Before("execution(public * com.service.*.*(..))")
    public void beforeService(JoinPoint jp)
    {
        String info= "进入"+jp.getSignature()+"方法";
        for(Object arg: jp.getArgs())
        {
            info+="参数： "+arg;
        }
        logger.info(info);
    }

    @After("execution(public * com.service.*.*(..))")
    public void afterService(JoinPoint jp)
    {
        String info="退出"+jp.getSignature()+"方法";
        logger.info(info);
    }
}
