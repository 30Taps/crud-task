package org.ygx.crudtaskmanager;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ygx.crudtaskmanager.controller.TaskController;
import org.ygx.crudtaskmanager.mapper.TaskEntityMapper;
import org.ygx.crudtaskmanager.module.TaskEntity;
import org.ygx.crudtaskmanager.service.TaskEntityServiceImpl;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
class CrudTaskManagerApplicationTests {

    @Autowired
    TaskEntityServiceImpl taskEntityService;

    @Test
    public void insertTest(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskName("test");
        taskEntity.setGroupName("group1");
        taskEntity.setMethodName("method1");
        taskEntity.setCronExpression("0/7 * * * * ?");
        System.out.println("111"+taskEntity);
        System.out.println(taskEntityService.insert(taskEntity));

    }
}
