package com.lzy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.pojo.Blog;
import com.lzy.pojo.BlogCategory;
import com.lzy.service.BlogCategoryService;
import com.lzy.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘子义
 * @since 2020-08-06
 */

@Controller
public class BlogCategoryController {

    @Autowired
    BlogCategoryService blogCategoryService;
    @Autowired
    BlogService blogService;

    @GetMapping("/blog/category/{bid}/{page}/{limit}")
    public String blogPage(
            @PathVariable int bid,
            @PathVariable int page,
            @PathVariable int limit,
            Model model){
        if (page < 1){
            page = 1;
        }
        // 查询这个分类下的所有问题，获取查询的数据信息
        Page<Blog> pageParam = new Page<>(page, limit);
        blogService.page(pageParam,new QueryWrapper<Blog>().eq("category_id",bid).orderByDesc("gmt_create"));
        List<Blog> records = pageParam.getRecords();
        model.addAttribute("blogList",records);
        model.addAttribute("pageParam",pageParam);
        // 查询这个分类信息
        BlogCategory category = blogCategoryService.getById(bid);
        model.addAttribute("thisCategoryName",category.getCategory());

        // 全部分类信息
        List<BlogCategory> categoryList = blogCategoryService.list(null);
        model.addAttribute("categoryList",categoryList);

        return "blog/list";
    }
}

