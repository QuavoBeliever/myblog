package com.lzy.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.pojo.Comment;
import com.lzy.pojo.Question;
import com.lzy.pojo.QuestionCategory;
import com.lzy.service.CommentService;
import com.lzy.service.QuestionCategoryService;
import com.lzy.service.QuestionService;
import com.lzy.utils.MyUtils;
import com.lzy.vo.QuestionWriteForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘子义
 * @since 2020-08-06
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionCategoryService questionCategoryService;
    @Autowired
    CommentService commentService;
    @Autowired
    QuestionService questionService;

    //展示问题
    @GetMapping("/question")
    public String questionList(Model model){
        Page<Question> pageParam = new Page<>(1, 10);
        questionService.page(pageParam,new QueryWrapper<Question>().orderByDesc("gmt_create"));
        List<Question> questionList = pageParam.getRecords();
        model.addAttribute("questionList",questionList);
        model.addAttribute("pageParam",pageParam);
        //分类的问题信息
        List<QuestionCategory> categoryList = questionCategoryService.list(null);
        model.addAttribute("categoryList",categoryList);

        return "question/list";
    }

    @GetMapping("/question/{page}/{limit}")
    public String questionListPage(@PathVariable int page,
                                   @PathVariable int limit,
                                   Model model){
        if (page<1){
            page=1;
        }
        Page<Question> pageParam = new Page<>(page, limit);
        questionService.page(pageParam,new QueryWrapper<Question>().orderByDesc("gmt_create"));
        List<Question> questionList = pageParam.getRecords();
        model.addAttribute("questionList",questionList);
        model.addAttribute("pageParam",pageParam);

        List<QuestionCategory> categoryList = questionCategoryService.list(null);
        model.addAttribute("categoryList",categoryList);
        return "question/list";
    }

    //写问题
    @GetMapping("question/write")
    public String toWrite(Model model){
        List<QuestionCategory> categoryList = questionCategoryService.list(null);
        model.addAttribute("categoryList",categoryList);
        return "question/write";
    }

    @PostMapping("question/write")
    public synchronized String write(QuestionWriteForm questionWriteForm){
        Question question = new Question();
        question.setQid(MyUtils.getUuid());
        question.setTitle(questionWriteForm.getTitle());
        question.setContent(questionWriteForm.getContent());
        question.setStatus(0);
        question.setSort(0);
        question.setViews(0);
        question.setAuthorId(questionWriteForm.getAuthorId());
        question.setAuthorName(questionWriteForm.getAuthorName());
        question.setAuthorAvatar(questionWriteForm.getAuthorAvatar());

        QuestionCategory category = questionCategoryService.getById(questionWriteForm.getCategoryId());
        question.setCategoryId(questionWriteForm.getCategoryId());
        question.setCategoryName(category.getCategory());
        question.setGmtCreate(MyUtils.getTime());
        question.setGmtUpdate(MyUtils.getTime());
        questionService.save(question);
        return "redirect:/question";
    }

    //阅读问题
    @GetMapping("/question/read/{qid}")
    public String read(@PathVariable String qid,Model model){
        Question question = questionService.getOne(new QueryWrapper<Question>().eq("qid", qid));
        question.setViews(question.getViews()+1);
        questionService.updateById(question);
        model.addAttribute("question",question);

        //查询评论
        List<Comment> commentList = commentService.list(new QueryWrapper<Comment>().eq("topic_id", qid).orderByDesc("gmt_create"));
        model.addAttribute("commentList",commentList);
        return "question/read";
    }

    //评论
    @PostMapping("question/comment/{qid}")
    public String comment(@PathVariable String qid,Comment comment){
        comment.setCommentId(MyUtils.getUuid());
        comment.setTopicCategory(2);
        comment.setGmtCreate(MyUtils.getTime());
        commentService.save(comment);

        //把问题状态改为已解决
        Question question = questionService.getOne(new QueryWrapper<Question>().eq("qid", qid));
        question.setStatus(1);
        questionService.updateById(question);
        return "redirect:/question/read/"+qid;
    }

    //编辑问题
    @GetMapping("/question/editor/{uid}/{qid}")
    public String toEditor(@PathVariable String uid,
                           @PathVariable String qid,
                           Model model){
        Question question = questionService.getOne(new QueryWrapper<Question>().eq("qid", qid));
        if (!question.getAuthorId().equals(uid)){
            MyUtils.print("非法编辑");
            return "redirect:/question";

        }
        model.addAttribute("question",question);
        List<QuestionCategory> categoryList = questionCategoryService.list(null);
        model.addAttribute("categoryList",categoryList);
        return "question/editor";
    }

    @PostMapping("/question/editor")
    public String editor(Question question){
        Question queryQuestion = questionService.getOne(new QueryWrapper<Question>().eq("qid", question.getQid()));

        queryQuestion.setTitle(question.getTitle());
        queryQuestion.setCategoryId(question.getCategoryId());
        queryQuestion.setContent(question.getContent());
        queryQuestion.setGmtUpdate(MyUtils.getTime());

        questionService.updateById(queryQuestion);

        return "redirect:/question/read/"+question.getQid();

    }

    //删除问题
    @GetMapping("/question/delete/{uid}/{qid}")
    public String delete(@PathVariable String uid,
                         @PathVariable String qid,
                         Model model){
        Question question = questionService.getOne(new QueryWrapper<Question>().eq("qid", qid));
        if (!question.getAuthorId().equals(uid)){
            MyUtils.print("非法删除");
            return "redirect:/question";
        }

        questionService.removeById(question);

        return "redirect:/question";
    }




    // md 文件上传
    @ApiOperation(value = "md文件上传问题")
    @RequestMapping("/question/write/file/upload")
    @ResponseBody
    public JSONObject fileUpload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {

        //获得SpringBoot当前项目的路径：System.getProperty("user.dir")
        String path = System.getProperty("user.dir")+"/upload/";

        //按照月份进行分类：
        Calendar instance = Calendar.getInstance();
        String month = (instance.get(Calendar.MONTH) + 1)+"月";
        path = path+month;

        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }

        //上传文件地址
        MyUtils.print("上传文件保存地址："+realPath);

        //解决文件名字问题：我们使用uuid;
        String filename = "ks-"+ UUID.randomUUID().toString().replaceAll("-", "");
        String originalFilename = file.getOriginalFilename();
        // KuangUtils.print(originalFilename);
        assert originalFilename != null;
        int i = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(i + 1);

        String outFilename = filename + "."+suffix;
        MyUtils.print("文件名：" + outFilename);

        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(new File(realPath +"/"+ outFilename));

        //给editormd进行回调
        JSONObject res = new JSONObject();
        res.put("url","/upload/"+month+"/"+ outFilename);
        res.put("success", 1);
        res.put("message", "upload success!");
        MyUtils.print(res.toJSONString());

        return res;
    }
}

