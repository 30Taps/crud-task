package org.ygx.crudtaskmanager.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 自动填充字段的实现类
 * @author ygx
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //获取taskState的值，为空就填1，不为空就不处理
        Object taskState = getFieldValByName("taskState", metaObject);
        if (null == taskState){
            setFieldValByName("taskState",1,metaObject);
        }
        //获取description的值，为空就填No description，不为空就不处理
        Object description = getFieldValByName("description", metaObject);
        if (null == description){
            setFieldValByName("description","No description",metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //获取description的值，为空就填No description，不为空就不处理
        Object description = getFieldValByName("description", metaObject);
        if (null == description){
            setFieldValByName("description","No description",metaObject);
        }
    }
}
