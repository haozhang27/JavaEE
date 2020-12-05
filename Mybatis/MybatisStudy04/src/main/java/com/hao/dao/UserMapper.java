package com.hao.dao;

import com.hao.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author haozhang
 */
public interface UserMapper {

    /**
     * 根据Id查询用户
     * @param id
     * @return
     */
    User getUserById(int id);

    /**
     * 分页实现
     * @param map
     * @return
     */
    List<User> getUserByLimit(Map<String, Integer> map);
}
