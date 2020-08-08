package com.lzy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.pojo.Say;
import com.lzy.service.SayService;
import com.lzy.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
public class SayController {
    @Autowired
    SayService sayService;

    @GetMapping("/say")
    public String userIndexSay(Model model){
        Page<Say> pageParam = new Page<>(1, 50);
        sayService.page(pageParam,new QueryWrapper<Say>().orderByDesc("gmt_create"));
        List<Say> sayList = pageParam.getRecords();
        model.addAttribute("sayList",sayList);
        model.addAttribute("pageParam",pageParam);
        return "page/say";
    }

    @PostMapping("/say/{role}")
    public String saveSay(@PathVariable("role") int role, Say say){
        if (role!=1){
            return "redirect:/say";
        }
        say.setId(MyUtils.getUuid());
        say.setGmtCreate(MyUtils.getTime());

        sayService.save(say);
        return "redirect:/say";
    }
}

