package com.cl.smyblog.service;

import com.cl.smyblog.entity.Blog;
import com.cl.smyblog.util.PageQueryUtil;
import com.cl.smyblog.util.PageResult;

public interface BlogService {
    String saveBlog(Blog blog);

    PageResult getBlogPage(PageQueryUtil pageQueryUtil);

    Blog getBlogById(Long blogId);

    String updateBlog(Blog blog);

    boolean deleteBatch(Integer[] ids);
}