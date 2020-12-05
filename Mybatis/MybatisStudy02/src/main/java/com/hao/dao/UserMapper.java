package com.hao.dao;

import com.hao.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author haozhang
 */
public interface UserMapper {
    /**
     * 查询全部用户
     * @return
     */
    List<User> getUserList();

    /**
     * 根据Id查询用户
     * @param id
     * @return
     */
    User getUserById(int id);

    /**
     * 插入一个用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 修改一个用户
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 删除一个用户
     * @param id
     * @return
     */
    int deleteUser(int id);


}
