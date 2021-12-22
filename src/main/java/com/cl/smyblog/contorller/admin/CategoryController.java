package com.cl.smyblog.contorller.admin;


import com.cl.smyblog.service.CategoryService;
import com.cl.smyblog.util.PageQueryUtil;
import com.cl.smyblog.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")//跳转视图
    public String categoryPage(HttpServletRequest request){
        request.setAttribute("path","categories");
        return "admin/category";
    }

    @PostMapping("/categories/save")//新增一个文章分类
    @ResponseBody
    public Result save(@RequestParam("categoryName") String categoryName,
                       @RequestParam("categoryIcon") String categoryIcon) {
        if (StringUtils.isEmpty(categoryName)) {
            return Result.fail(400,"请输入分类名称！");
        }
        if (StringUtils.isEmpty(categoryIcon)) {
            return Result.fail(400,"请选择分类图标！");
        }
        if (categoryService.saveCategory(categoryName, categoryIcon)  ) {
            return Result.success(null);
        } else {
            return Result.fail(400,"分类名称重复");
        }
    }

    @RequestMapping("/categories/list")//分页查询文章分类
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return Result.fail(400,"参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return Result.success(categoryService.getBlogCategoryPage(pageUtil));
    }

    @RequestMapping("/categories/delete")//根据选择的序号批量删除文章分类
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return Result.fail(400,"参数异常！");
        }
        if (categoryService.deleteBatch(ids)) {
            return Result.success(null);
        } else {
            return Result.fail(400,"删除失败");
        }
    }

}
