package com.lzc.books.service;

import com.lzc.books.bean.Book;
import com.lzc.books.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;


    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public ArrayList<Book> queryBook(String searchWord){
        return  bookDao.queryBook(searchWord);
    }

    public ArrayList<Book> getAllBooks(){
        return bookDao.getAllBooks();
    }

    public int deleteBook(long sernum){
        return bookDao.deleteBook(sernum);
    }
    public int deleteBook2(long bookId){
        return bookDao.deleteBook2(bookId);
    }

    public boolean matchBook(String searchWord){
        return bookDao.matchBook(searchWord)>0;
    }

    public boolean addBook(Book book){
        return bookDao.addBook(book)>0;
    }

    public Book getBook(Long bookId){
        Book book=bookDao.getBook(bookId);
        return book;
    }
    public boolean editBook(Book book){
        System.out.println(book.getBookId());
        return bookDao.editBook(book)>0;
    }

}
