package com.aspect;

import com.Exception.GoodsException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class APIMonitor {

    //存储每个API被调用的次数
    Map<String, Integer> APICallingTime=new HashMap<>();
    //存储每个API运行的时长
    Map<String, Time> APIProceedingTime=new HashMap<>();
    //存储每个API发生异常的次数
    Map<String, Integer> APIExceptionTime=new HashMap<>();
    Logger logger= LoggerFactory.getLogger(getClass());

    @Before("within(com.controller.*)")
    public void statisticCallingTimes(JoinPoint jp)
    {
        String APIName=jp.getSignature().toString();
        if(APICallingTime.get(APIName)==null)
        {
            APICallingTime.put(APIName,1);
        }
        else
        {
            int times=APICallingTime.get(APIName);
            times+=1;
            APICallingTime.put(APIName,times);
        }
    }

    @Around("within(com.controller.*)")
    public Object statisticProceedingTimes(ProceedingJoinPoint jp) throws Throwable {
        long tb = Calendar.getInstance().getTimeInMillis();
        Object re = jp.proceed();
        long ta = Calendar.getInstance().getTimeInMillis();
        String APIName=jp.getSignature().toString();
        long t=ta-tb;
        if(APIProceedingTime.get(APIName)==null)
        {
            Time time=new Time(t,t,t,t,1);
            APIProceedingTime.put(APIName,time);
        }
        else
        {
            Time time=APIProceedingTime.get(APIName);
            if(time.getLongestTime()<t)
            {
                time.setLongestTime(t);
            }
            if(time.getLowestTime()>t)
            {
                time.setLowestTime(t);
            }
            long tSum = time.getSumTime();
            tSum += t;
            time.setSumTime(tSum);
            int total = time.getTotal();
            total+=1;
            double tAverage=tSum/total;
            time.setTotal(total);
            time.setAverageTime(tAverage);
            APIProceedingTime.put(APIName,time);
        }
        return re;
    }

    @AfterThrowing("within(com.controller.*)")
    public void statisticExceptionTime(JoinPoint jp)
    {
        String APIName=jp.getSignature().toString();
        if(APIExceptionTime.get(APIName)==null)
        {
            APIExceptionTime.put(APIName,1);
        }
        else
        {
            int times =APIExceptionTime.get(APIName);
            times+=1;
            APIExceptionTime.put(APIName,times);
        }
    }

    @After("within(com.controller.*)")
    public void log()
    {
        logger.info("API调用次数表");
        for(String key:APICallingTime.keySet())
        {
            logger.info(key+":"+APICallingTime.get(key));
        }
        logger.info("API运行时长信息");
        for(String key:APIProceedingTime.keySet())
        {
            logger.info(key+":"+APIProceedingTime.get(key));
        }
        logger.info("API发生异常信息");
        for(String key:APIExceptionTime.keySet())
        {
            logger.info(key+":"+APIExceptionTime.get(key));
        }
    }

}


@AllArgsConstructor
@Data
class Time{
    long longestTime;
    long lowestTime;
    double averageTime;
    long sumTime;
    int total;
}
