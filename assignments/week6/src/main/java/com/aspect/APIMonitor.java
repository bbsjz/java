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
    Map<String, Integer> APICallingTime=new HashMap<>();
    Map<String, Time> APIProceedingTime=new HashMap<>();
    Map<String, Integer> APIExceptionTime=new HashMap<>();
    Logger logger= LoggerFactory.getLogger(getClass());

    @Before("within(com.controller)")
    public void statisticCallingTime(JoinPoint jp)
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
    public void statisticAPITime(ProceedingJoinPoint jp) throws Throwable {
        long tb = Calendar.getInstance().getTimeInMillis();
        jp.proceed();
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
            APIProceedingTime.put(APIName,time);
        }
    }

    @After("within(com.controller)")
    public void log()
    {



    }

    public void print(Map<String,Object> map)
    {
        for(String key:map.keySet())
        {
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
