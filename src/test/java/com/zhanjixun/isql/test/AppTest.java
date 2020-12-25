package com.zhanjixun.isql.test;

import com.zhanjixun.isql.builder.TableSQLBuilder;
import com.zhanjixun.isql.test.entity.Student;
import org.junit.Test;

/**
 * @author zhanjixun
 * @date 2020-11-21 14:44:03
 */
public class AppTest {

    @Test
    public void name1() {
        System.out.println(TableSQLBuilder.buildCreateSQL(Student.class));
    }
    
}
