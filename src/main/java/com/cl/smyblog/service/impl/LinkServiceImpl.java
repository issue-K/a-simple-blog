package com.cl.smyblog.service.impl;


import com.cl.smyblog.dao.BlogLinkMapper;
import com.cl.smyblog.entity.BlogLink;
import com.cl.smyblog.service.LinkService;
import com.cl.smyblog.util.PageQueryUtil;
import com.cl.smyblog.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private BlogLinkMapper blogLinkMapper;

    @Override
    public PageResult getBlogLinkPage(PageQueryUtil pageUtil) {
        List<BlogLink> li = blogLinkMapper.getBlogLinkList(pageUtil);
        int total = blogLinkMapper.getTotal();
        return new PageResult( li,total,(int)pageUtil.get("limit"),(int)pageUtil.get("page") );
    }

    @Override
    public boolean saveLink(BlogLink link) {
        return blogLinkMapper.insertLink( link );
    }

    @Override
    public boolean deleteBatch(Integer[] ids) {
        return blogLinkMapper.deleteBatch( ids );
    }

    @Override
    public boolean updateLink(BlogLink blogUpdLink) {
        return blogLinkMapper.updateBlogLink(blogUpdLink)>0;
    }

    @Override
    public BlogLink selectById(Integer id) {
        return blogLinkMapper.selectById( id );
    }
}
