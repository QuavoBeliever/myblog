package com.lzy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.pojo.Question;
import com.lzy.pojo.QuestionCategory;
import com.lzy.service.QuestionCategoryService;
import com.lzy.service.QuestionService;
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

public class QuestionCategoryController {
    @Autowired
    QuestionCategoryService questionCategoryService;
    @Autowired
    QuestionService questionService;

    @GetMapping("question/category/{cid}/{page}/{limit}")
    public String questionPage(@PathVariable int cid,
                               @PathVariable int page,
                               @PathVariable int limit,
                               Model model){
        if (page<1){
            page=1;
        }
        //查询这个分类下所有的问题，获取数据
        Page<Question> pageParam = new Page<>(page, limit);
        questionService.page(pageParam,new QueryWrapper<Question>().eq("category_id",cid).orderByDesc("gmt_create"));
        List<Question> records = pageParam.getRecords();
        model.addAttribute("questionList",records);
        model.addAttribute("pageParam",pageParam);

        //查询这个分类信息
        QuestionCategory category = questionCategoryService.getById(cid);
        model.addAttribute("thisCategoryName",category.getCategory());

        //查询全部分类信息
        List<QuestionCategory> categoryList = questionCategoryService.list(null);
        model.addAttribute("categoryList",categoryList);
        return "question/list";

    }
}

