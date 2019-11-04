package com.lzc.books.dao;


import com.lzc.books.bean.ReaderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;


@Mapper
@Component(value ="ReaderInfoDao")
public interface ReaderInfoDao {
    @Select("SELECT * FROM reader_info")
    public ArrayList<ReaderInfo> getAllReaderInfo();
    @Select("SELECT * FROM reader_info where readerId = #{readerId} ")
    public ReaderInfo findReaderInfoByReaderId(int readerId);
    @Delete("DELETE FROM reader_info where readerId = #{readerId} ")
    public int deleteReaderInfo(int readerId);
    @Delete("DELETE FROM reader_card where readerId = #{readerId} ")
    public int deleteReaderCard(int readerId);
    @Update("UPDATE reader_info set name = #{name} ,sex = #{sex}  ,birth =#{birth},address =#{address} ,telcode =#{telcode} where readerId = #{readerId} ")
    public int editReaderInfo(ReaderInfo readerInfo);
    @Insert("INSERT INTO reader_info VALUES(#{readerId},#{name},#{sex},#{birth},#{address},#{telcode})")
    public int addReaderInfo(ReaderInfo readerInfo);
}
