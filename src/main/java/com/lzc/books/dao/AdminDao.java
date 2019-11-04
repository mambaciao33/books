package com.lzc.books.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
@Mapper
@Component(value ="AdminDao")
public interface AdminDao {
    @Select("SELECT COUNT(*) FROM admin where adminId =#{adminId} and password = #{password} ")
    public int getMatchCount(@Param("adminId") int adminId,@Param("password") String password);
    @Update("UPDATE admin set password = #{newPasswd} where adminId = #{adminId} ")
    public int rePassword(@Param("adminId") int adminId,@Param("newPasswd") String newPasswd);
    @Select("SELECT password from admin where adminId =#{id}")
    public String getPasswd(int id);
}
