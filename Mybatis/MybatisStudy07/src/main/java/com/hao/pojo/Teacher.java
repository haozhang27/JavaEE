package com.hao.pojo;

import lombok.Data;

import java.util.List;

/**
 * Demo class
 *
 * @author haozhang
 * @date 2019/09/19
 */
@Data
public class Teacher {
    private int id;
    private String name;

    /**
     * 一个老师对应多个学生
     */
    private List<Student> students;
}
