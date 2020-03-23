package com.hqj.bigproject.controller;

import com.github.pagehelper.PageInfo;
import com.hqj.bigproject.pojo.BpUser;
import com.hqj.bigproject.service.BpUserService;
import com.hqj.bigproject.utils.RedisOperator;
import com.hqj.bigproject.utils.UserJSONResult;
import com.hqj.bigproject.utils.UtilJson;
import com.hqj.bigproject.utils.UtilUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class BpUserController {
    private static final Logger LOG = LoggerFactory.getLogger(BpUserController.class);

    @Autowired
    private BpUserService bpUserService;
    @Autowired
    private RedisOperator redisOperator;

    @RequestMapping(value = "/findAll")
    public UserJSONResult findAll(){
        return UserJSONResult.ok(bpUserService.findAll());
    }

    /**
     * 验证登录
     * @param bpUser
     * @return
     */
    @RequestMapping(value = "/checklogin")
    @ResponseBody
    public String checkUser(@RequestBody BpUser bpUser, HttpSession httpSession){
        String status = "";
        LOG.info("用户名："+bpUser.getUserName());
        LOG.info("密码："+bpUser.getPassWord());
        bpUser = bpUserService.selectBpUser(bpUser);
        if (bpUser == null) {
            status = "fail";
        }else{
            LOG.info("登录成功！");
            httpSession.setAttribute("loginUser",bpUser);
            redisOperator.set(httpSession.getId(), UtilJson.toJson(bpUser));
            status = "ok";
        }

        return status;
    }

    /**
     * 退出登录
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpSession httpSession){
        LOG.info("SessionId="+httpSession.getId());
        redisOperator.del(httpSession.getId());
        httpSession.invalidate();
        return "ok";
    }

    /**
     * 创建用户
     * @param bpUser
     * @return
     */
    @RequestMapping(value = "/createuser", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestBody BpUser bpUser) {
        bpUser.setUserId(UtilUUID.newShortUUID());
        int i = bpUserService.insertUser(bpUser);
        if (i != 0) {
            return "ok";
        } else {
            return "erro";
        }
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/deleteUser",method = RequestMethod.GET)
    @ResponseBody
    public String doDeleteUser(String userId){
        String status = "";
        int i = bpUserService.deleteUser(userId);
        if (i > 0){
            status = "ok";
        }else {
            status = "fail";
        }
        return status;
    }

    /**
     * 用户管理
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/viewUsers")
    public ModelAndView viewUsers(ModelMap modelMap){
        LOG.info("查看用户列表...");
        PageInfo<BpUser> pageInfo = bpUserService.findAll();
        modelMap.addAttribute("bpuserList", pageInfo);
        return new ModelAndView("thymeleaf/user/userlist");
    }
}
