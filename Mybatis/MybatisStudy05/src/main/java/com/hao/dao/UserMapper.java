package com.hao.dao;

import com.hao.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author haozhang
 */
public interface UserMapper {

    @Select("select * from user")
    List<User> getUsers();

    /**
     * 方法存在多个参数，所有参数前都加@Param（基本数据类型参数）
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id")int id);

    @Insert("insert into user(id, name, pwd) values (#{id}, #{name}, #{password})")
    int addUser(User user);

    @Update("update user set name=#{name}, pwd=#{password} where id=#{id}")
    int updateUser(User user);

    @Delete("delete from user where id=#{uid}")
    int deleteUser(@Param("uid") int id);
}
