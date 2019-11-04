package com.lzc.books.dao;


import com.lzc.books.bean.ReaderCard;
import com.lzc.books.bean.ReaderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
@Mapper
@Component(value = "ReaderCardDao")
public interface ReaderCardDao {
   @Update("UPDATE reader_card set name = #{name} where readerId = #{readerId}")
    public int updateName(@Param("readerId") int readerId,@Param("name") String name);
    @Insert("INSERT INTO reader_card (readerId,name) values ( #{readerId}, #{name})")
    public int addReaderCard(ReaderInfo readerInfo);
    @Update("UPDATE reader_card set passwd =#{newPasswd} where readerId = #{readerId}")
    public int rePassword(@Param("readerId") int readerId,@Param("newPasswd") String newPasswd);
    @Select("select readerId, name, passwd, cardState from reader_card where readerId = #{readerId} ")
    public ReaderCard findReaderByReaderId(int readerId);
    @Select("select count(*) from reader_card where readerId = #{readerId} and passwd =#{passwd}")
    public int getMatchCount(@Param("readerId") int readerId, @Param("passwd") String passwd);

}
