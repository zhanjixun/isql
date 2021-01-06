package com.zhanjixun.isql;

import lombok.Data;

/**
 * 连接配置
 *
 * @author zhanjixun
 * @date 2020-12-30 18:07:58
 */
@Data
public class ConnectionConfig {
    /**
     * 主机
     */
    private String host;
    /**
     * 端口号
     */
    private int port;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;

}
