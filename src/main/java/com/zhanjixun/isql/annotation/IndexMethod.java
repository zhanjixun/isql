package com.zhanjixun.isql.annotation;

/**
 * 索引方法
 *
 * @author zhanjixun
 * @date 2020-12-30 17:42:10
 */
public enum IndexMethod {

    EMPTY(""),

    BTREE("BTREE"),

    HASH("HASH");

    IndexMethod(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

}
