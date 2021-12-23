package com.cl.smyblog.dao;

import com.cl.smyblog.entity.Blog;
import com.cl.smyblog.util.PageQueryUtil;

import java.util.List;

public interface BlogMapper {


    int insertBlog(Blog blog);

    int getTotal();

    List<Blog> findBlogList(int start, int limit);

    Blog selectById(Long blogId);

    int deleteBlogById(Long blogId);
}