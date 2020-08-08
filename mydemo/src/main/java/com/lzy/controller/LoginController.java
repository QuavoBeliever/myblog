package com.lzy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzy.pojo.Invite;
import com.lzy.pojo.User;
import com.lzy.pojo.UserInfo;
import com.lzy.service.InviteService;
import com.lzy.service.UserInfoService;
import com.lzy.service.UserService;
import com.lzy.utils.MyUtils;
import com.lzy.vo.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    InviteService inviteService;

    @Autowired
    UserService userService;

    @Autowired
    UserInfoService userInfoService;

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String toRegister() {
        return "register";
    }

    //注册
    @PostMapping("/register")
    public String register(RegisterForm registerForm, Model model) {
        MyUtils.print("表单注册信息：" + registerForm.toString());

        //表单密码重复判断
        if (!registerForm.getPassword().equals(registerForm.getPassword())) {
            model.addAttribute("registerMsg", "密码输入错误");
            return "register";
        }
        //用户名已经存在
        User hasUser = userService.getOne(new QueryWrapper<User>().eq("username", registerForm.getUsername()));
        if (hasUser != null) {
            model.addAttribute("registerMsg", "用户已经存在");
            return "register";
        }

        //验证邀请码
        Invite invite = inviteService.getOne(new QueryWrapper<Invite>().eq("code", registerForm.getCode()));
        if (invite == null) {
            model.addAttribute("registerMsg", "邀请码不存在");
            return "register";
        } else {
            if (invite.getStatus() == 1) {
                model.addAttribute("registerMsg", "邀请码已被使用");
                return "register";
            } else {
                User user = new User();
                user.setUid(MyUtils.getUuid());

                if (registerForm.getUsername().equals("lzy")){
                    user.setRoleId(1);
                }else {
                    user.setRoleId(2);
                }
                user.setUsername(registerForm.getUsername());
                String encode = new BCryptPasswordEncoder().encode(registerForm.getPassword());
                user.setPassword(encode);
                user.setGmtCreate(MyUtils.getTime());
                user.setLoginDate(MyUtils.getTime());

                userService.save(user);
                MyUtils.print("新用户注册成功" + user);

                //激活邀请码
                invite.setActiveTime(MyUtils.getTime());
                invite.setStatus(1);
                invite.setUid(user.getUid());
                inviteService.updateById(invite);

                //把用户uid传给userinfo表
                userInfoService.save(new UserInfo().setUid(user.getUid()));

                //注册成功
                return "redirect:/toLogin";


            }
        }
    }

}


