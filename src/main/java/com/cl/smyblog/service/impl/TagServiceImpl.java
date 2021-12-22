package com.cl.smyblog.service.impl;

import com.cl.smyblog.dao.TagMapper;
import com.cl.smyblog.entity.Tag;
import com.cl.smyblog.service.TagService;
import com.cl.smyblog.util.PageQueryUtil;
import com.cl.smyblog.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;
    
    @Override
    public boolean saveTag(String tagName) {
        Tag temp = tagMapper.selectByTagName( tagName );
        if( temp==null ){
            Tag tag = new Tag();
            tag.setTagName( tagName );
            return tagMapper.insertTag( tag )>0;
        }
        return false;
    }

    @Override
    public PageResult getTagPage(PageQueryUtil pageQueryUtil) {
        int totalCount = tagMapper.getTotal();
        List<Tag> li = tagMapper.selectTagPage( (int)pageQueryUtil.get("start"),(int)pageQueryUtil.get("limit") );
        return new PageResult( li,totalCount,(int)pageQueryUtil.get("limit"),(int)pageQueryUtil.get("page") );
    }

    @Override
    public boolean deleteBatch(Integer[] ids) {
        if( tagMapper.deleteTagList(ids)>0 ){
            return true;
        }else{
            return false;
        }
    }
}
