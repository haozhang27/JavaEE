package com.hao.dao;

import com.hao.pojo.User;
import com.hao.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demo class
 *
 * @author haozhang
 * @date 2019/09/19
 */
public class UserMapperTest {
    @Test
    public void testGetUserList() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //执行sql
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        userMapper.deleteUser(6);

        sqlSession.commit();

        sqlSession.close();
    }
}
