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

#### 实现文件上传功能

![image](https://user-images.githubusercontent.com/64780485/147088033-3dfa1bfc-1f15-4ad5-a76d-14f2ecb1db4a.png)

![image](https://user-images.githubusercontent.com/64780485/147088107-f7de8ec1-0f12-4c9a-933d-3dc54d236c68.png)

先接受一个MultipartFile的文件参数

再生成一个文件名,new一个新的file(指定好文件路径)

最后调用file.transferTo(destFile)保存在本地

#### 保存博客功能

先在controller层接收,下面是service层代码

先根据博客所属的分类,用对应```Mapper```找到对应的博客分类

![image](https://user-images.githubusercontent.com/64780485/147090326-454a0cbe-b350-42b9-9ed9-2aaf547c2feb.png)

原因是我们需要把这个分类的```CategoryRank```属性加上一更新到数据库

然后处理标签,把已有的标签和没有的标签分开来,把没有的标签插入到数据库

最后,还需要更新Blog-Tag(博客-标签)的对应表

![image](https://user-images.githubusercontent.com/64780485/147090757-21c7f841-923f-4a1d-8b07-f570204ad390.png)
