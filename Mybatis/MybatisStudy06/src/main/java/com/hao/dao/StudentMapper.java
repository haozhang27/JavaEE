package com.hao.dao;

import com.hao.pojo.Student;

import java.util.List;

public interface StudentMapper {

    /**
     * 查询所有的学生信息，以及对应的老师的信息
     */
    List<Student> getStudentList();

    List<Student> getStudentList2();
}
