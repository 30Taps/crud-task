package org.ygx.crudtaskmanager.controller;

import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.ygx.crudtaskmanager.module.TaskEntity;
import org.ygx.crudtaskmanager.service.TaskEntityServiceImpl;
import org.ygx.crudtaskmanager.job.JobService;

import java.util.List;

/**
 * 控制层
 * @author ygx
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskEntityServiceImpl taskEntityService;
    @Autowired
    private JobService jobService;
    @Autowired
    private Scheduler scheduler;

    /**
     * 显示任务列表
     *
     * @return 所有的任务集合
     */
    @PostMapping("/showList")
    public List<TaskEntity> showList() {
        LOGGER.info("展示全部任务");
        return (taskEntityService.findAll());
    }

    /**
     * 查找任务
     * 输入任务名或组名进行模糊查找
     *
     * @param condition 条件，先搜任务名再搜组名
     * @return 查询结果集
     */
    @PostMapping("/select")
    public List<TaskEntity> selectList(String condition) {
        LOGGER.info("查找结果如下");
        return (taskEntityService.findByCondition(condition));
    }

    /**
     * 任务新增
     * 数据库和任务同步新增
     * @param taskEntity 新增实体对象
     *                   接入前端后对象由前端传入
     * @return 成功或失败
     */
    @PostMapping("/insert")
    public boolean insert(@RequestBody @Validated TaskEntity taskEntity) {
        LOGGER.info("正在新增任务...");
        //没通过参数校验
        if(!taskEntityService.paramVerify(taskEntity)) {
            System.out.println("没通过参数校验");
            return false;
        }
        else {
            //数据库里新增一条成功
            if (taskEntityService.insert(taskEntity)){
                try{
                    //增加并启动一个job
                    jobService.addJob(scheduler,taskEntity);
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 任务删除
     * 数据库和任务同步删除
     * @param taskEntity 删除对象
     *                   接入前端后对象由前端传入
     * @return 成功或失败
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody @Validated TaskEntity taskEntity) {
        LOGGER.info("正在删除任务...");
        try {
            jobService.removeJob(scheduler,taskEntity);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return (taskEntityService.delete(taskEntity));
    }

    /**
     * 任务更新
     *
     * @param taskEntity 待更新任务
     * @return 成功或失败
     */
    @PostMapping("/update")
    @Transactional
    public boolean update(@RequestBody @Validated TaskEntity taskEntity){
        LOGGER.info("正在更新任务...");
        TaskEntity target = taskEntityService.find(taskEntity.getOldTaskName(), taskEntity.getOldGroupName());
        return (delete(target) && insert(taskEntity));
    }

    /**
     * 任务启动
     * @param taskEntity 目标任务
     */
    @PostMapping("/start")
    public void start(@RequestBody @Validated TaskEntity taskEntity){
        TaskEntity target = taskEntityService.find(taskEntity.getTaskName(), taskEntity.getGroupName());
        jobService.resumeJob(scheduler, target);
        LOGGER.info("任务已启动！");

    }

    /**
     * 任务停止
     * @param taskEntity 目标任务
     */
    @PostMapping("/pause")
    public void pause(@RequestBody @Validated TaskEntity taskEntity){
        TaskEntity target = taskEntityService.find(taskEntity.getTaskName(), taskEntity.getGroupName());
        jobService.pauseJob(scheduler, target);
        LOGGER.info("任务已停止");
    }

}
