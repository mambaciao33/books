package com.lzc.books.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "PictureDao")
public interface PictureDao {
/*    @Insert("INSERT INTO book_img (bookId,img) VALUES (#{bookId},#{img})")
    public int picture(@Param("bookId") Integer bookId, @Param("img") String img);*/
@Update("update reader_info set img= #{img}   where readerId= #{readerId}")
    //@Insert("INSERT INTO book_img (bookId,img) VALUES (#{bookId},#{img})")
    public int picture(@Param("readerId") Integer readerId, @Param("img") String img);
    @Select("SELECT img FROM book_img WHERE bookId = #{bookId} ")
    public String  picture2(Integer bookId);
}
