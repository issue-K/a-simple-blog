package com.cl.smyblog.contorller.admin;

import com.cl.smyblog.service.TagService;
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
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tagPage(HttpServletRequest request){
        request.setAttribute("path","tags");
        return "admin/tag";
    }

    @PostMapping("/tags/save")
    @ResponseBody
    public Result save( @RequestParam("tagName") String tagName){
        if(StringUtils.isEmpty(tagName) ){
            return Result.fail(400,"参数异常!");
        }
        if( tagService.saveTag( tagName ) ){
            return Result.success(null);
        }else{
            return Result.fail(400,"标签名称重复");
        }
    }

    @GetMapping("/tags/list")
    @ResponseBody
    public Result list( @RequestParam Map<String,Object> params){
        if( StringUtils.isEmpty( params.get("page")) || StringUtils.isEmpty( params.get("limit")) ){
            return Result.fail(400,"参数异常");
        }
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        return Result.success( tagService.getTagPage(pageQueryUtil) );
    }

    @PostMapping("/tags/delete")
    @ResponseBody
    public Result delete( @RequestBody Integer[] ids ){//删除一个数组的id
        if( ids.length<1 ){
            return Result.fail(400,"参数异常");
        }
        if( tagService.deleteBatch(ids) ){
            return Result.success( null );
        }else{
            return Result.fail(400,"删除失败");
        }
    }
}
