package com.cl.smyblog.dao;

import com.cl.smyblog.entity.BlogCategory;

import java.util.List;

public interface BlogCategoryMapper {

    public int insertCategory(BlogCategory blogCategory);

    public BlogCategory selectByName(String categoryName);

    int TotalBlogCategory();

    List<BlogCategory> BlogCategoryPages(int start, int limit);

    int deleteBatch(Integer[] ids);
}
