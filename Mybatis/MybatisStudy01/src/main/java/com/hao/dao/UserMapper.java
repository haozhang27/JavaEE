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

    List<User> getUserLike(String val);

    /**
     * 根据Id查询用户
     * @param id
     * @return
     */
    User getUserById(int id);

    User getUserById2(Map<String, Object> map);

    /**
     * 插入一个用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 插入用户
     * @param map
     * @return
     */
    int insertUser(Map<String, Object> map);


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
