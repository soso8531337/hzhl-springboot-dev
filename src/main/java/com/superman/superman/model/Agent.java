package com.superman.superman.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/** 代理表
 * Created by liujupeng on 2018/11/23.
 */
@Data
public class Agent {
    private Integer id;
    private Integer userId;
    private Integer agentId;
    private String agentName;
    private String userName;
    private Date createTime;
    private Date updateTime;
}
