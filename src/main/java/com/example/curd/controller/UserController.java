package com.example.curd.controller;

import com.example.curd.entity.User;
import com.example.curd.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/user/edit")
    public String editPage(@RequestParam int userid, Model model, RedirectAttributes redirectAttributes){
        User user = userService.getById(userid);
        if(user==null){
            redirectAttributes.addFlashAttribute("error","用户不存在");
            return "redirect:/index";
        }
        model.addAttribute("user", user);
        return "editUser";

    }
    /**
     * 接收修改页面提交的数据。
     */
    @PostMapping("/user/update")
    public String updateUser(
            @RequestParam int userid,
            @RequestParam String username,
            @RequestParam(required = false, defaultValue = "")
            String name,
            @RequestParam(required = false, defaultValue = "")
            String password,
            RedirectAttributes redirectAttributes) {

        boolean success = userService.updateUser(
                userid,
                username,
                name,
                password
        );

        if (success) {
            redirectAttributes.addFlashAttribute(
                    "success",
                    "用户信息修改成功"
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "修改失败，用户不存在或参数不合法"
            );
        }

        return "redirect:/index";
    }

    /**
     * 删除用户。
     */
    @PostMapping("/user/delete")
    public String deleteUser(
            @RequestParam int userid,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Object loginUserId =
                session.getAttribute("loginUserId");

        // 可选规则：禁止用户删除自己
        if (loginUserId instanceof Integer currentUserId
                && currentUserId == userid) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "不能删除当前登录用户"
            );

            return "redirect:/index";
        }

        boolean success = userService.deleteUserById(userid);

        if (success) {
            redirectAttributes.addFlashAttribute(
                    "success",
                    "用户删除成功"
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "删除失败，用户可能不存在"
            );
        }

        return "redirect:/index";
    }
}
