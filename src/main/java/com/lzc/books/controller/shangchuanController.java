package com.lzc.books.controller;

import com.lzc.books.bean.ReaderCard;
import com.lzc.books.bean.ReaderInfo;
import com.lzc.books.service.PictureService;
import com.lzc.books.service.ReaderInfoService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;
@Controller
//@ResponseBody
public class shangchuanController {

    PictureService pictureService;
    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }
    private ReaderInfoService readerInfoService;
    @Autowired
    public void setReaderInfoService(ReaderInfoService readerInfoService) {
        this.readerInfoService = readerInfoService;
    }

    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String UPLOAD_PATH_PREFIX = "static/asserts/";

    //就是他了
    @RequestMapping("/uploads")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()){
            //返回选择文件提示
            return "请选择上传文件";
        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        //构建文件上传所要保存的"文件夹路径"--这里是相对路径，保存到项目根路径的文件夹下
        String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
//        String format = sdf.format(new Date());
        //存放上传文件的文件夹
        File files = new File(realPath + "mm");
        if(!files.isDirectory()){
            //递归生成文件夹
            files.mkdirs();
        }
        //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try {
            //构建真实的文件路径
//            File newFile = new File(files.getAbsolutePath() + File.separator + newName);
   /*         "hamburger".substring(3,8)  returns "burge"
　　         "smiles".substring(0,5) returns "smile"*/
            File newFile = new File(files.getAbsolutePath() + File.separator + newName);
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            System.out.println(newFile);
            file.transferTo(newFile);
            String  lu="asserts\\mm\\";
            ReaderCard readerCard=(ReaderCard) request.getSession().getAttribute("readercard");
            boolean flag=pictureService.picture(readerCard.getReaderId(),lu+newName);
     /*       System.out.println(flag);
            ReaderInfo readerInfo=readerInfoService.getReaderInfo(readerCard.getReaderId());
            System.out.println(readerInfo.getImg());
            model.addAttribute("readerinfo",readerInfo);*/
            return "redirect:/reader_info.html";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败!";
    }
}
