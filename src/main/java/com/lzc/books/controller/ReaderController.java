package com.lzc.books.controller;


import com.lzc.books.bean.Book;
import com.lzc.books.bean.ReaderCard;
import com.lzc.books.bean.ReaderInfo;
import com.lzc.books.service.LoginService;
import com.lzc.books.service.ReaderCardService;
import com.lzc.books.service.ReaderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class ReaderController {

    private ReaderInfoService readerInfoService;
    @Autowired
    public void setReaderInfoService(ReaderInfoService readerInfoService) {
        this.readerInfoService = readerInfoService;
    }
    private LoginService loginService;


    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    private ReaderCardService readerCardService;

    @Autowired
    public void setReaderCardService(ReaderCardService readerCardService) {
        this.readerCardService = readerCardService;
    }

    @RequestMapping("allreaders.html")
    public ModelAndView allBooks(){
        ArrayList<ReaderInfo> readers=readerInfoService.readerInfos();
        ModelAndView modelAndView=new ModelAndView("admin_readers");
        modelAndView.addObject("readers",readers);
        return modelAndView;
    }
    @GetMapping("/reader_delete.html/{readerId}")
    public String readerDelete(@PathVariable("readerId") int readerId, Model model, RedirectAttributes redirectAttributes){
        boolean success=readerInfoService.deleteReaderInfo(readerId);
        boolean successes=readerInfoService.deleteReaderCard(readerId);
        if(success&&successes){
            redirectAttributes.addFlashAttribute("succ", "密码修改成功！");
            return "redirect:/allreaders.html";
        }else {
            redirectAttributes.addFlashAttribute("error", "密码修改失败！");
            return "redirect:/allreaders.html";
        }
    }
   //用户进入个人信息查询页面
    @RequestMapping("/reader_info.html")
    public ModelAndView toReaderInfo(HttpServletRequest request) {
        ReaderCard readerCard=(ReaderCard) request.getSession().getAttribute("readercard");
        ReaderInfo readerInfo=readerInfoService.getReaderInfo(readerCard.getReaderId());
        System.out.println(readerInfo.toString());
        ModelAndView modelAndView=new ModelAndView("reader_info");
        modelAndView.addObject("readerinfo",readerInfo);
        return modelAndView;
    }
//跳到编辑页面
    @RequestMapping("/reader_edit.html/{readerId}")
    public String readerInfoEdit(@PathVariable("readerId") int readerId, Model model){
        ReaderInfo readerInfo=readerInfoService.getReaderInfo(readerId);
//        Book book=bookService.getBook(book_id);
        model.addAttribute("readerInfos",readerInfo);
        return "admin_reader_edit";
    }
    //处理修改信息并跳回全部读者页面
    @RequestMapping("/reader_edit_do.html")
    public String updateEmployee(ReaderInfo readerInfo, RedirectAttributes redirectAttributes){
        System.out.println("修改的员工数据："+readerInfo.getName());
        ReaderCard readerCard = loginService.findReaderCardByUserId(readerInfo.getReaderId());
        String oldName=readerCard.getName();
        if(!oldName.equals(readerInfo.getName())){
            boolean succo=readerCardService.updateName(readerInfo.getReaderId(),readerInfo.getName());
            ReaderInfo readerInfos=new ReaderInfo();
            readerInfos.setAddress(readerInfo.getAddress());
            readerInfos.setBirth(readerInfo.getBirth());
            readerInfos.setName(readerInfo.getName());
            readerInfos.setReaderId(readerInfo.getReaderId());
            readerInfos.setTelcode(readerInfo.getTelcode());
            readerInfos.setSex(readerInfo.getSex());
            boolean succ=readerInfoService.editReaderInfo(readerInfos);
            if(succo&&succ){
                redirectAttributes.addFlashAttribute("succ", "读者信息修改成功！");
                return "redirect:/allreaders.html";
            }else {
                redirectAttributes.addFlashAttribute("error", "读者信息修改失败！");
                return "redirect:/allreaders.html";
            }
        }
        else {
            System.out.println("部分修改");
            ReaderInfo readerInfoc=new ReaderInfo();
            readerInfoc.setAddress(readerInfo.getAddress());
            readerInfoc.setBirth(readerInfo.getBirth());
            readerInfoc.setName(readerInfo.getName());
            readerInfoc.setReaderId(readerInfo.getReaderId());
            readerInfoc.setTelcode(readerInfo.getTelcode());
            readerInfoc.setSex(readerInfo.getSex());
            boolean succ=readerInfoService.editReaderInfo(readerInfoc);
            if(succ){
               // redirectAttributes.addFlashAttribute("succ", "读者信息修改成功！");
                return "redirect:/allreaders.html";
            }else {
               // redirectAttributes.addFlashAttribute("error", "读者信息修改失败！");
                return "redirect:/allreaders.html";
            }
        }
    }
//进入管理员添加读者页面
    @RequestMapping("reader_add.html")
    public ModelAndView readerInfoAdd(){
        ModelAndView modelAndView=new ModelAndView("admin_reader_add");
        return modelAndView;

    }
    //用户功能进入修改密码页面
    @RequestMapping("reader_repasswd.html")
    public ModelAndView readerRePasswd(){
        ModelAndView modelAndView=new ModelAndView("reader_repasswd");
        return modelAndView;
    }
    //用户功能修改密码执行
    @RequestMapping("reader_repasswd_do.html")
    public String readerRePasswdDo(HttpServletRequest request, String oldPasswd, String newPasswd, String reNewPasswd, RedirectAttributes redirectAttributes){
        ReaderCard readerCard=(ReaderCard) request.getSession().getAttribute("readercard");
        int readerId=readerCard.getReaderId();
        String passwd=readerCard.getPasswd();

        if (newPasswd.equals(reNewPasswd)){
            if(passwd.equals(oldPasswd)){
                boolean succ=readerCardService.updatePasswd(readerId,newPasswd);
                if (succ){
                    ReaderCard readerCardNew = loginService.findReaderCardByUserId(readerId);
                    request.getSession().setAttribute("readercard", readerCardNew);
                    redirectAttributes.addFlashAttribute("succ", "密码修改成功！");
                    return "redirect:/reader_repasswd.html";
                }else {
                    redirectAttributes.addFlashAttribute("succ", "密码修改失败！");
                    return "redirect:/reader_repasswd.html";
                }

            }else {
                redirectAttributes.addFlashAttribute("error", "修改失败,原密码错误");
                return "redirect:/reader_repasswd.html";
            }
        }else {
            redirectAttributes.addFlashAttribute("error", "修改失败,两次输入的新密码不相同");
            return "redirect:/reader_repasswd.html";
        }
    }

    @RequestMapping("reader_add_do.html")
    public String readerInfoAddDo(String name,String sex,String birth,String address,String telcode,int readerId,RedirectAttributes redirectAttributes){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date nbirth=new Date();
        try{
            Date date=sdf.parse(birth);
            nbirth=date;
        }catch (ParseException e){
            e.printStackTrace();
        }

        ReaderInfo readerInfo=new ReaderInfo();
        readerInfo.setAddress(address);
        readerInfo.setBirth(nbirth);
        readerInfo.setName(name);
        readerInfo.setReaderId(readerId);
        readerInfo.setTelcode(telcode);
        readerInfo.setSex(sex);
        boolean succ=readerInfoService.addReaderInfo(readerInfo);
        boolean succc=readerCardService.addReaderCard(readerInfo);
        ArrayList<ReaderInfo> readers=readerInfoService.readerInfos();
        if (succ&&succc){
            redirectAttributes.addFlashAttribute("succ", "添加读者信息成功！");
            return "redirect:/allreaders.html";
        }else {
            redirectAttributes.addFlashAttribute("succ", "添加读者信息失败！");
            return "redirect:/allreaders.html";
        }
    }
    @RequestMapping("reader_info_edit")
    public ModelAndView readerInfoEditReader(HttpServletRequest request){
        ReaderCard readerCard=(ReaderCard) request.getSession().getAttribute("readercard");
        ReaderInfo readerInfo=readerInfoService.getReaderInfo(readerCard.getReaderId());
        ModelAndView modelAndView=new ModelAndView("reader_info_edit");
        modelAndView.addObject("readerinfo",readerInfo);
        return modelAndView;

    }
    @RequestMapping("reader_edit_do_r.html")
    public String readerInfoEditDoReader(HttpServletRequest request, String name, String sex, String birth, String address, String telcode, RedirectAttributes redirectAttributes){
        ReaderCard readerCard=(ReaderCard) request.getSession().getAttribute("readercard");
        if (!readerCard.getName().equals(name)){
            boolean succo=readerCardService.updateName(readerCard.getReaderId(),name);
            //更新reader_card数据
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date nbirth=new Date();
            try{
                Date date=sdf.parse(birth);
                nbirth=date;
            }catch (ParseException e){
                e.printStackTrace();
            }
            ReaderInfo readerInfo=new ReaderInfo();
            readerInfo.setReaderId(readerCard.getReaderId());
            readerInfo.setName(name);
            readerInfo.setSex(sex);
            readerInfo.setBirth(nbirth);
            readerInfo.setAddress(address);
            readerInfo.setTelcode(telcode);
            boolean succ=readerInfoService.editReaderInfo(readerInfo);
            if(succ&&succo){
                ReaderCard readerCardNew = loginService.findReaderCardByUserId(readerCard.getReaderId());
                request.getSession().setAttribute("readercard", readerCardNew);
                redirectAttributes.addFlashAttribute("succ", "信息修改成功！");
                return "redirect:/reader_info.html";
            }else {
                redirectAttributes.addFlashAttribute("error", "信息修改失败！");
                return "redirect:/reader_info.html";
            }
        }else {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date nbirth=new Date();
            try{
                Date date=sdf.parse(birth);
                nbirth=date;
            }catch (ParseException e){
                e.printStackTrace();
            }
            ReaderInfo readerInfo=new ReaderInfo();
            readerInfo.setAddress(address);
            readerInfo.setBirth(nbirth);
            readerInfo.setName(name);
            readerInfo.setReaderId(readerCard.getReaderId());
            readerInfo.setTelcode(telcode);
            readerInfo.setSex(sex);

            boolean succ=readerInfoService.editReaderInfo(readerInfo);
            if(succ){
                ReaderCard readerCardNew = loginService.findReaderCardByUserId(readerCard.getReaderId());
                request.getSession().setAttribute("readercard", readerCardNew);
                redirectAttributes.addFlashAttribute("succ", "信息修改成功！");
                return "redirect:/reader_info.html";
            }else {
                redirectAttributes.addFlashAttribute("error", "信息修改失败！");
                return "redirect:/reader_info.html";
            }
        }
    }
}
