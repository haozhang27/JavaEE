package com.hao.dao;

import com.hao.pojo.Student;
import com.hao.pojo.Teacher;
import com.hao.utils.MybatisUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * Demo class
 *
 * @author haozhang
 * @date 2019/09/19
 */
public class TestTeacherMapper {

    @Test
    public void testGetTeacher() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher(1);
        System.out.println(teacher);

        sqlSession.close();
    }

    @Test
    public void testGetStudentList() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> studentList = mapper.getStudentList2();

        for (Student student : studentList) {
            System.out.println(student);
        }
        sqlSession.close();
    }
}
