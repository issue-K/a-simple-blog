package com.cl.smyblog.contorller.admin;


import com.cl.smyblog.entity.Blog;
import com.cl.smyblog.entity.BlogLink;
import com.cl.smyblog.service.LinkService;
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
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/links")
    public String linkPage(HttpServletRequest request){
        request.setAttribute("path","links");
        return "admin/link";
    }

    @GetMapping("/links/list")//分页查询
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return Result.fail(400,"参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return Result.success(linkService.getBlogLinkPage(pageUtil));
    }

    @GetMapping("/links/info/{id}")//友链详情
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        BlogLink link = linkService.selectById(id);
        return Result.success(link);
    }

    /**
     * 友链添加
     */
    @PostMapping("/links/save")//添加记录
    @ResponseBody
    public Result save(@RequestParam("linkType") Integer linkType,
                       @RequestParam("linkName") String linkName,
                       @RequestParam("linkUrl") String linkUrl,
                       @RequestParam("linkRank") Integer linkRank,
                       @RequestParam("linkDescription") String linkDescription) {
        if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription)) {
            return Result.fail(400,"参数异常！");
        }
        BlogLink link = new BlogLink();
        link.setLinkType(linkType.byteValue());
        link.setLinkRank(linkRank);
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        link.setLinkDescription(linkDescription);
        return Result.success(linkService.saveLink(link));
    }

    /**
     * 友链删除
     */
    @RequestMapping(value = "/links/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return Result.fail(400,"参数异常！");
        }
        if (linkService.deleteBatch(ids)) {
            return Result.success(null);
        } else {
            return Result.fail(400,"删除错误");
        }
    }

    //友链更新
    @PostMapping("links/update")
    @ResponseBody
    public Result update(@RequestParam("linkId") Integer linkId,
                         @RequestParam("linkType") Byte linkType,
                         @RequestParam("linkName") String linkName,
                         @RequestParam("linkUrl") String linkUrl,
                         @RequestParam("linkDescription") String linkDescription,
                         @RequestParam("linkRank") Integer linkRank){
        BlogLink blogLink = new BlogLink();
        blogLink.setLinkId(linkId);
        blogLink.setLinkType(linkType);
        blogLink.setLinkName(linkName);
        blogLink.setLinkUrl(linkUrl);
        blogLink.setLinkDescription(linkDescription);
        blogLink.setLinkRank(linkRank);
        return Result.success( linkService.updateLink(blogLink) );
    }
}