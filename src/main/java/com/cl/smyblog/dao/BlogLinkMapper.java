package com.cl.smyblog.dao;

import com.cl.smyblog.entity.BlogLink;
import com.cl.smyblog.util.PageQueryUtil;

import java.util.List;

public interface BlogLinkMapper {
    List<BlogLink> getBlogLinkList(PageQueryUtil pageUtil);

    int getTotal();

    boolean insertLink(BlogLink link);

    boolean deleteBatch(Integer[] ids);

    BlogLink selectById(Integer id);

    int updateBlogLink(BlogLink blogUpdLink);
}
