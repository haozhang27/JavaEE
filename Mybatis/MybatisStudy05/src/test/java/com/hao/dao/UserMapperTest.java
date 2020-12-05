package com.hao.dao;

import com.hao.pojo.User;
import com.hao.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;



/**
 * Demo class
 *
 * @author haozhang
 * @date 2019/09/19
 */
public class UserMapperTest {
    @Test
    public void testGetUsers() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //底层主要应用反射
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);


        mapper.deleteUser(4);

        sqlSession.close();
    }
}
