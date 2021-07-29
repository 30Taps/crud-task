package org.ygx.crudtaskmanager.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Job 的实例要到该执行它们的时候才会实例化出来。每次 Job 被执行，一个新的 Job 实例会被创建。
 * 其中暗含的意思就是你的 Job 不必担心线程安全性，因为同一时刻仅有一个线程去执行给定 Job 类的实例，甚至是并发执行同一 Job 也是如此。
 * @author ygx
 * @DisallowConcurrentExecution 保证上一个任务执行完后，再去执行下一个任务
 */
@DisallowConcurrentExecution
public class JobSeri implements Job, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void execute(JobExecutionContext context){
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap dataMap = jobDetail.getJobDataMap();
        /**
         * 获取任务中保存的方法名字，动态调用方法
         */
        String methodName = dataMap.getString("methodName");
        try {
            JobSeri job = new JobSeri();
            Method method = job.getClass().getMethod(methodName);
            method.invoke(job);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void test1(){
        System.out.println("测试方法1");
    }
    public void test2(){
        System.out.println("测试方法2");
    }
    public void test3(){
        System.out.println("测试方法3");
    }
}

