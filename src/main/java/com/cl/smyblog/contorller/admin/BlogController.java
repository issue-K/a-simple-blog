package com.cl.smyblog.contorller.admin;
import com.cl.smyblog.entity.Blog;
import com.cl.smyblog.service.BlogService;
import com.cl.smyblog.service.CategoryService;
import com.cl.smyblog.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/blogs/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        request.setAttribute("categories", categoryService.getAllCategories());
        return "admin/edit";
    }

    @PostMapping("/blogs/md/uploadfile")//上传文件的接口
    public void uploadFile(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam("editormd-image-file")MultipartFile file) throws IOException, URISyntaxException {
        String file_upload_path = "D:/bigdata/smyblog/src/main/resources/upload/";
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring( fileName.lastIndexOf("."));
        //生成文件名
        SimpleDateFormat frm = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append( frm.format(new Date()) ).append( r.nextInt(100) ).append(suffixName);
        String newFileName = tempName.toString();
        //创建文件
        File destFile = new File( file_upload_path+newFileName );
        String fileUrl = getUriPrefix( new URI(""+request.getRequestURL()) )+"/upload/"+newFileName;
        File fileDirectory = new File( file_upload_path );
        if( !fileDirectory.exists() ){//若这个文件夹不存在,需要创建
            if( !fileDirectory.mkdir() ){//若创建文件夹失败
                throw new IOException("文件夹创建失败,路径为: "+fileDirectory );
            }
        }

        file.transferTo(destFile);//写入本地

        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type","text.html");
        response.getWriter().write("{\"success\": 1, \"message\":\"success\",\"url\":\"" + fileUrl + "\"}");

    }

    @PostMapping("/blogs/save")//保存博客内容
    @ResponseBody
    public Result save(@RequestParam("blogTitle") String blogTitle,
                       @RequestParam(name = "blogSubUrl", required = false) String blogSubUrl,
                       @RequestParam("blogCategoryId") Integer blogCategoryId,
                       @RequestParam("blogTags") String blogTags,
                       @RequestParam("blogContent") String blogContent,
                       @RequestParam("blogCoverImage") String blogCoverImage,
                       @RequestParam("blogStatus") Byte blogStatus,
                       @RequestParam("enableComment") Byte enableComment) {
        if(StringUtils.isEmpty(blogTitle) ){
            return Result.fail(400,"请输入文章标题");
        }
        if (blogTitle.trim().length() > 150) {
            return Result.fail(400,"标题过长");
        }
        if (StringUtils.isEmpty(blogTags)) {
            return Result.fail(400,"请输入文章标签");
        }
        if (blogTags.trim().length() > 150) {
            return Result.fail(400,"标签过长");
        }
        if (blogSubUrl.trim().length() > 150) {
            return Result.fail(400,"路径过长");
        }
        if (StringUtils.isEmpty(blogContent)) {
            return Result.fail(400,"请输入文章内容");
        }
        if (blogTags.trim().length() > 100000) {
            return Result.fail(400,"文章内容过长");
        }
        if (StringUtils.isEmpty(blogCoverImage)) {
            return Result.fail(400,"封面图不能为空");
        }
        Blog blog = new Blog();
        blog.setBlogTitle(blogTitle);
        blog.setBlogSubUrl(blogSubUrl);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus);
        blog.setEnableComment(enableComment);
        String message = blogService.saveBlog(blog);
        if( message == "success" ){
            return Result.success(null);
        }else{
            return Result.fail(400,message);
        }
    }

    public static String getUriPrefix( URI uri ){
        return uri.getScheme() + "://" + uri.getHost() + ":"+uri.getPort();
    }
}