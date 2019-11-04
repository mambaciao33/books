package com.lzc.books.controller;
import com.lzc.books.bean.Book;
import com.lzc.books.service.BookService;
import com.lzc.books.service.LendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class BookController {
    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    private LendService lendService;
    @Autowired
    public void setLendService(LendService lendService) {
        this.lendService = lendService;
    }

    @RequestMapping("/querybook.html")
    public ModelAndView queryBookDo(HttpServletRequest request, String searchWord){
        boolean exist=bookService.matchBook(searchWord);
        if (exist){
            ArrayList<Book> books = bookService.queryBook(searchWord);
            ModelAndView modelAndView = new ModelAndView("admin_books");
            modelAndView.addObject("books",books);
            return modelAndView;
        }
        else{
            return new ModelAndView("admin_books","error","没有匹配的图书");
        }
    }
    @RequestMapping("/reader_querybook.html")
    public ModelAndView readerQueryBook(){
       return new ModelAndView("reader_book_query");

    }
    @RequestMapping("/reader_querybook_do.html")
    public String readerQueryBookDo(HttpServletRequest request, String searchWord, RedirectAttributes redirectAttributes){
        boolean exist=bookService.matchBook(searchWord);
        if (exist){
            ArrayList<Book> books = bookService.queryBook(searchWord);
            redirectAttributes.addFlashAttribute("books", books);
//            request.getSession().getAttribute("books");
            request.getSession().setAttribute("abooks", books);

            return "redirect:/reader_querybook.html";
        }
        else{
            redirectAttributes.addFlashAttribute("error", "没有匹配的图书！");
            return "redirect:/reader_querybook.html";
        }

    }
//管理员请求查看全部图书
    @RequestMapping("/allbooks.html")
    public ModelAndView allBook(){
        ArrayList<Book> books=bookService.getAllBooks();
        ModelAndView modelAndView=new ModelAndView("admin_books");
        modelAndView.addObject("books",books);
        return modelAndView;
    }
    @RequestMapping("/deletebook/{sernum}")
    public String deleteBook(@PathVariable("sernum") long sernum,RedirectAttributes redirectAttributes){
        System.out.println(sernum);
        int res=bookService.deleteBook(sernum);
        if (res==1){
            redirectAttributes.addFlashAttribute("succ", "图书删除成功！");
            return "redirect:/allbooks.html";
        }else {
            redirectAttributes.addFlashAttribute("error", "图书删除失败！");
            return "redirect:/allbooks.html";
        }
    }
    //管理员删除图书
    @RequestMapping("/deletebook2.html/{bookId}")
    public String bookEdit(@PathVariable("bookId") Long bookId,RedirectAttributes redirectAttributes){
        int res=bookService.deleteBook2(bookId);

        if (res==1){
            redirectAttributes.addFlashAttribute("succ", "图书删除成功！");
            return "redirect:/allbooks.html";
        }else {
            redirectAttributes.addFlashAttribute("error", "图书删除失败！");
            return "redirect:/allbooks.html";
        }
    }

    @RequestMapping("/book_add.html")
    public ModelAndView addBook(HttpServletRequest request){

            return new ModelAndView("admin_book_add");

    }

    @RequestMapping("/book_add_do.html")
    //BookAddCommand的属性必须和表单的相同
    public String addBookDo(BookAddCommand bookAddCommand,RedirectAttributes redirectAttributes){
        System.out.println(bookAddCommand);
        Book book=new Book();
        book.setBookId(0l);
        book.setPrice(bookAddCommand.getPrice());
        book.setState(bookAddCommand.getState());
        book.setPublish(bookAddCommand.getPublish());
        book.setPubdate(bookAddCommand.getPubdate());
        book.setName(bookAddCommand.getName());
        book.setIsbn(bookAddCommand.getIsbn());
        book.setClassId(bookAddCommand.getClassId());
        book.setAuthor(bookAddCommand.getAuthor());
        book.setIntroduction(bookAddCommand.getIntroduction());
        book.setPressmark(bookAddCommand.getPressmark());
        book.setLanguage(bookAddCommand.getLanguage());
        boolean succ=bookService.addBook(book);
//        ArrayList<Book> books=bookService.getAllBooks();
        if (succ){
            redirectAttributes.addFlashAttribute("succ", "图书添加成功！");
            return "redirect:/allbooks.html";
        }
        else {
            redirectAttributes.addFlashAttribute("succ", "图书添加失败！");
            return "redirect:/allbooks.html";
        }
    }
    //管理员进入书籍编辑页面
    @RequestMapping("/updatebook.html/{bookId}")
    public String bookEdit(@PathVariable("bookId") Long bookId, Model model){
        Book book=bookService.getBook(bookId);
        model.addAttribute("detail",book);
        return "admin_book_edit";
    }
    //管理员编辑图书信息
    @RequestMapping("/book_edit_do.html")
    public String bookEditDo(Book books,RedirectAttributes redirectAttributes){
        System.out.println(books.getName());
        System.out.println(books.getBookId());
        Book book=new Book();
        book.setBookId(books.getBookId());
        book.setPrice(books.getPrice());
        book.setState(books.getState());
        book.setPublish(books.getPublish());
        book.setPubdate(books.getPubdate());
        book.setName(books.getName());
        book.setIsbn(books.getIsbn());
        book.setClassId(books.getClassId());
        book.setAuthor(books.getAuthor());
        book.setIntroduction(books.getIntroduction());
        book.setPressmark(books.getPressmark());
        book.setLanguage(books.getLanguage());
        boolean succ=bookService.editBook(book);
        if (succ){
            redirectAttributes.addFlashAttribute("succ", "图书修改成功！");
            return "redirect:/allbooks.html";
        }
        else {
            redirectAttributes.addFlashAttribute("error", "图书修改失败！");
            return "redirect:/allbooks.html";
        }
    }

//管理员查看书籍详情
    @RequestMapping("/bookdetail.html/{bookId}")
    public String bookLend(@PathVariable("bookId") Long bookId, Model model){
        Book book=bookService.getBook(bookId);
        model.addAttribute("detail",book);
        return "admin_book_detail";
    }

//读者查看书籍详情
    @GetMapping("/readerbookdetail.html/{book_id}")
    public String toEditPage(@PathVariable("book_id") Long book_id,Model model){
       Book book=bookService.getBook(book_id);
        model.addAttribute("details",book);
        return "reader_book_detail";
    }

}
