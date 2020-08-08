package com.lzy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzy.mapper.BlogMapper;
import com.lzy.pojo.Blog;
import com.lzy.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    BlogMapper blogMapper;
    @Autowired
    BlogService blogService;

    @Test
    void contextLoads() {
        /*List<Blog> blogList = blogMapper.selectList(new QueryWrapper<Blog>().eq("author_id", "6aba4abde7c7499597aa91e7f154ca2c"));
        blogList.forEach(System.out::println);
        blogList.get(0).getId();
        System.out.println(blogList.get(0).getId());
        blogList.get(1).setAuthorAvatar("/images/avatar/" + fileName)*/


    }
}
