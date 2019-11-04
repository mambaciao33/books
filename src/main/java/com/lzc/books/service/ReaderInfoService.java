package com.lzc.books.service;


import com.lzc.books.bean.ReaderInfo;
import com.lzc.books.dao.ReaderInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReaderInfoService {

    private ReaderInfoDao readerInfoDao;
    @Autowired
    public void setReaderInfoDao(ReaderInfoDao readerInfoDao) {
        this.readerInfoDao = readerInfoDao;
    }
    public ArrayList<ReaderInfo> readerInfos(){
        return readerInfoDao.getAllReaderInfo();
    }

    public boolean deleteReaderInfo(int readerId){
        return readerInfoDao.deleteReaderInfo(readerId)>0;
    }
    public boolean deleteReaderCard(int readerId){
        return readerInfoDao.deleteReaderCard(readerId)>0;
    }

    public ReaderInfo getReaderInfo(int readerId){
        return readerInfoDao.findReaderInfoByReaderId(readerId);
    }
    public boolean editReaderInfo(ReaderInfo readerInfo){
        return readerInfoDao.editReaderInfo(readerInfo)>0;
    }
    public boolean addReaderInfo(ReaderInfo readerInfo){
        return  readerInfoDao.addReaderInfo(readerInfo)>0;
    }
}
