package com.lzc.books.dao;
import com.lzc.books.bean.Lend;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;

@Mapper
@Component(value ="LendDao")
public interface LendDao {

    @Update("UPDATE lend_list SET backDate =#{date} WHERE bookId =#{bookId} AND backDate is NULL")
    public int bookReturnOne(@Param("bookId") long bookId,@Param("date") Date date);
    @Update("UPDATE book_info SET state = 1 WHERE bookId = #{bookId}")
    public int bookReturnTwo(long bookId);
    @Insert("INSERT INTO lend_list (bookId,readerId,lendDate) VALUES (#{bookId},#{readerId} ,#{date})")
    public int bookLendOne(@Param("bookId") long bookId, @Param("readerId") int readerId, @Param("date") Date date);
    @Update("UPDATE book_info SET state = 0 WHERE bookId = #{bookId} ")
    public int bookLendTwo(long bookId);
    @Select("SELECT * FROM lend_list")
    public ArrayList<Lend> lendList();
    @Select("SELECT * FROM lend_list WHERE readerId = #{readerId} ")
    public ArrayList<Lend> myLendList(int readerId);
    @Select("SELECT bookId FROM book_info WHERE name = #{name} ")
    public Long bookLendOne2(String name);
}
