package com.hao.pojo;

import lombok.Data;

import java.util.Date;

/**
 * Demo class
 *
 * @author haozhang
 * @date 2019/09/19
 */
@Data
public class Blog {
    private String id;
    private String title;
    private String author;
    private Date createTime;
    private int views;
}
