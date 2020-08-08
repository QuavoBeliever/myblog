package com.lzy.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzy.mapper.BlogMapper;
import com.lzy.mapper.CommentMapper;
import com.lzy.mapper.QuestionMapper;
import com.lzy.pojo.*;
import com.lzy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
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
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    BlogService blogService;
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    HttpSession session;
    // 更新用户资料
    @GetMapping("/userinfo/setting/{uid}")
    public String userSetting(@PathVariable String uid, Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);
        // todo: 可扩展
        return "user/settings";
    }
    @PostMapping("/userinfo/update/{uid}")
    public String userInfo(@PathVariable String uid,UserInfo userInfo){
        // 获取用户信息;
        userInfoService.updateById(userInfo);
        return "redirect:/user/"+uid;
    }

    // 用户信息回填
    private void userInfoCallBack(String uid,Model model){
        UserInfo userInfo = userInfoService.getById(uid);
        model.addAttribute("userInfo",userInfo);
        if (userInfo.getHobby()!=null && !userInfo.getHobby().equals("")){
            String[] hobbys = userInfo.getHobby().split(",");
            model.addAttribute("infoHobbys",hobbys);
        }
        // 获取用户的问题，博客，回复数
        int blogCount = blogService.count(new QueryWrapper<Blog>().eq("author_id", uid));
        int questionCount = questionService.count(new QueryWrapper<Question>().eq("author_id", uid));
        int commentCount = commentService.count(new QueryWrapper<Comment>().eq("user_id", uid));
        model.addAttribute("blogCount",blogCount);
        model.addAttribute("questionCount",questionCount);
        model.addAttribute("commentCount",commentCount);
    }

    @PostMapping("userinfo/updateavatar/{uid}")
    public String updateAvatar(@PathVariable String uid, MultipartFile file,Model model, HttpServletRequest request) throws Exception {

        String staticPath = ClassUtils.getDefaultClassLoader().getResource("static/").getPath();

        String fileName = file.getOriginalFilename();  // 文件名
        System.out.println(fileName);
        // 图片存储目录及图片名称
        String url_path = "images/avatar/" + fileName;
        //图片保存路径
        String savePath = staticPath + url_path;
        System.out.println("图片保存地址："+savePath);
        // 访问路径=静态资源路径+文件目录路径
        String visitPath ="static/" + url_path;
        System.out.println("图片访问uri："+visitPath);

        File saveFile = new File(savePath);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = userService.getOne(new QueryWrapper<User>().eq("uid", uid));
        user.setAvatar("/images/avatar/" + fileName);
        userService.updateById(user);

        List<Blog> blogList = blogMapper.selectList(new QueryWrapper<Blog>().eq("author_id", uid));
        blogList.forEach(System.out :: println);
        for (int i = 0; i <blogList.size() ; i++) {
            blogList.get(i).setAuthorAvatar("/images/avatar/" + fileName);
            blogMapper.updateById(blogList.get(i));
        }

        List<Question> questionList = questionMapper.selectList(new QueryWrapper<Question>().eq("author_id", uid));
        for (int i = 0; i <questionList.size() ; i++) {
            questionList.get(i).setAuthorAvatar("/images/avatar/" + fileName);
            questionMapper.updateById(questionList.get(i));
        }

        List<Comment> commentList = commentMapper.selectList(new QueryWrapper<Comment>().eq("user_id", uid));
        for (int i = 0; i <commentList.size() ; i++) {
            commentList.get(i).setUserAvatar("/images/avatar/" + fileName);
            commentMapper.updateById(commentList.get(i));
        }

        session.setAttribute("loginUser",user);
        return "index";

    }
}

