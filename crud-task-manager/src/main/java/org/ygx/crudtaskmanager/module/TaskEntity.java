package org.ygx.crudtaskmanager.module;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;


/**
 * 由CodeHelper自动生成
 * 继承Model类，使用ActiveRecord
 * @author ygx
 */
@Data
@TableName(value = "taskentity")
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity extends Model<TaskEntity> {

    /**
     * id 自增
     */
    @TableId(value = "ID", type = IdType.AUTO)
    @TableField(select = false)
    @Version
    private Integer id;
    /**
     * 任务名称
     */
    @NotBlank(message = "任务名不能为空！")
    @TableField(value = "TASK_NAME")
    private String taskName;
    /**
     * 所在组名
     */
    @NotBlank(message = "组名不能为空！")
    @TableField(value = "GROUP_NAME")
    private String groupName;
    /**
     * 描述
     */
    @TableField(value = "DESCRIPTION",fill = FieldFill.INSERT_UPDATE)
    private String description;
    /**
     * 使用方法
     */
    @NotBlank(message = "方法名不能为空！")
    @TableField(value = "METHOD_NAME")
    private String methodName;
    /**
     * 时间表达式
     */
    @NotNull(message = "时间不能为空！")
    @TableField(value = "CRON_EXPRESSION")
    private String cronExpression;
    /**
     * 任务状态。0为停止1为运行中
     */
    @TableField(value = "TASK_STATE",fill = FieldFill.INSERT)
    private Integer taskState;
    /**
     * 任务名称 用于修改
     */
    @TableField(value = "OLDTASK_NAME")
    private String oldTaskName;
    /**
     * 组名 用于修改
     */
    @TableField(value = "OLDGROUP_NAME")
    private String oldGroupName;

}