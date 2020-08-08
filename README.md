# myblog
界面

![QQ图片20200808153346](C:\Users\ASUS\Desktop\QQ图片20200808153346.png)

**项目使用技术**

* springboot
* springsecurity
* mysql
* thymeleaf
* mybatis-plus
* 代码自动生成
* bootstrap
* fastjson



**项目介绍**

* 项目分为登录注册注销、写博客、提问题、评论、个人页面等模块



**项目结构**

```
src/main/java/com/lzy
- config        配置类，里面有springsecurity等
- controller    controller
- generator     代码自动生成
- mapper        mapper
- pojo          实体类
- service       service
- utils         工具类，生成时间、uuid
- vo            自定义一些类
DemoApplication 启动类

src/resources
- static        静态资源
- templates     页面模板
- application-dev.properties   配置文件
```

