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
    public void test() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //执行sql
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.getUserList();

        for (User user : userList) {
            System.out.println(user);
        }

        sqlSession.close();
    }

    @Test
    public void testGetUserLike() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> list = mapper.getUserLike("%李%");

        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void testGetUserById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.getUserById(1);
        System.out.println(user);

        sqlSession.close();
    }

    @Test
    public void testGetUserById2() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        Map<String, Object> map = new HashMap<>(16);
        map.put("userId", 1);
        User user = mapper.getUserById2(map);
        System.out.println(user);
    }

    /**
     * 增删改需要提交事务
     */
    @Test
    public void testAddUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        int ret = userMapper.addUser(new User(4, "胡", "123"));
        if (ret > 0) {
            System.out.println("插入成功");
        }

        //提交事务
        sqlSession.commit();

        sqlSession.close();

    }

    @Test
    public void testInsertUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        HashMap<String, Object> map = new HashMap<>(16);
        map.put("userId", 4);
        map.put("userName", "hello");
        map.put("password", "222");

        int res = mapper.insertUser(map);
        if (res > 0) {
            System.out.println("插入成功");
        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testUpdateUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        int ret = mapper.updateUser(new User(4, "hu", "1222"));
        if (ret > 0) {
            System.out.println("修改成功");
        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDeleteUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        int ret = mapper.deleteUser(4);
        if (ret > 0) {
            System.out.println("删除成功");
        }

        sqlSession.commit();
        sqlSession.close();
    }
}
