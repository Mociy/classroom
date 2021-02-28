package com.example.classroom.service;

import com.example.classroom.dao.AdminDao;
import com.example.classroom.dao.AuthorDao;
import com.example.classroom.pojo.Author;
import com.example.classroom.pojo.Complaint;
import com.example.classroom.pojo.Credentials;
import com.example.classroom.pojo.Reason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminDao adminDao;


    public String getPassword(String name){
        return adminDao.getPassword(name);
    }
    public List<Credentials> getAll(int page){
        return adminDao.getAll(page);
    }

    public List<Complaint>getComplaint(){
        return adminDao.getComplaint();
    }

    public void updateFlag(String author_id){
        adminDao.updateFlag(author_id);
    }

    public void no_pass(String author_id){
        adminDao.no_pass(author_id);
    }

    public void addReason(Reason reason){
        adminDao.addReason(reason);
    }
    public Author getAuthor(String name){
        return adminDao.getAuthor(name);
    }
}
