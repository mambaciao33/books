package com.lzc.books.service;

import com.lzc.books.bean.ReaderInfo;
import com.lzc.books.dao.ReaderCardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderCardService {
    private ReaderCardDao readerCardDao;

    @Autowired
    public void setReaderCardDao(ReaderCardDao readerCardDao) {
        this.readerCardDao = readerCardDao;
    }
    public boolean addReaderCard(ReaderInfo readerInfo){
        return  readerCardDao.addReaderCard(readerInfo)>0;
    }
    public boolean updatePasswd(int readerId,String passwd){
        return readerCardDao.rePassword(readerId,passwd)>0;
    }
    public boolean updateName(int readerId,String name){
        return readerCardDao.updateName(readerId,name)>0;
    }

}
