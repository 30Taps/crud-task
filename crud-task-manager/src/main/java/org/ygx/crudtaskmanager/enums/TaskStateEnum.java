package org.ygx.crudtaskmanager.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 运行状态的枚举类
 * 暂不使用
 * @author ygx
 */

public enum TaskStateEnum implements IEnum<Integer> {

    /**
     * 运行中
     */
    Running(1,"Running"),
    /**
     * 已停止
     */
    Stopped(0,"Stopped");

    private final int value;
    private final String desc;

    TaskStateEnum(int value,String desc){
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
