package com.example.curd.controller;

import com.example.curd.entity.User;
import com.example.curd.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("loginUserId") == null) {
            return "redirect:/login";
        }

        return "redirect:/index";
    }

    @GetMapping("/index")
    public String list(Model model, HttpSession session) {
        // 未登录不能访问
        if (session.getAttribute("loginUserId") == null) {
            return "redirect:/login";
        }

        List<User> list = userService.list();

        model.addAttribute("list", list);
        model.addAttribute(
                "loginUsername",
                session.getAttribute("loginUsername")
        );

        return "index";
    }
}