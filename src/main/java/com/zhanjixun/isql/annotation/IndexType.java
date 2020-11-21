package com.zhanjixun.isql.annotation;

import lombok.Getter;

/**
 * 索引类型
 *
 * @author :zhanjixun
 * @date : 2019/12/20 09:57
 * @contact :zhanjixun@qq.com
 */
public enum IndexType {
    /**
     * 正常索引
     */
    NORMAL(""),
    /**
     * 唯一索引
     */
    UNIQUE("UNIQUE"),
    /**
     * 全文索引
     */
    FULL_TEXT("FULLTEXT");

    IndexType(String name) {
        this.name = name;
    }

    @Getter
    private String name;

}
