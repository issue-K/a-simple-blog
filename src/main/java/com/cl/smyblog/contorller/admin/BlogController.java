package com.cl.smyblog.contorller.admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/blogs/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        return "admin/edit";
    }

    @PostMapping("/blogs/md/uploadfile")
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

    public static String getUriPrefix( URI uri ){
        return uri.getScheme() + "://" + uri.getHost() + ":"+uri.getPort();
    }
}