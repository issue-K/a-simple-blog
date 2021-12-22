package com.cl.smyblog.dao;

import com.cl.smyblog.entity.BlogTagRelation;

import java.util.List;

public interface BlogTagRelationMapper {
    int insertBatch(List<BlogTagRelation> li);
}
