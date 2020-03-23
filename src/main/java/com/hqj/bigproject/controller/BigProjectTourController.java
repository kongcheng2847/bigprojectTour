package com.hqj.bigproject.controller;

import com.hqj.bigproject.pojo.BpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/bigproject")
public class BigProjectTourController {

    private static final Logger LOG = LoggerFactory.getLogger(BigProjectTourController.class);

    @RequestMapping("/login.do")
    public String login() {
        return "thymeleaf/login/login";
    }

    @RequestMapping("/index.do")
    public String selectAll(ModelMap modelMap, HttpServletRequest request) {
        BpUser bpUser = (BpUser) request.getSession().getAttribute("loginUser");
        modelMap.addAttribute("name", bpUser.getUserName());
        modelMap.addAttribute("describe", "万丈红尘三杯酒，千秋大业一壶茶！！");
        modelMap.addAttribute("bpUser", bpUser);
        return "thymeleaf/login/index";
    }

    /**
     * 查看地图
     * @return
     */
    @RequestMapping(value = "/viewMap")
    public String viewMap(){
        return "thymeleaf/map/supermap";
    }

    /**
     * 查看日程
     * @return
     */
    @RequestMapping(value = "/viewCalendar")
    public String viewCalendar(){
        return "thymeleaf/fullcalendar/fullcalendar";
    }
}
