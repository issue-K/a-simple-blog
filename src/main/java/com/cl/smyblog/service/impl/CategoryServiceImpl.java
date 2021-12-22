package com.cl.smyblog.service.impl;

import com.cl.smyblog.dao.BlogCategoryMapper;
import com.cl.smyblog.entity.BlogCategory;
import com.cl.smyblog.service.CategoryService;
import com.cl.smyblog.util.PageQueryUtil;
import com.cl.smyblog.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private BlogCategoryMapper blogCategoryMapper;

    @Override
    public boolean saveCategory(String categoryName, String categoryIcon) {
        if( blogCategoryMapper.selectByName( categoryName )==null ) {
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setCategoryName(categoryName);
            blogCategory.setCategoryIcon(categoryIcon);
            if (blogCategoryMapper.insertCategory(blogCategory) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public PageResult getBlogCategoryPage(PageQueryUtil pageUtil) {
        int totalsize = blogCategoryMapper.TotalBlogCategory();//总共数据
        int pagesize = pageUtil.getLimit();//一页几条数据
        int currentpage = pageUtil.getPage();//当前页
        List<BlogCategory> li = blogCategoryMapper.BlogCategoryPages((int)pageUtil.get("start"),(int)pageUtil.get("limit") );

        return new PageResult(li,totalsize,pagesize,currentpage);
    }

    @Override
    public boolean deleteBatch(Integer[] ids) {
        return blogCategoryMapper.deleteBatch( ids )>0;
    }
}
