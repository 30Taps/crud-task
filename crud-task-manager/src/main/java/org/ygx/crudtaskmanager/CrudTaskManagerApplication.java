package org.ygx.crudtaskmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author ygx
 */
@SpringBootApplication
@MapperScan({"org.ygx.crudtaskmanager.mapper",
        "com.baomidou.mybatisplus.core.conditions.query"})
public class CrudTaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudTaskManagerApplication.class, args);
        System.out.println("动态任务管理系统启动");

    }

}
