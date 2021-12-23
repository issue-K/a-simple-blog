package com.cl.smyblog.service;

import com.cl.smyblog.entity.BlogLink;
import com.cl.smyblog.util.PageQueryUtil;
import com.cl.smyblog.util.PageResult;

public interface LinkService {
    PageResult getBlogLinkPage(PageQueryUtil pageUtil);

    boolean saveLink(BlogLink link);

    boolean deleteBatch(Integer[] ids);

    BlogLink selectById(Integer id);

    boolean updateLink(BlogLink blogLink);
}
