package com.lzy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzy.pojo.Comment;
import com.lzy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘子义
 * @since 2020-08-06
 */
@Controller

public class CommentController {
    @Autowired
    CommentService commentService;

    //删除评论
    @GetMapping("user/comment/delete/{uid}/{cid}")
    public String deleteComment(@PathVariable String uid,
                                @PathVariable String cid)
    {
        commentService.remove(new QueryWrapper<Comment>().eq("comment_id", cid));
        return "redirect:/user/comment/"+uid+"/1/10";
    }
}

