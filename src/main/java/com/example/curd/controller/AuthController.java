package com.example.curd.controller;

import com.example.curd.entity.User;
import com.example.curd.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 进入注册页面。
     */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    /**
     * 提交注册信息。
     */
    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String name,
            @RequestParam String password,

            Model model) {

        username = username.trim();

        // 基础参数校验
        if (username.length() < 3 || username.length() > 20) {
            model.addAttribute("error", "用户名长度应为3到20个字符");
            return "register";
        }

        if (name.length() > 20) {
            model.addAttribute("error", "姓名长度应为1到20个字符");
            return "register";
        }

        if (password.length() < 6 || password.length() > 50) {
            model.addAttribute("error", "密码长度应为6到50个字符");
            return "register";
        }

        boolean success = userService.register(username, name, password);

        if (!success) {
            model.addAttribute("error", "用户名已经存在");
            return "register";
        }

        return "redirect:/login";
    }

    /**
     * 进入登录页面。
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * 提交登录信息。
     */
    @PostMapping("/login")
    public String login( @RequestParam String username, @RequestParam String password, HttpSession session, Model model) {

        username = username.trim();

        User user = userService.login(username, password);

        if (user == null) {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }

        // Session 中不要存储密码
        session.setAttribute("loginUserId", user.getUserid());
        session.setAttribute("loginUsername", user.getUsername());

        return "redirect:/index";
    }

    /**
     * 退出登录。
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}