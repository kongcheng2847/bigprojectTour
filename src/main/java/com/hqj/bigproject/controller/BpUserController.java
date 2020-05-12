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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class BpUserController {
    private static final Logger LOG = LoggerFactory.getLogger(BpUserController.class);

    @Autowired
    private BpUserService bpUserService;
    @Autowired
    private RedisOperator redisOperator;

    @RequestMapping(value = "/findAll")
    public UserJSONResult findAll(@RequestParam(defaultValue = "1")Integer pageNum,
                                  @RequestParam(defaultValue = "3") Integer pageSize){
        return UserJSONResult.ok(bpUserService.findAll(pageNum,pageSize));
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
        return "redirect:/bigproject/login.do";
    }

    /**
     * 创建用户
     * @param bpUser
     * @return
     */
    @PostMapping(value = "/createuser")
    public String createUser(BpUser bpUser) {
        System.out.printf("新增用户： %s",bpUser);
        LOG.info("新增用户：%s",bpUser);
        bpUser.setUserId(UtilUUID.newShortUUID());
        int i = bpUserService.insertUser(bpUser);
        if(i > 0){
            return "redirect:/bigproject/login.do";
        }else{
            return "redirect:/bigproject/logon.do";
        }
    }

    /**
     * 验证用户注册时的用户名是否已存在
     * @Parmam String
     * @return
     */
    @PostMapping(value = "/validate/username")
    @ResponseBody
    public Boolean ValiDateUserName(@RequestParam String userName){
        LOG.info(userName);
        BpUser bpUser = new BpUser();
        bpUser.setUserName(userName);
        return bpUserService.selectBpUser(bpUser) == null;
    }

    /**
     * 验证用户注册时邮箱是否已存在
     * @Parmam String
     * @return
     */
    @PostMapping(value = "/validate/email")
    @ResponseBody
    public Boolean ValiDateEmail(@RequestParam String eMail){
        LOG.info(eMail);
        BpUser bpUser = new BpUser();
        bpUser.seteMail(eMail);
        return bpUserService.selectBpUser(bpUser) == null;
    }

    /**
     * 验证用户注册时身份证号是否已存在
     * @Parmam String
     * @return
     */
    @PostMapping(value = "/validate/idcard")
    @ResponseBody
    public Boolean ValiDateIdCard(@RequestParam String idCrad){
        LOG.info(idCrad);
        BpUser bpUser = new BpUser();
        bpUser.setIdCrad(idCrad);
        return bpUserService.selectBpUser(bpUser) == null;
    }

    /**
     * 修改用户密码
     * @return
     */
    @PostMapping(value = "/update/password")
    public String updateBpUserPassWord(@RequestParam String userName,String idCrad,String passWord){
        BpUser bpUser = new BpUser();
        bpUser.setUserName(userName);
        bpUser.setIdCrad(idCrad);
        bpUser = bpUserService.selectBpUser(bpUser);
        bpUser.setPassWord(passWord);
        int i = bpUserService.updateUser(bpUser);
        if (i>0){
            return "redirect:/bigproject/login.do";
        }
        return "redirect:/bigproject/rest/password";
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/deleteUser",method = RequestMethod.GET)
    @ResponseBody
    public String doDeleteUser(String userId) {
        String status = "";
        int i = bpUserService.deleteUser(userId);
        if (i > 0) {
            status = "ok";
        } else {
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
    public ModelAndView viewUsers(ModelMap modelMap) {
        LOG.info("查看用户列表...");
        PageInfo<BpUser> pageInfo = bpUserService.findAll(1, 3);

        modelMap.addAttribute("bpuserList", pageInfo);
        return new ModelAndView("thymeleaf/user/userlist");
    }
}
