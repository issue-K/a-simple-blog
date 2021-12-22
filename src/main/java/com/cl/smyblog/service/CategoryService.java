package com.cl.smyblog.service;

import com.cl.smyblog.entity.BlogCategory;
import com.cl.smyblog.util.PageQueryUtil;
import com.cl.smyblog.util.PageResult;

import java.util.List;

public interface CategoryService {
    boolean saveCategory(String categoryName, String categoryIcon);

    PageResult getBlogCategoryPage(PageQueryUtil pageUtil);

    boolean deleteBatch(Integer[] ids);

    List<BlogCategory> getAllCategories();
}