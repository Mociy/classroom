package com.example.classroom.service;

import com.example.classroom.dao.AdminDao;
import com.example.classroom.dao.AuthorDao;
import com.example.classroom.pojo.Author;
import com.example.classroom.pojo.Credentials;
import com.example.classroom.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorDao authorDao;
    @Autowired
    AdminDao adminDao;

    public void addAuthor(Author author){
        authorDao.addAuthor(author);
    }
    public String isExists(String name){
        return authorDao.isExists(name);
    }
    public void check(Credentials credentials){
        String author_id=authorDao.getId(credentials.getUserName());
        credentials.setAuthor_id(author_id);
        credentials.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        credentials.setFlag(0);
        authorDao.check(credentials);
        authorDao.deleteReason(author_id);
    }
    public String getReason(String author_id){
       return authorDao.getReason(author_id);
    }
    public String getId(String author){
        return authorDao.getId(author);
    }
    public Integer isChecking(String author){
        return authorDao.isChecking(authorDao.getId(author));
    }
    public void delete_this(String video_id){
        authorDao.delete_this(video_id);
        adminDao.deleteComplaint(video_id);
    }


    public List<Video> getVideosByAuthor(String author){
        return authorDao.getVideosByAuthor(author);
    }

}
