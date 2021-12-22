# a-simple-blog
springboot+mybatis

### first commit

导入了关于```admin```的前端页面.

实现了```/admin/login```登录接口,在浏览器输入```/admin/login```(get请求)可以进入登录页面

在登录页面输入账号密码和验证码可发送```/admin/login```(post请求)进行登录

其中```/admin/registry```注册接口只是测试使用,后续会修改.

### second commit

实现了登录拦截器

未登录直接跳转到登录页面,登录成功后把用户的信息(```id```)存在```session```中

实现了修改密码,修改名称

使用之前存在session中的id找到原来的用户进行修改即可.

### add blogedit(博客编辑页面),Tag(博客标签)

实现文件上传功能

![image](https://user-images.githubusercontent.com/64780485/147088033-3dfa1bfc-1f15-4ad5-a76d-14f2ecb1db4a.png)

![image](https://user-images.githubusercontent.com/64780485/147088107-f7de8ec1-0f12-4c9a-933d-3dc54d236c68.png)

