package com.lzc.books.controller;


import com.lzc.books.bean.Admin;
import com.lzc.books.bean.ReaderCard;
import com.lzc.books.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

//标注为一个Spring mvc的Controller
@Controller
public class LoginController {

    private LoginService loginService;


    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    //负责处理login.html请求
    @RequestMapping(value = {"/","/login.html"})
    public String toLogin(HttpServletRequest request){
        request.getSession().invalidate();
        return "login";
        //直接跳转到页面
    }

    //负责处理loginCheck.html请求
    //请求参数会根据参数名称默认契约自动绑定到相应方法的入参中
    @RequestMapping(value = "/api/loginCheck", method = RequestMethod.POST)
    public @ResponseBody
    Object loginCheck(HttpServletRequest request){
//        System.out.println(66666);
        int id=Integer.parseInt(request.getParameter("id"));
        String passwd = request.getParameter("passwd");
                boolean isReader = loginService.hasMatchReader(id, passwd);
                boolean isAdmin = loginService.hasMatchAdmin(id, passwd);
        HashMap<String, String> res = new HashMap<String, String>();
                if (isAdmin==false&&isReader==false) {
                    res.put("stateCode", "0");
                    res.put("msg","账号或密码错误！");
                } else if(isAdmin){
                    Admin admin=new Admin();
                    admin.setAdminId(id);
                    admin.setPassword(passwd);
                    //把admin存入session域
                    request.getSession().setAttribute("admin",admin);
                    request.getSession().setAttribute("adminId", admin.getAdminId());
                    res.put("stateCode", "1");
                    res.put("msg","管理员登陆成功！");
                }else {
                    ReaderCard readerCard = loginService.findReaderCardByUserId(id);
                    request.getSession().setAttribute("readercard", readerCard);
                    request.getSession().setAttribute("readername", readerCard.getName());
                    res.put("stateCode", "2");
                    res.put("msg","读者登陆成功！");
                }
        return res;
    }
    //跳转到管理员界面
    @RequestMapping("/admin_main.html")
    public ModelAndView toAdminMain(HttpServletResponse response) {

            return new ModelAndView("admin_main");
    }

//进入用户查询图书界面，用户登录后跳转
    @RequestMapping("/reader_main.html")
    public ModelAndView toReaderMain(HttpServletResponse response) {

//        return new ModelAndView("reader_main");
        return new ModelAndView("reader_book_query");
    }
//进入管理员修改密码界面
    @RequestMapping("/admin_repasswd.html")
    public ModelAndView reAdminPasswd() {

        return new ModelAndView("admin_repasswd");
    }
//管理员修改密码逻辑
    @RequestMapping("/admin_repasswd_do")
    public String reAdminPasswdDo(HttpServletRequest request, String oldPasswd, String newPasswd, String reNewPasswd, RedirectAttributes redirectAttributes ) {

        Admin admin=(Admin) request.getSession().getAttribute("admin");
        int id=admin.getAdminId();
        String passwd=loginService.getAdminPasswd(id);

        if(passwd.equals(oldPasswd)){
            boolean succ=loginService.adminRePasswd(id,newPasswd);
            if (succ){

                redirectAttributes.addFlashAttribute("succ", "密码修改成功！");
                return "redirect:/admin_repasswd.html";
                //controller里面的跳转
            }
            else {
                redirectAttributes.addFlashAttribute("error", "密码修改失败！");
                return "redirect:/admin_repasswd.html";
            }
        }else {
            redirectAttributes.addFlashAttribute("error", "旧密码错误！");
            return "redirect:/admin_repasswd.html";
        }
    };

    //配置404页面
     @RequestMapping("*")
     public String notFind(){
     return "404";
       }
}