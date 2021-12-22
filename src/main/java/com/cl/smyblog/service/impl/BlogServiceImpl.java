package com.cl.smyblog.service.impl;


import com.cl.smyblog.dao.BlogCategoryMapper;
import com.cl.smyblog.dao.BlogMapper;
import com.cl.smyblog.dao.BlogTagRelationMapper;
import com.cl.smyblog.dao.TagMapper;
import com.cl.smyblog.entity.Blog;
import com.cl.smyblog.entity.BlogCategory;
import com.cl.smyblog.entity.BlogTagRelation;
import com.cl.smyblog.entity.Tag;
import com.cl.smyblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogCategoryMapper blogCategoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;

    @Override
    public String saveBlog(Blog blog) {
        BlogCategory blogCategory = blogCategoryMapper.selectById( blog.getBlogCategoryId() );
        if( blogCategory == null ){
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        }else{
            blog.setBlogCategoryName(blogCategory.getCategoryName() );
            blogCategory.setCategoryRank( blogCategory.getCategoryRank()+1 );
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if( tags.length>6 ){
            return "标签数量限制为6";
        }
        if( blogMapper.insertBlog(blog)>0 ){
            List<Tag> taglist = new ArrayList<>();//新增的
            List<Tag> allTagList = new ArrayList<>();
            for(int i=0;i<tags.length;i++){
                Tag tag = tagMapper.selectByTagName( tags[i] );
                if( tag==null ){//不存在此标签就新增一个
                    tag = new Tag();
                    tag.setTagName( tags[i] );
                    taglist.add( tag );
                }else{
                    allTagList.add( tag );
                }
            }
            if( !CollectionUtils.isEmpty(taglist) ){//存在新增
                tagMapper.insertTagList( taglist );
            }
            blogCategoryMapper.updateById( blogCategory );//更新引用次数
            //新增(博客-标签)关系数据
            List<BlogTagRelation> li = new ArrayList<>();
            allTagList.addAll( taglist );
            for(Tag tag:allTagList ){
                BlogTagRelation blogTagRelation = new BlogTagRelation();
                blogTagRelation.setTagId( tag.getTagId() );
                blogTagRelation.setBlogId( blog.getBlogId() );
                li.add( blogTagRelation );
            }
            if( blogTagRelationMapper.insertBatch(li)>0 ){
                return "success";
            }
        }
        return "保存失败";
    }
}
