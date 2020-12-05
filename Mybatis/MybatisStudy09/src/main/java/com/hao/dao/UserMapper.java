package com.hao.dao;

import com.hao.pojo.User;
import org.apache.ibatis.annotations.Param;


/**
 * @author haozhang
 */
public interface UserMapper {
    /**
     *
     * @param id
     * @return
     */
    User queryUserById(@Param("id") int id);

    int updateUser(User user);
}
