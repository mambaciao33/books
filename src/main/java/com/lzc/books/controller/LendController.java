package com.lzc.books.controller;

import com.lzc.books.bean.Book;
import com.lzc.books.bean.ReaderCard;
import com.lzc.books.bean.ReaderInfo;
import com.lzc.books.service.BookService;
import com.lzc.books.service.LendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class LendController {

    private LendService lendService;
    @Autowired
    public void setLendService(LendService lendService) {
        this.lendService = lendService;
    }
    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

/*    @RequestMapping("/lendbook.html")
    public ModelAndView bookLend(HttpServletRequest request){
        long bookId=Integer.parseInt(request.getParameter("bookId"));
        Book book=bookService.getBook(bookId);
       ModelAndView modelAndView=new ModelAndView("admin_book_lend");
       modelAndView.addObject("book",book);
       return modelAndView;
    }*/
    @RequestMapping("/lendbook.html/{bookId}")
    public String bookLend(@PathVariable("bookId") Long bookId, Model model){
        Book book=bookService.getBook(bookId);
        model.addAttribute("book",book);
        return "admin_book_lend";
    }
    @RequestMapping("/lendbookdo.html")
    public String updateEmployee(Book book,int readerId,RedirectAttributes redirectAttributes){
//        System.out.println("kkkkkkkkk"+book.getName());
//        System.out.println("kkkkkkkkk"+readerId);
        Long lendsucc5=lendService.selectidbyname(book.getName());
//        System.out.println("kkkkkkkkk"+lendsucc5);
        boolean lendsucc=lendService.bookLend(lendsucc5,readerId);
        if (lendsucc){
            redirectAttributes.addFlashAttribute("succ", "图书借阅成功！");
            return "redirect:/allbooks.html";
        }else {
            redirectAttributes.addFlashAttribute("succ", "图书借阅成功！");
            return "redirect:/allbooks.html";
        }
    }
    @RequestMapping("/returnbook.html/{bookId}")
    public String bookReturn(@PathVariable("bookId") Long bookId,RedirectAttributes redirectAttributes ){
        System.out.println(bookId);
        boolean retSucc=lendService.bookReturn(bookId);
        if (retSucc){
            redirectAttributes.addFlashAttribute("succ", "图书归还成功！");
            return "redirect:/allbooks.html";
        }
        else {
            redirectAttributes.addFlashAttribute("error", "图书归还失败！");
            return "redirect:/allbooks.html";
        }
    }
/*    @RequestMapping("/returnbook.html888")
    public String bookReturn(Long bookId,int readerId, RedirectAttributes redirectAttributes){
        System.out.println(bookId);
        boolean retSucc=lendService.bookReturn(bookId);
        if (retSucc){
            redirectAttributes.addFlashAttribute("succ", "图书归还成功！");
            return "redirect:/allbooks.html";
        }
        else {
            redirectAttributes.addFlashAttribute("error", "图书归还失败！");
            return "redirect:/allbooks.html";
        }
    }*/


    @RequestMapping("/lendlist.html")
    public ModelAndView lendList(){
        ModelAndView modelAndView=new ModelAndView("admin_lend_list");
        modelAndView.addObject("list",lendService.lendList());
        return modelAndView;
    }
    //用户进入查询个人页面
    @RequestMapping("/mylend.html")
    public ModelAndView myLend(HttpServletRequest request){
        ReaderCard readerCard=(ReaderCard) request.getSession().getAttribute("readercard");
        ModelAndView modelAndView=new ModelAndView("reader_lend_list");
        modelAndView.addObject("lists",lendService.myLendList(readerCard.getReaderId()));
        return modelAndView;
    }




}
