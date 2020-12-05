package com.hao.dao;

import com.hao.pojo.Teacher;
import com.hao.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * Demo class
 *
 * @author haozhang
 * @date 2019/09/19
 */
public class TestTeacher {

    @Test
    public void testGetTeacherList() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

        Teacher teacher = mapper.getTeacher2(1);

        System.out.println(teacher);

        sqlSession.close();
    }
}
