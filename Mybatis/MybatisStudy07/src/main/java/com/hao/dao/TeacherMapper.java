package com.hao.dao;

import com.hao.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author haozhang
 */
public interface TeacherMapper {

    /**
     * 获取老师
     * @return
     */
    List<Teacher> getTeacherList();

    /**
     * 获取指定老师下的所有学生及老师的信息
     */
    Teacher getTeacher(int id);

    Teacher getTeacher2(int id);
}
