package com.zhanjixun.isql.test.entity;


import com.zhanjixun.isql.annotation.*;
import lombok.Data;

/**
 * @author zhanjixun
 * @date 2020-07-03 11:37:24
 */
@Data
@Table(prefix = "tb_", name = "student", comment = "学生表")
@Index(name = "idx_student_no", columnList = "studentNo", type = IndexType.UNIQUE, method = IndexMethod.BTREE, comment = "学号索引")
public class Student {

    @Column(length = 11, primaryKey = true)
    private int id;

    @Column(length = 32, comment = "学号")
    private String studentNo;

}
