package com.hao.dao;

import com.hao.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {

    int addBlog(Blog blog);

    List<Blog> queryBlogIF(Map map);

    List<Blog> queryBlogChoose(Map map);

    int updateBlog(Map map);

    //查询第1-2-3记录博客
    List<Blog> queryForeach(Map map);
}
