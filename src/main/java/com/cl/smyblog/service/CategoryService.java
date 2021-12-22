package com.cl.smyblog.service;

import com.cl.smyblog.util.PageQueryUtil;
import com.cl.smyblog.util.PageResult;

public interface CategoryService {
    boolean saveCategory(String categoryName, String categoryIcon);

    PageResult getBlogCategoryPage(PageQueryUtil pageUtil);

    boolean deleteBatch(Integer[] ids);
}