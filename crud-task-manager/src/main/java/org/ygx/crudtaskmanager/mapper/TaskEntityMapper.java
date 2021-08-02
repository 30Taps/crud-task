package org.ygx.crudtaskmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ygx.crudtaskmanager.module.TaskEntity;

import java.util.List;

/**
 * 包含mp基本crud
 *
 * @author ygx
 */
@Mapper
public interface TaskEntityMapper extends BaseMapper<TaskEntity> {

    /**
     * 条件查找
     * @param condition 条件字符串
     * @return 任务名或组名符合条件的任务
     */
    List<TaskEntity> findByCondition(String condition);

    /**
     * 查找同组下同名的任务
     * @param taskEntity
     * @return 同组下同名的任务
     */
    TaskEntity findSameName(TaskEntity taskEntity);
}