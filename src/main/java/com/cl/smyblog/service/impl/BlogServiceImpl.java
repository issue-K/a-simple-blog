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
import com.cl.smyblog.util.PageQueryUtil;
import com.cl.smyblog.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Blog getBlogById(Long blogId) {
        return blogMapper.selectById( blogId );
    }

    @Override
    public PageResult getBlogPage(PageQueryUtil pageQueryUtil) {
        List<Blog>blogs = blogMapper.findBlogList((int)pageQueryUtil.get("start"),(int)pageQueryUtil.get("limit"));
        int total = blogMapper.getTotal();
        PageResult pageResult = new PageResult( blogs,total,(int)pageQueryUtil.get("start"),(int)pageQueryUtil.get("limit") );
        return pageResult;
    }

    @Override
    public boolean deleteBatch(Integer[] ids) {//批量删除博客
        int cas = 0;
        for(int i=0;i<ids.length;i++){
            cas += blogMapper.deleteBlogById( (long)ids[i] );
        }
        return cas>0;
    }

    @Override
    @Transactional
    public String updateBlog(Blog blog) {
        Blog blogForUpdate = blogMapper.selectById(blog.getBlogId());//查询原来的博客
        if (blogForUpdate == null) {
            return "数据不存在";
        }
        blogForUpdate.setBlogTitle(blog.getBlogTitle());
        blogForUpdate.setBlogSubUrl(blog.getBlogSubUrl());
        blogForUpdate.setBlogContent(blog.getBlogContent());
        blogForUpdate.setBlogCoverImage(blog.getBlogCoverImage());
        blogForUpdate.setBlogStatus(blog.getBlogStatus());
        blogForUpdate.setEnableComment(blog.getEnableComment());//设置新的值

        BlogCategory blogCategory = blogCategoryMapper.selectById(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blogForUpdate.setBlogCategoryId(0);
            blogForUpdate.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blogForUpdate.setBlogCategoryName(blogCategory.getCategoryName());
            blogForUpdate.setBlogCategoryId(blogCategory.getCategoryId());
            //分类的排序值加1
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量限制为6";
        }
        blogForUpdate.setBlogTags(blog.getBlogTags());
        //新增的tag对象
        List<Tag> tagListForInsert = new ArrayList<>();
        //所有的tag对象，用于建立关系数据
        List<Tag> allTagsList = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            Tag tag = tagMapper.selectByTagName(tags[i]);
            if (tag == null) {
                //不存在就新增
                Tag tempTag = new Tag();
                tempTag.setTagName(tags[i]);
                tagListForInsert.add(tempTag);
            } else {
                allTagsList.add(tag);
            }
        }
        //新增标签数据不为空->新增标签数据
        if (!CollectionUtils.isEmpty(tagListForInsert)) {
            tagMapper.insertTagList(tagListForInsert);
        }
        List<BlogTagRelation> blogTagRelations = new ArrayList<>();
        //新增关系数据
        allTagsList.addAll(tagListForInsert);
        for ( Tag tag : allTagsList) {
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(tag.getTagId());
            blogTagRelations.add(blogTagRelation);
        }
        //修改blog信息->修改分类排序值->删除原关系数据->保存新的关系数据
        blogCategoryMapper.updateById(blogCategory);
        //删除原关系数据
        blogTagRelationMapper.deleteByBlogId(blog.getBlogId());
        blogTagRelationMapper.insertBatch(blogTagRelations);
        blogMapper.deleteBlogById( blog.getBlogId() );
        if (blogMapper.insertBlog(blogForUpdate) > 0) {
            return "success";
        }
        return "修改失败";
    }

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
