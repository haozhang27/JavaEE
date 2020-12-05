package com.hao.dao;

import com.hao.pojo.User;

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
}
