package com.lzc.books.service;

import com.lzc.books.dao.PictureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService {
    @Autowired
    PictureDao  pictureDao;

    public void setPictureDao(PictureDao pictureDao) {
        this.pictureDao = pictureDao;
    }

    public boolean picture(int readerId, String img){
        return  pictureDao.picture(readerId, img)>0;
    }

    public String picture2(Integer bookId)
    {
        return  pictureDao.picture2(bookId);
    }
}
