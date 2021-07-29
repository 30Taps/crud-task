package org.ygx.crudtaskmanager.service;

import org.springframework.scheduling.config.Task;
import org.ygx.crudtaskmanager.module.TaskEntity;

import java.util.List;

/**
 * service层方法的接口
 * @author ygx
 */
public interface TaskService {

    /**
     * 参数校验
     * @param taskEntity 目标对象
     * @return 是否出现一组中出现同名任务的情况
     */
    boolean paramVerify(TaskEntity taskEntity);
    /**
     * 查询所有
     * @return 查询结果
     */
    List<TaskEntity> findAll();
    /**
     * 条件查询
     * @param condition 查询条件
     * @return 查询结果集
     */
    List<TaskEntity> findByCondition(String condition);
    /**
     * 增
     * @param taskEntity 新增对象
     * @return 成功或失败
     */
    boolean insert(TaskEntity taskEntity);
    /**
     * 删
     * @param taskEntity 删除对象
     * @return 成功或失败
     */
    boolean delete(TaskEntity taskEntity);
}
