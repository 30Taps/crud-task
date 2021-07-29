package org.ygx.crudtaskmanager.job;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.ygx.crudtaskmanager.module.TaskEntity;
import org.ygx.crudtaskmanager.service.TaskEntityServiceImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * quartz对任务的操作工具类
 * @author ygx
 */
@Slf4j
@Service
@MapperScan({"org.ygx.crudtaskmanager.module.TaskEntity",
             "org.ygx.crudtaskmanager.job.JobSeri"})
public class JobService {

    @Autowired
    TaskEntityServiceImpl taskEntityService;

    /**
     * 新增并启动任务
     * @param taskEntity 目标任务
     */
    public void addJob(Scheduler scheduler,TaskEntity taskEntity) throws Exception {

        System.out.println(taskEntity.getMethodName());
        Class cls = Class.forName("org.ygx.crudtaskmanager.job.JobSeri");
        cls.getDeclaredConstructor().newInstance();
        //构建job新信息
        JobDetail jobDetail = JobBuilder.newJob(cls)
                .withIdentity(taskEntity.getTaskName(), taskEntity.getGroupName())
                .withDescription(taskEntity.getDescription())
                .build();
        jobDetail.getJobDataMap().put("methodName", taskEntity.getMethodName());
        //触发时间点
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(taskEntity.getCronExpression());
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger"+taskEntity.getTaskName(), taskEntity.getGroupName())
                .startNow()
                .withSchedule(cronScheduleBuilder)
                .build();
        //交由Scheduler安排触发
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("任务已启动");
    }

    /**
     * 移除任务
     * @param taskEntity 目标任务
     */
    public void removeJob(Scheduler scheduler,TaskEntity taskEntity){
        try {
            JobKey jobKey = JobKey.jobKey(taskEntity.getTaskName(),taskEntity.getGroupName());
            TriggerKey triggerKey = TriggerKey.triggerKey(taskEntity.getTaskName(),taskEntity.getGroupName());
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 停止任务
     */
    public void pauseJob(Scheduler scheduler,TaskEntity taskEntity) {
        try {
            JobKey key = new JobKey(taskEntity.getTaskName(),taskEntity.getGroupName());
            scheduler.pauseJob(key);
            taskEntityService.setProperty(taskEntity,0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动任务（已存在）
     */
    public void resumeJob(Scheduler scheduler,TaskEntity taskEntity) {
        try {
            JobKey key = new JobKey(taskEntity.getTaskName(),taskEntity.getGroupName());
            scheduler.resumeJob(key);
            taskEntityService.setProperty(taskEntity,1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
