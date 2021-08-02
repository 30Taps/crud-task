package org.ygx.crudtaskmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ygx.crudtaskmanager.module.TaskEntity;
import org.ygx.crudtaskmanager.mapper.TaskEntityMapper;

import java.util.List;

/**
 * 任务实体的service层
 * 负责增删改查
 * @author ygx
 */
@Service
public class TaskEntityServiceImpl extends ServiceImpl<TaskEntityMapper, TaskEntity> implements TaskService{

    @Autowired
    TaskEntityMapper taskEntityMapper;
    /**
     * 参数校验.
     * <p>组名下有没有同名的任务<br />
     * 各种字段是否都合法
     * </p>
     *
     * @param taskEntity 目标对象
     * @return 检验结果
     */
    @Override
    public boolean paramVerify(TaskEntity taskEntity) {
        /**
         * 暂时只判定同组下是否有同名任务和cron表达式是否正确
         */
        boolean flag = true;
        if (this.taskEntityMapper.findSameName(taskEntity) != null ||
                !CronExpression.isValidExpression(taskEntity.getCronExpression())){
            flag = false;
        }
        return flag;
    }

    /**
     * 查询所有
     *
     * @return 查询结果
     */
    @Override
    public List<TaskEntity> findAll() {
        return taskEntityMapper.selectList(null);
    }

    /**
     * 条件查询
     *
     * @param condition 查询条件
     * @return 查询结果集
     */
    @Override
    public List<TaskEntity> findByCondition(String condition) {
        List<TaskEntity>taskEntities = this.taskEntityMapper.findByCondition(condition);
        return taskEntities;
    }

    /**
     * 增
     *
     * @param taskEntity 新增对象
     * @return 成功或失败
     */
    @Override
    public boolean insert(TaskEntity taskEntity) {
        //先插入字段
        if (this.taskEntityMapper.insert(taskEntity) != 0 ){
            //给old字段同步当前任务名和组名
            TaskEntity target = new TaskEntity();
            target.setId(taskEntity.getId());
            target.setOldTaskName(taskEntity.getTaskName());
            target.setOldGroupName(taskEntity.getGroupName());
            if (this.taskEntityMapper.updateById(target) != 0) {
                return true;
            }
        }
            return false;
    }

    /**
     * 删
     *
     * @param taskEntity 删除对象
     * @return 成功或失败
     */
    @Override
    public boolean delete(TaskEntity taskEntity) {
        //查找符合条件的任务
        QueryWrapper<TaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("TASK_NAME",taskEntity.getTaskName())
                .like("GROUP_NAME",taskEntity.getGroupName());
        TaskEntity targetTask = this.taskEntityMapper.selectOne(queryWrapper);
        if (targetTask != null) {
            return (this.taskEntityMapper.delete(queryWrapper) != 0);
        }else {
            System.out.println("该任务不存在！");
            return false;
        }
    }

    /**
     * 精确查找，用于启动或停止
     */
    public TaskEntity find(String taskName, String groupName){
        QueryWrapper<TaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("TASK_NAME",taskName)
                .like("GROUP_NAME",groupName);
        TaskEntity targetTask = this.taskEntityMapper.selectOne(queryWrapper);
        return targetTask;
    }

    /**
     * 修改运行状态
     */
    public void setProperty(TaskEntity taskEntity, Integer state){
        TaskEntity target = new TaskEntity();
        target.setId(taskEntity.getId());
        target.setTaskState(state);
        int rows = this.taskEntityMapper.updateById(target);
    }
}
