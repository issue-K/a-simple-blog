package com.cl.smyblog.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryUtil extends LinkedHashMap<String, Object> {
    //当前页码
    private int page;
    //每页条数
    private int limit;

    public PageQueryUtil(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());  //第几页开始
        this.limit = Integer.parseInt(params.get("limit").toString());  //查询几条数据
        this.put("start", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }
}
