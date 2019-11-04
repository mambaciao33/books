package com.lzc.books.controller;

import com.lzc.books.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
@Controller
public class indexcontroller {
    PictureService pictureService;
     @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    //登录请求
 /*   @RequestMapping(value = {"/logins"})
    public String page(HttpServletRequest request){
        request.getSession().invalidate();
        return "login";
    }*/
 //上传图片
    @RequestMapping(value = {"/shang"})
    public String page2(HttpServletRequest request){
        request.getSession().invalidate();
        return "shangchuan";
    }
    //查看上传的图片
    @RequestMapping(value = {"/look"})
    public String page3(HttpServletRequest request,Model model){
        String img=pictureService.picture2(555);
        model.addAttribute("img",img);
//        request.getSession().setAttribute("img",img);
        System.out.println(img);
        return "shangchuan";
    }
}
