package com.why.transportsecurity.controller;

import com.why.transportsecurity.utils.commonutils.EmptyUtils;
import com.why.transportsecurity.po.AdminInfo;
import com.why.transportsecurity.po.AdminLogin;
import com.why.transportsecurity.exception.AdminException;
import com.why.transportsecurity.vo.PersonalCenter;
import com.why.transportsecurity.service.impl.AdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @ClassName：AdminController
 * @Description：todo 用于管理员用户账号控制层controller
 * @Author: why
 * @DateTime：2021/12/11 14:11
 */
@Controller
@RequestMapping("/ts/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    /**
     * 展示登录页面
     * @return
     */
    @RequestMapping(value = "/showLogin",method = RequestMethod.GET)
    public String showLogin(){
        log.info("show login");
        log.info("show login complete");
        return "adminPage/signIn";
    }

    /**
     * 展示注册页面
     * @return
     */
    @RequestMapping(value = "/showRegister",method = RequestMethod.GET)
    public String showRegister(){
        log.info("show register");
        return "adminPage/register";
    }

    /**
     * 注册
     * @param request
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(HttpServletRequest request, Model model){
        log.info("register...");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String uPhone = request.getParameter("uPhone");
        log.info("Configuration parameter");
        AdminLogin adminLogin = new AdminLogin(1, username, password, "admin");
        try {
            log.info("register is staring...");
            adminService.register(adminLogin,uPhone,"admin");
        } catch (AdminException e) {
            log.error(e.toString());
            model.addAttribute("errorMsg","用户已经存在,请修改用户名");
            return "adminPage/register";
        }
        log.info("complete!");
        return "redirect:/ts/admin/showLogin";
    }

    /**
     * 个人中心
     * @param principal
     * @return
     */
    @RequestMapping(value = "/personal_center",method = RequestMethod.GET)
    public String personalCenter(Principal principal,Model model){
        String name = principal.getName();
        log.info("Query the information of " + name);
        try {
            log.info("Enter the personal center");
            PersonalCenter personalCenter = adminService.personalCenter(name);
            model.addAttribute("personal",personalCenter);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AdminLogin userDetails = (AdminLogin) authentication.getPrincipal();
            model.addAttribute("nameLogin",userDetails.getUsername());
            return "/adminPage/personalCenter";
        } catch (AdminException e) {
            log.error(e.toString());
           return "redirect:/ts/admin/toError";
        }
    }

    /**
     * 基本信息修改
     * @param request
     * @return
     */
    @RequestMapping("/personal_update")
    public String updateInfo(HttpServletRequest request,Model model,Principal principal){
        log.info("Start updating personal information");
        //接收参数
        String username = request.getParameter("username");
        String uPhone = request.getParameter("uPhone");
        String uEmail = request.getParameter("uEmail");
        String uAddress = request.getParameter("uAddress");
        String uBirth = request.getParameter("uBirth");
        String uDate = request.getParameter("uDate");
        String usernameOld = request.getParameter("usernameOld");
        String uId = request.getParameter("uId");
        //判空
        log.info("Calibration parameters");
        boolean empty = EmptyUtils.isStringEmpty(usernameOld,uId);
        if (empty){
            log.error("Invalid parameter");
            model.addAttribute("resultMsg","参数校验错误！");
            return "redirect:/ts/admin/personal_center";
        }
        //创建参数对象
        log.info("Create a parameter object");
        AdminLogin adminLogin = new AdminLogin(null, username, null, null);
        AdminInfo adminInfo = new AdminInfo(null, uPhone, uEmail, uAddress, uBirth, uDate, Integer.valueOf(uId));
        //修改
        try {
            log.info("Update database");
            adminService.updatePersonal(adminLogin,adminInfo,usernameOld);
            log.info("complete!");
            model.addAttribute("resultMsg","更新成功！");
            return "redirect:/ts/admin/personal_center";
        } catch (AdminException e) {
            log.error(e.toString());
            model.addAttribute("resultMsg",e.toString());
            return "redirect:/ts/admin/personal_center";
        }
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/update_pwd")
    public String updatePwd(HttpServletRequest request,Model model){
        //用户名
        String username = request.getParameter("username");
        //旧密码
        String uOldPwd = request.getParameter("uOldPwd");
        //第一次输入的新密码
        String uNewPwdPre = request.getParameter("uNewPwdPre");
        //第二次输入的新密码
        String uNewPwdAfter = request.getParameter("uNewPwdAfter");
        //查询数据库用户数据
        PersonalCenter personalCenter = null;
        try {
            if (username != null){
                personalCenter = adminService.personalCenter(username);
                model.addAttribute("personal",personalCenter);
            }else {
                return "redirect:/ts/admin/personal_center";
            }
        } catch (AdminException e) {
            log.error(e.toString());
            model.addAttribute("alertMsg",e.toString());
            return "/adminPage/personalCenter";
        }
        //参数校验
        boolean empty = EmptyUtils.isStringEmpty(username, uOldPwd, uNewPwdPre, uNewPwdAfter);
        if (empty){
            log.warn("Parameter verification failed");
            model.addAttribute("alertMsg","输入不能为空");
            return "/adminPage/personalCenter";
        }
        //判断旧密码和数据库中密码是否一致
        String encode = passwordEncoder.encode(uOldPwd);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdminLogin userDetails = (AdminLogin) authentication.getPrincipal();
        boolean matches = passwordEncoder.matches(uOldPwd, userDetails.getPassword());
        if (!matches){
            log.warn("The original password is incorrectly entered");
            model.addAttribute("alertMsg","原密码输入不正确");
            return "/adminPage/personalCenter";
        }
        //判断两次输入的密码是否一致
        if (!uNewPwdPre.equals(uNewPwdAfter)){
            log.warn("Two password entries are inconsistent");
            model.addAttribute("alertMsg","两次密码输入不一致");
            return "/adminPage/personalCenter";
        }
        //更新密码
        AdminLogin adminLogin = new AdminLogin(1,username,uNewPwdPre,null);
        try {
            adminService.updatePersonalPassword(adminLogin);
        } catch (AdminException e) {
            log.warn("Two password entries are inconsistent");
            model.addAttribute("alertMsg","更新失败");
            return "/adminPage/personalCenter";
        }
        //跳转去登录
        return "redirect:/ts/admin/showLogin";
    }

    @RequestMapping("/showFindPassword")
    public String showFindPassword(){
        return "adminPage/findPwd";
    }

//    @RequestMapping("/find_password")
//    public String findPassword(String username){
//
//    }

    /**
     * 登录成功
     * @return
     */
    @RequestMapping("/toSuccess")
    @ResponseBody
    public String success(){
        log.info("login success");
        return "success";
    }

    /**
     * 登录失败
     * @return
     */
    @RequestMapping("/toError")
    @ResponseBody
    public String error(){
        log.info("login error");
        return "error";
    }
}
