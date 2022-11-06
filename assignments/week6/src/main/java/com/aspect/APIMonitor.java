package com.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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

    @Before("within(com.controller)")
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

    @Around("within(com.controller)")
    public void statisticProceedingTimes(ProceedingJoinPoint jp) throws Throwable {
        long tb = Calendar.getInstance().getTimeInMillis();
        try{
            jp.proceed();
        }
        catch(Exception ex)
        {
            statisticExceptionTime(jp.getSignature().toString());
        }
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
    }

    public void statisticExceptionTime(String name)
    {
        if(APIExceptionTime.get(name)==null)
        {
            APIExceptionTime.put(name,1);
        }
        else
        {
            int times =APIExceptionTime.get(name);
            times+=1;
            APIExceptionTime.put(name,times);
        }
    }

    @After("within(com.controller)")
    public void log()
    {
        logger.info("API调用次数表/n");
        for(String key:APICallingTime.keySet())
        {
            logger.info(key+":"+APICallingTime.get(key)+"/n");
        }
        logger.info("API运行时长信息/n");
        for(String key:APIProceedingTime.keySet())
        {
            logger.info(key+":"+APIProceedingTime.get(key)+"/n");
        }
        logger.info("API发生异常信息/n");
        for(String key:APIExceptionTime.keySet())
        {
            logger.info(key+":"+APIExceptionTime.get(key)+"/n");
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
