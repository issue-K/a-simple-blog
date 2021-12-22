package com.cl.smyblog.service;

import com.cl.smyblog.util.PageQueryUtil;
import com.cl.smyblog.util.PageResult;

public interface TagService {
    boolean saveTag(String tagName);

    PageResult getTagPage(PageQueryUtil pageQueryUtil);

    boolean deleteBatch(Integer[] ids);
}
