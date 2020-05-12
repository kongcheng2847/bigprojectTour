package com.hqj.bigproject.controller;

import com.hqj.bigproject.pojo.BpUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("th")
public class ThymeleafController {

    @RequestMapping("/index")
    public String index(ModelMap map) {
        map.addAttribute("name", "thymeleaf-bigproject");
        return "thymeleaf/index";
    }

    @RequestMapping("/login")
    public String login(ModelMap map) {
        map.addAttribute("name", "thymeleaf-bigproject");

        return "thymeleaf/login/index";
    }

    @RequestMapping("center")
    public String center() {
        return "thymeleaf/center/center";
    }

    @RequestMapping("test")
    public String test(ModelMap map) {

        BpUser u = new BpUser();
        u.setUserId("superadmin");
        u.setUserName("superadmin");
        u.setPassWord("superadmin");
        map.addAttribute("user", u);

        BpUser u1 = new BpUser();
        u.setUserId("xiaoming");
        u1.setUserName("xiaoming");
        u1.setPassWord("xiaoming");

        BpUser u2 = new BpUser();
        u.setUserId("xiaohua");
        u2.setUserName("xiaohua");
        u2.setPassWord("xiaohua");

        List<BpUser> userList = new ArrayList<>();
        userList.add(u);
        userList.add(u1);
        userList.add(u2);

        map.addAttribute("userList", userList);

        return "thymeleaf/test";
    }

    @PostMapping("postform")
    public String postform(BpUser u) {

        System.out.println("姓名：" + u.getUserName());
        System.out.println("ID：" + u.getUserId());

        return "redirect:/th/test";
    }

    @RequestMapping("showerror")
    public String showerror(BpUser u) {

        int a = 1 / 0;

        return "redirect:/th/test";
    }
}

