package com.hao.pojo;

import lombok.Data;

/**
 * Demo class
 *
 * @author haozhang
 * @date 2019/09/19
 */
@Data
public class Student {
    private int id;
    private String name;

    /**
     * 学生需要关联一个老师
     */
    private Teacher teacher;
}
