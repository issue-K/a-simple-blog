package com.cl.smyblog.dao;

import com.cl.smyblog.entity.Tag;

import java.util.List;

public interface TagMapper {


    Tag selectByTagName(String tagName);

    int insertTag(Tag tag);

    Integer getTotal();

    List<Tag> selectTagPage(int start, int limit);

    int deleteTagList(Integer[] ids);

    void insertTagList(List<Tag> taglist);
}
